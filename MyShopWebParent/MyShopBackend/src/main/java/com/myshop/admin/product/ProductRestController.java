package com.myshop.admin.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {
    @Autowired private ProductService productService;

    @PostMapping("/products/check_unique")
    public String checkUnique(@RequestParam(value = "id",required = false) Integer productId,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "alias",required = false) String alias) {
        return productService.checkUniqueNameAndAlias(productId,name,alias);
    }

}

