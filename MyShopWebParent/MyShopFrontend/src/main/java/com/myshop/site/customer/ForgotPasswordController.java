package com.myshop.site.customer;


import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.site.Utility;
import com.myshop.site.setting.EmailSettingBag;
import com.myshop.site.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SettingService settingService;

    @GetMapping("/forgot_password")
    public String showRequestForm() {
        return "customers/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String handleForgotPassword(HttpServletRequest req, Model model) {
        String email = req.getParameter("email");
        try {
            String token = customerService.updateResetPasswordToken(email);
            String linkSend = Utility.getSiteURL(req) + "/reset_password?token=" + token;
            sendVerificationEmail(linkSend, email);
            model.addAttribute("message", "We have sent the reset password link to your email");
        } catch (CustomerNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", "Could not send email");
        }

        return "customers/forgot_password_form";
    }

    private void sendVerificationEmail(String link, String email) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettingBag = settingService.listEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettingBag);
        String toAddress = email;
        String mailSubject = "Reset Your Password";
        String mailContent = "<p>Hello,</p>"
                + "<p>We received a request to reset your password. If you didn't request this, you can ignore this email.</p>"
                + "<p>To reset your password, click the following link:</p>"
                + "<p><a href=\"" + link + "\"><b><u>Click to reset your password</u></b></a></p>"
                + "<p>Thank you!</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // Enable HTML content
        helper.setFrom(emailSettingBag.getMailFrom(), emailSettingBag.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        Customer customer = customerService.getResetPasswordToken(token);
        if (customer != null) {
            model.addAttribute("token", token);
            return "customers/reset_password_form";
        } else {
            model.addAttribute("message", "Invalid token");
            model.addAttribute("pageTitle", "Invalid token");
            return "notification";
        }
    }

    @PostMapping("/reset_password")
    public String resetPassword(HttpServletRequest req, Model model) {
        String token = req.getParameter("token");
        String newPassword = req.getParameter("password");
        try {
            customerService.updateResetPassword(token, newPassword);
            model.addAttribute("message", "Password was reset successfully!To continue, please login again.");
        } catch (CustomerNotFoundException e) {
            model.addAttribute("message", "Could not find any customer");
            model.addAttribute("pageTitle", "Not found");
        }
        return "notification";
    }

}
