package com.myshop.admin.brand;

import com.myshop.admin.FileUploadUtils;
import com.myshop.admin.ListInfoUtils;
import com.myshop.admin.category.CategoryService;
import com.myshop.admin.export.BrandExportToEXCEL;
import com.myshop.admin.export.CategoryExportToEXCEL;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.Brand;
import com.myshop.common.entity.Category;
import com.myshop.common.exception.BrandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model,1,"name","asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable("pageNum") int pageNum,
                             @RequestParam(value = "sortField",required = false) String sortField,
                             @RequestParam(value = "sortDir",required = false) String sortDir,
                             @RequestParam(value = "keyword",required = false) String keyword) {

        Page<Brand> page = brandService.listByPage(pageNum,sortField,sortDir,keyword);
        List<Brand> list = page.getContent();
        int startCount = (pageNum - 1) * BrandService.BRAND_PER_PAGE + 1;
        long endCount = startCount + BrandService.BRAND_PER_PAGE - 1;
        ListInfoUtils.setInfoList(model, pageNum, sortField, sortDir, keyword, endCount, page, startCount, list);

        return "brands/brands";

    }

    @GetMapping("/create_new")
    public String createBrand(Model model) {
        List<Category> categories = categoryService.listHierarchicalUseInForm();
        model.addAttribute("categories", categories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create new brand");
        return "brands/brand_form";
    }

    @GetMapping("/edit/{id}")
    public String editBrand(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Brand brand = brandService.get(id);
            List<Category> categories = categoryService.listHierarchicalUseInForm();
            model.addAttribute("categories", categories);
            model.addAttribute("brand", brand);
            model.addAttribute("pageTitle", "Edit brand with id: " + id);

            return "brands/brand_form";
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
            return "redirect:/brands";
        }
    }


    @PostMapping("/save")
    public String saveBrand(@ModelAttribute("brand") Brand brand,
                            @RequestParam("fileImage") MultipartFile multipartFile,
                            RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            brand.setLogo(fileName);
            Brand savedBrand = brandService.save(brand);
            String uploadDir = "brands-logo/" + savedBrand.getId();
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
        } else {

            brandService.save(brand);
        }
        redirectAttributes.addFlashAttribute("message_success", "The brand has been saved successfully");

        return "redirect:/brands";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            String dir = "brands-logo/" + id;
            AmazonS3Util.removeFolder(dir);
            brandService.deleteBrand(id);
            redirectAttributes.addFlashAttribute("message_success", "The brand has been deleted successfully");
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }


        return "redirect:/brands";
    }

    @GetMapping("/export_excel")
    public void exportToEXCEL(HttpServletResponse response) throws IOException {
        List<Category> categoryList = categoryService.listHierarchicalUseInForm() ;
        List<Brand> brandList = brandService.listAll();
        BrandExportToEXCEL exportToEXCEL = new BrandExportToEXCEL();
        exportToEXCEL.export(response,categoryList,brandList);
    }

}
