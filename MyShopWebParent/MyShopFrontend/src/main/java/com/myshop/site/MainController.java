package com.myshop.site;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.Product;
import com.myshop.site.category.CategoryService;
import com.myshop.site.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private CategoryService categoryService;
    @Autowired ProductService productService;



    @GetMapping("")
    public String viewHomePage(Model model ) {
        List<Category> categoryList = categoryService.listAllEnabled();
        List<Product> appleProducts  = productService.listByBrand("Apple");
        List<Product> accessories = productService.listByCategory("Accessories");
        model.addAttribute("accessories",accessories);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("appleProducts",appleProducts );


        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication == null || authentication instanceof AnonymousAuthenticationToken)) {
            return viewHomePage(model);
        }
        return "login";
    }
}
