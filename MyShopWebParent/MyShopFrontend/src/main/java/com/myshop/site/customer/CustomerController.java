package com.myshop.site.customer;

import com.myshop.common.AmazonS3Util;
import com.myshop.common.entity.Customer;
import com.myshop.common.entity.User;
import com.myshop.site.FileUploadUtils;
import com.myshop.site.Utility;
import com.myshop.site.security.CustomerUserDetail;
import com.myshop.site.setting.EmailSettingBag;
import com.myshop.site.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SettingService settingService;

    @GetMapping("/register")
    public String listAll(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/register";
    }

    @PostMapping("/customers/create_new")
    public String  register(@RequestParam("fileImage") MultipartFile multipartFile,
                            @ModelAttribute("customer") Customer customer,
                            HttpServletRequest servletRequest) throws MessagingException, IOException {
        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            customer.setImage(fileName);
            Customer savedCustomer = customerService.save(customer);
            sendVerificationEmail(servletRequest,customer);
            String uploadDir = "customers-photo/" + savedCustomer.getId();
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());

        }else {
            if(customer.getImage().isEmpty()) {
                customer.setImage( null);
            }
            customerService.save(customer);
        }
        return "customers/registration_success";
    }

    private void sendVerificationEmail(HttpServletRequest servletRequest, Customer customer) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettingBag = settingService.listEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettingBag);
        String toAddress = customer.getEmail();
        String mailSubject = emailSettingBag.getVerifySubject();
        String mailContent = emailSettingBag.getVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(emailSettingBag.getMailFrom(),emailSettingBag.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(mailSubject);
        mailContent = mailContent.replace("[[name]]",customer.getFullName());


        String verifyURL = Utility.getSiteURL(servletRequest) + "/verify?code=" + customer.getVerificationCode()    ;
        mailContent = mailContent.replace("[[URL]]",verifyURL   );
        helper.setText(mailContent,true);
        mailSender.send(message);

    }

    @GetMapping("/verify")
    public String verify(@RequestParam("code") String verificationCode) {
        boolean isSuccess = customerService.enable(verificationCode);

        return "customers/"+ (isSuccess ? "verify_success": "verify_fail");
    }

    @GetMapping("/customers/info")
    public String viewInfoAccount(Model model, @AuthenticationPrincipal CustomerUserDetail logged) {
        Customer customer = customerService.findByEmail(logged.getUsername());
        model.addAttribute("customer",customer);
        model.addAttribute("pageTitle","Edit your profile");
        return "customers/account_form";
    }

    @PostMapping("/customers/save")
    public String saveUser(@ModelAttribute("customer") Customer customer,
                           @AuthenticationPrincipal CustomerUserDetail loggedUser,
                           @RequestParam("fileImage") MultipartFile multipartFile,
                           RedirectAttributes redirectAttributes
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            customer.setImage(fileName);
            Customer savedUser = customerService.saveAccount(customer);
            String uploadDir = "customers-photo/" + savedUser.getId();

            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
        } else {
            if (customer.getImage().isEmpty()) {
                customer.setImage(null);
            }
            customerService.saveAccount(customer);
        }

        //update info for logged user (no required logout then login again)
        loggedUser.setFirstName(customer.getFirstName());
        loggedUser.setLastName(customer.getLastName());
        loggedUser.setAvatar(customer.getImage());
        redirectAttributes.addFlashAttribute("message_success", "The customer has been saved successfully");

        return "redirect:/customers/info";
    }


}
