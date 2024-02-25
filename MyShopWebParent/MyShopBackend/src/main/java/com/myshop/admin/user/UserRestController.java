package com.myshop.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired UserService userService;

    @PostMapping("/users/check_unique")
    public String checkUniqueEmail(@RequestParam(value = "id",required = false) Integer id,@RequestParam("email") String email) {
        return userService.isUniqueEmail(id,email) ?"OK" : "duplicated";
    }
}
