package com.myshop.admin.category;

import com.myshop.admin.FileUploadUtils;
import com.myshop.admin.export.CategoryExportToCSV;
import com.myshop.admin.export.CategoryExportToEXCEL;
import com.myshop.admin.export.UserExportToCSV;
import com.myshop.admin.export.UserExportToEXCEL;
import com.myshop.admin.user.UserService;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.Category;
import com.myshop.common.entity.Role;
import com.myshop.common.entity.User;
import com.myshop.common.exception.CategoryNotFoundException;
import com.myshop.common.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model,1,"name","asc",null);
    }
    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable("pageNum") int pageNum,
                             @RequestParam(value = "sortField",required = false,defaultValue = "firstName") String sortField,
                             @RequestParam(value = "sortDir",required = false,defaultValue = "asc") String sortDir,
                             @RequestParam(value = "keyword",required = false) String keyword) {

        PageCategoryInfo pageInfo = new PageCategoryInfo();

        List<Category> categoryList = categoryService.listByPage(pageInfo,pageNum,sortField,sortDir,keyword);
        int startCount = (pageNum - 1) * CategoryService.ROOT_CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + CategoryService.ROOT_CATEGORIES_PER_PAGE - 1;
        if(endCount > pageInfo.getTotalElements()) {
            endCount = pageInfo.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("keyword",keyword);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("sortField",sortField);
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalPages",pageInfo.getTotalPages());
        model.addAttribute("totalItems",pageInfo.getTotalElements());
        model.addAttribute("categoryList",categoryList);
        return "categories/categories";
    }

    @GetMapping("/create_new")
    public String createCategory(Model model) {
        List<Category> categories = categoryService.listHierarchicalUseInForm();
        model.addAttribute("categories",categories);
        model.addAttribute("category",new Category());
        model.addAttribute("pageTitle","Create new category");
        return "categories/category_form";
    }
    @GetMapping("/edit/{id}")
    public String editCategory(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.get(id);
            List<Category> categories = categoryService.listHierarchicalUseInForm();

            model.addAttribute("category", category);
            model.addAttribute("categories",categories);

            model.addAttribute("pageTitle", "Edit category with id: " + id);

            return "categories/category_form";
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
            return "redirect:/categories";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            String dir = "categories-photo/" + id;
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.deleteDir(uploadDir);
            AmazonS3Util.removeFolder(dir);

            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message_success", "The category has been deleted successfully");
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }


        return "redirect:/categories";
    }


    @PostMapping("/save")
    public String saveBrand(@ModelAttribute("category") Category category,
                           @RequestParam("fileImage") MultipartFile multipartFile,
                           RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            category.setImage(fileName);
            Category savedCategory = categoryService.saveCategory(category);
            String uploadDir = "categories-photo/" + savedCategory.getId();
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        } else {

            categoryService.saveCategory(category);
        }
        redirectAttributes.addFlashAttribute("message_success", "The category has been saved successfully");

        return "redirect:/categories";
    }

    @GetMapping("{categoryId}/enabled/{status}")
    public String updateStatus(@PathVariable("categoryId") Integer id, @PathVariable("status") boolean status,RedirectAttributes redirectAttributes) {
        try {
            categoryService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("message_success", "The category has been updated status successfully");
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }
        return "redirect:/categories";
    }


    @GetMapping("/export_csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Category> categoryList = categoryService.listHierarchicalUseInForm() ;
        CategoryExportToCSV exportToCSV = new CategoryExportToCSV() ;
        exportToCSV.export(response,categoryList);
    }

    @GetMapping("/export_excel")
    public void exportToEXCEL(HttpServletResponse response) throws IOException {
        List<Category> categoryList = categoryService.listHierarchicalUseInForm() ;
        CategoryExportToEXCEL exportToEXCEL = new CategoryExportToEXCEL();
        exportToEXCEL.export(response,categoryList);
    }
}
