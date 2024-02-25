package com.myshop.admin.user;

import com.myshop.admin.FileUploadUtils;
import com.myshop.admin.security.MyShopUserDetails;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired private UserService userService;

    @GetMapping("/info")
    public String viewInfoAccount(Model model, @AuthenticationPrincipal MyShopUserDetails logged) {
        User user = userService.findByEmail(logged.getUsername());
        model.addAttribute("user",user);
        model.addAttribute("pageTitle","Edit your profile");
        return "users/account_form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @AuthenticationPrincipal MyShopUserDetails loggedUser,
                           @RequestParam("fileImage") MultipartFile multipartFile,
                           RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            user.setPhoto(fileName);
            User savedUser = userService.saveAccount(user);
            String uploadDir = "users-photo/" + savedUser.getId();
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
        } else {
            if (user.getPhoto().isEmpty()) {
                user.setPhoto(null);
            }
            userService.saveAccount(user);
        }

        //update info for logged user (no required logout then login again)
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        loggedUser.setAvatar(user.getPhoto());
        redirectAttributes.addFlashAttribute("message_success", "The user has been saved successfully");

        return "redirect:/account/info";
    }

}
