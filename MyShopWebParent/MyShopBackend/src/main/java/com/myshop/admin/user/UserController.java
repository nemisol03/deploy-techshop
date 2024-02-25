package com.myshop.admin.user;

import com.myshop.admin.FileUploadUtils;
import com.myshop.admin.export.UserExportToCSV;
import com.myshop.admin.export.UserExportToEXCEL;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.Role;
import com.myshop.common.entity.User;
import com.myshop.common.exception.UserNotFoundException;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model,1,"firstName","asc",null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable("pageNum") int pageNum,
                             @RequestParam(value = "sortField",required = false,defaultValue = "firstName") String sortField,
                             @RequestParam(value = "sortDir",required = false,defaultValue = "asc") String sortDir,
                             @RequestParam(value = "keyword",required = false) String keyword
    ) {

        Page<User> page = userService.listByPage(pageNum,sortField,sortDir,keyword);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        List<User> userList = page.getContent();
        int startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if(endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("keyword",keyword);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("sortField",sortField);
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("userList", userList);
        model.addAttribute("roles", roles);

        return "users/users";
    }

    @GetMapping("/create_new")
    public String createNew(Model model) {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Create new User");
        return "users/user_form";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.get(id);
            List<Role> roles = (List<Role>) roleRepository.findAll();
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user with id: " + id);

            return "users/user_form";
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
            return "redirect:/users";
        }
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("fileImage") MultipartFile multipartFile,
                           RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            user.setPhoto(fileName);
            User savedUser = userService.saveUser(user);
            String uploadDir = "users-photo/" + savedUser.getId();
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
        } else {
            if (user.getPhoto().isEmpty()) {
                user.setPhoto(null);
            }
            userService.saveUser(user);
        }
        redirectAttributes.addFlashAttribute("message_success", "The user has been saved successfully");

        return "redirect:/users";
    }

    @GetMapping("{id}/enabled/{status}")
    public String updateStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean status, RedirectAttributes redirectAttributes) {
        try {
            userService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("message_success", "The user has been updated status successfully");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            String dir = "users-photo/" + id;
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.deleteDir(uploadDir);
            AmazonS3Util.removeFolder(dir);
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message_success", "The user has been deleted successfully");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }


        return "redirect:/users";
    }

    @GetMapping("/export_csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> userList = userService. listAll() ;
        UserExportToCSV exportToCSV = new UserExportToCSV() ;
        exportToCSV.export(response,userList);
    }

    @GetMapping("/export_excel")
    public void exportToEXCEL(HttpServletResponse response) throws IOException {
        List<User> userList = userService. listAll() ;
        UserExportToEXCEL exportToEXCEL = new UserExportToEXCEL();
        exportToEXCEL.export(response,userList);
    }

}
