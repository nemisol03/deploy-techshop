package com.myshop.admin.customer;

import com.myshop.admin.FileUploadUtils;
import com.myshop.admin.ListInfoUtils;
import com.myshop.admin.export.CustomerExportToCSV;
import com.myshop.admin.export.CustomerExportToEXCEL;
import com.myshop.admin.export.UserExportToEXCEL;
import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
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
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "firstName", "asc", null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable("pageNum") int pageNum,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Customer> page = customerService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Customer> list = page.getContent();
        int startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
        long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
        ListInfoUtils.setInfoList(model, pageNum, sortField, sortDir, keyword, endCount, page, startCount, list);


        return "customers/customers";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.get(id);
            model.addAttribute("customer", customer);
            model.addAttribute("pageTitle", "Edit customer with id: " + id);

            return "customers/customer_form";
        } catch (CustomerNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
            return "redirect:/customers";
        }
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("customer") Customer customer,
                           @RequestParam("fileImage") MultipartFile multipartFile,
                           RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            customer.setImage(fileName);
            Customer savedUser = customerService.saveCustomer(customer);
            String uploadDir = "customers-photo/" + savedUser.getId();
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
        } else {
            if (customer.getImage().isEmpty()) {
                customer.setImage(null);
            }
            customerService.saveCustomer(customer);
        }
        redirectAttributes.addFlashAttribute("message_success", "The customer has been saved successfully");

        return "redirect:/customers";
    }

    @GetMapping("{id}/enabled/{status}")
    public String updateStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean status, RedirectAttributes redirectAttributes) {
        try {
            customerService.updateStatus(id, status);
            redirectAttributes.addFlashAttribute("message_success", "The customer has been updated status successfully");
        } catch ( CustomerNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            String dir = "customers-photo/" + id;
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.deleteDir(uploadDir);
            AmazonS3Util.removeFolder(dir);
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("message_success", "The customer has been deleted successfully");
        } catch (CustomerNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message_error", ex.getMessage());
        }


        return "redirect:/customers";
    }

    @GetMapping("/export_csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Customer> customerList = customerService.listAll() ;
        CustomerExportToCSV exportToCSV = new CustomerExportToCSV() ;
        exportToCSV.export(response,customerList);
    }

    @GetMapping("/export_excel")
    public void exportToEXCEL(HttpServletResponse response) throws IOException {
        List<Customer> customerList = customerService.listAll() ;
        CustomerExportToEXCEL exportToEXCEL = new CustomerExportToEXCEL();
        exportToEXCEL.export(response,customerList);
    }
}
