package com.myshop.site.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/check_unique")
    public String checkUniqueEmail(@RequestParam(value = "id",required = false) Integer id,@RequestParam("email") String email ){
        return customerService.checkUnique(id,email) ? "OK": "duplicated" ;
    }
}
