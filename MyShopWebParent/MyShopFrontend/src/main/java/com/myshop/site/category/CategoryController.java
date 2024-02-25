package com.myshop.site.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class CategoryController {

    @Autowired private CategoryService categoryService;


}
