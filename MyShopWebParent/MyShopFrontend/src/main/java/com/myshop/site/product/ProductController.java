package com.myshop.site.product;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.Product;
import com.myshop.common.exception.CategoryNotFoundException;
import com.myshop.common.exception.ProductNotFoundException;
import com.myshop.site.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/c/{alias}")
    public String listByCategoryFirstPage(Model model,@PathVariable("alias") String alias) {
        return listByCategory(alias,1,model );
    }

    @GetMapping("/c/{alias}/page/{pageNum}")
    public String listByCategory(@PathVariable("alias") String alias,@PathVariable("pageNum") int pageNum, Model model) {
        Category category = categoryService.getByAlias(alias);
        if(category == null) {
            return "error/404";
        }
        List<Category> listCategoryParents = categoryService.getCategoryParents(category);
        Page<Product> page = productService.listByCategory(pageNum,category.getId() );
        List<Product> productList = page.getContent();
        int startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
        if(endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listCategoryParents",listCategoryParents);
        model.addAttribute("productList",productList);
        model.addAttribute("category",category);
        return "products/products_by_category";

    }
    @GetMapping("/p/{productId}/view_detail")
    public String viewDetailProduct(Model model, @PathVariable("productId") Integer id) {
        try {

            Product product = productService.get(id);
            Category category = categoryService.get(product.getCategory().getId());
            List<Category> listCategoryParents = categoryService.getCategoryParents(category);
            if(category == null) {
                return "error/404";
            }

            //Other related products

            List<Product> relatedProducts = productService.listRelatedProductsByCategory(product.getCategory().getId(),product.getId());
            model.addAttribute("product",product);
            model.addAttribute("listCategoryParents",listCategoryParents);
            model.addAttribute("relatedProducts",relatedProducts);
            return "products/product_detail";
        } catch (ProductNotFoundException | CategoryNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping("/products/search/page/{pageNum}")
    public String searchProducts(Model model,
                                 @PathVariable("pageNum") int pageNum,
                                 @RequestParam("keyword") String keyword) {

        Page<Product> page = productService.search(pageNum,keyword);
        List<Product> searchResult = page.getContent();
        int startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
        if(endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("searchResult",searchResult);
        model.addAttribute("keyword",keyword);
        return "products/search_products";

    }


}
