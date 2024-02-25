package com.myshop.admin.product;

import com.myshop.admin.FileUploadUtils;
import com.myshop.admin.ListInfoUtils;
import com.myshop.admin.brand.BrandService;
import com.myshop.admin.export.ProductExportToCSV;
import com.myshop.admin.export.ProductExportToEXCEL;
import com.myshop.admin.security.MyShopUserDetails;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.Brand;
import com.myshop.common.entity.Product;
import com.myshop.common.entity.ProductImage;
import com.myshop.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private BrandService brandService;
    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model,1,"name","asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model,@PathVariable("pageNum") int pageNum,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             @RequestParam(value = "keyword",required = false) String keyword) {

        Page<Product> page = productService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Product> list = page.getContent();
        int startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
        ListInfoUtils.setInfoList(model, pageNum, sortField, sortDir, keyword, endCount, page, startCount, list);
        return "products/products";
    }

    @GetMapping("/create_new")
    public String createProduct(Model model) {
        List<Brand> brands = brandService.listAll();
        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);
        model.addAttribute("brands",brands);
        model.addAttribute("product",product);
        model.addAttribute("pageTitle","Create new product");
        return "products/product_form";
    }

    @PostMapping("/save")
    public String saveProduct(@RequestParam(value = "fileImage",required = false)MultipartFile mainImgMultipart,
                              @RequestParam(value = "extrasFileImage",required = false) MultipartFile[] extraImgsMultipart,
                              @ModelAttribute("product") Product product,
                              @RequestParam(value = "detailIDs",required = false) String [] detailIDs,
                              @RequestParam(value = "nameDetails",required = false) String [] nameDetails,
                              @RequestParam(value = "valueDetails",required = false) String [] valueDetails,
                              @RequestParam(value = "imageIDs",required = false) String [] imageIDs,
                              @RequestParam(value = "imageNames",required = false) String [] imageNames,
                              @AuthenticationPrincipal MyShopUserDetails loggedUser,
                              RedirectAttributes redirectAttributes) throws ProductNotFoundException, IOException {
        if(loggedUser.hasRole("Salesperson")) {

            productService.savePrice(product);
            redirectAttributes.addFlashAttribute("message_success", "The product has been saved successfully");
            return "redirect:/products";
        }
        setMainImage(mainImgMultipart,product);
        setExistingExtraImgs(imageIDs,imageNames,product);
        setNewExtraImgs(extraImgsMultipart,product);
        setProductDetails(detailIDs,nameDetails,valueDetails,product);
        Product savedProduct = productService.save(product);
        //delete extra images those not refer to
        deleteExtraImageWereRemoveOnForm(savedProduct);
        saveImagesProduct(mainImgMultipart,extraImgsMultipart,savedProduct);
        redirectAttributes.addFlashAttribute("message_success", "The product has been saved successfully");
        return "redirect:/products";
    }

    private void deleteExtraImageWereRemoveOnForm(Product savedProduct) {
        String uploadDir = "products-photos/" + savedProduct.getId() + "/extras";
        List<String> listObjectKeys = AmazonS3Util.listFolder(uploadDir);
        for( var objectKey : listObjectKeys) {
            int lastIndexOfSlash = objectKey.lastIndexOf("/");
            String fileName = objectKey.substring(lastIndexOfSlash+1);
            if(savedProduct.containImageName(fileName)==false) {
                AmazonS3Util.deleteFile(objectKey);
            }
        }


    }

    private void setExistingExtraImgs(String[] imageIDs, String[] imageNames, Product product) {
        if(imageIDs==null || imageNames==null || imageIDs.length <= 0 || imageNames.length <=0 ) return;
        if(imageIDs.length >0 && imageNames.length >0) {
            Set<ProductImage> images = new HashSet<>();
            for (int i = 0; i < imageIDs.length; i++) {
                int id = Integer.parseInt(imageIDs[i]);
                String name = imageNames[i];
                images.add(new ProductImage(id,name,product ));

            }
            product.setImages(images);
        }
    }

    private void setProductDetails(String detailIDs [],String[] nameDetails, String[] valueDetails, Product product) {
        if(nameDetails==null || valueDetails == null || nameDetails.length ==0 || valueDetails.length ==0) return;
        if(nameDetails.length >0 && valueDetails.length>0) {
            for (int i = 0; i < nameDetails.length; i++) {
                String name = nameDetails[i];
                String value = valueDetails[i];
                Integer id = Integer.parseInt(detailIDs[i]);
                if(id!=0) {
                    product.addDetail(id,name,value,product);

                }else if (!name.isEmpty() && !value.isEmpty())  {
                    product.addDetail(name,value,product);
                }
            }
        }
    }

    private void saveImagesProduct(MultipartFile mainImgMultipart, MultipartFile[] extraImgsMultipart, Product savedProduct) throws IOException {
        if(!mainImgMultipart.isEmpty()) {
            String uploadDir = "products-photos/" + savedProduct.getId() ;
            List<String> listObjectKeys = AmazonS3Util.listFolder(uploadDir + "/");
            for(var objectKey : listObjectKeys) {
                if(!objectKey.contains("/extras/")) {
                    AmazonS3Util.deleteFile(objectKey);
                }
            }
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir,savedProduct.getMainImage(),mainImgMultipart);
            AmazonS3Util.uploadFile(uploadDir,savedProduct.getMainImage(),mainImgMultipart.getInputStream());
        }

        if(extraImgsMultipart.length>0) {
            String uploadDir = "products-photos/" + savedProduct.getId() + "/extras";
//        not clean directory:->  :deleteExtraImageWereRemoveOnForm()   FileUploadUtils.cleanDir(uploadDir);
            for(var multipart : extraImgsMultipart) {
                if(!multipart.isEmpty() ) {
                    String fileName = StringUtils.cleanPath(multipart.getOriginalFilename());

//                    FileUploadUtils.saveFile(uploadDir,fileName,multipart);
                    AmazonS3Util.uploadFile(uploadDir,fileName,multipart.getInputStream());
                }
            }
        }

    }

    private void setNewExtraImgs(MultipartFile[] extraImgsMultipart, Product product) {
        if(extraImgsMultipart.length>0) {
            for(var multipart : extraImgsMultipart) {
                if(!multipart.isEmpty()) {
                    String fileName =  StringUtils.cleanPath(multipart.getOriginalFilename())  ;
                    if(!product.containImageName(fileName)) {

                    product.addExtraImage(fileName,product);
                    }
                }
            }
        }
    }

    private void setMainImage(MultipartFile mainImgMultipart, Product product) {
        if(!mainImgMultipart.isEmpty()) {
            String fileName =StringUtils.cleanPath( mainImgMultipart.getOriginalFilename());
            product.setMainImage(fileName);
        }
    }

    @GetMapping("{productId}/enabled/{status}")
    public String updateEnabled(@PathVariable("productId") Integer productId,@PathVariable("status") boolean status,RedirectAttributes redirectAttributes ) {
        productService.updateStatus(productId,status);
        redirectAttributes.addFlashAttribute("message_success","The products has been updated successfully!");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.get(id);
            List<Brand> brands = brandService.listAll();
            model.addAttribute("product", product);
            model.addAttribute("brands",brands);
            model.addAttribute("pageTitle", "Edit product with id: " + id);

            return "products/product_form";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
            return "redirect:/products";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            String mainImageFolder = "products-photos/" + id;
            String extrasImageFolder  = "products-photos/" + id+"/extras";
//            FileUploadUtils.cleanDir(mainImage);
//            FileUploadUtils.cleanDir(extrasImage);
//            FileUploadUtils.deleteDir(extrasImage);
//            FileUploadUtils.deleteDir(mainImage);
            AmazonS3Util.removeFolder(mainImageFolder);
            AmazonS3Util.removeFolder(extrasImageFolder);
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("message_success", "The product has been deleted successfully");
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }


        return "redirect:/products";
    }

    @GetMapping("{id}/view_detail")
    public String viewProductDetail(@PathVariable("id") Integer id,Model model,RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.get(id);

            model.addAttribute("product",product);
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("message_error", e.getMessage());
        }

        return "products/modal_product_details";
    }

    @GetMapping("/export_csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Product> productList = productService.listAll() ;
        ProductExportToCSV exportToCSV = new ProductExportToCSV();
        exportToCSV.export(response,productList);
    }

    @GetMapping("/export_excel")
    public void exportToEXCEL(HttpServletResponse response) throws IOException {
        List<Product> productList = productService.listAll() ;
        ProductExportToEXCEL exportToEXCEL = new ProductExportToEXCEL();
        exportToEXCEL.export(response,productList);

    }

}
