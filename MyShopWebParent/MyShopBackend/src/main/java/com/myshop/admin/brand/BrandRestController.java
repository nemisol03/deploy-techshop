package com.myshop.admin.brand;

import com.myshop.admin.category.CategoryDTO;
import com.myshop.common.entity.Brand;
import com.myshop.common.entity.Category;
import com.myshop.common.exception.BrandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BrandRestController {

    @Autowired private BrandService brandService;

    @PostMapping("/brands/check_unique")
    public String checkUnique(@RequestParam(value = "brandId",required = false) Integer id,@RequestParam("name") String name) {
     return    brandService.isUniqueBrand(id,name) ? "OK" : "duplicated";
    }

    @GetMapping("/brands/{brandId}/categories")

    public List<CategoryDTO> getCategoriesByBrand(@PathVariable Integer brandId) throws BrandNotFoundException {

            Brand brand = brandService.get(brandId);
        Set<Category> categories  = brand.getCategories();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(var category : categories) {
            categoryDTOS.add(new CategoryDTO(category.getId(),category.getName()));
        }
       return categoryDTOS;
    }
}
