package com.myshop.site;

import com.myshop.common.entity.Customer;
import com.myshop.common.exception.CustomerNotFoundException;
import com.myshop.site.customer.CustomerService;
import com.myshop.site.oauth.CustomerOAuth2User;
import com.myshop.site.security.CustomerUserDetail;
import com.myshop.site.setting.EmailSettingBag;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

public class Utility {
    public static String getSiteURL(HttpServletRequest servletRequest) {
        String url = servletRequest.getRequestURL().toString();
        return url.replace(servletRequest.getServletPath(),"");
    }

    public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settingBag) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(settingBag.getHost());
        mailSender.setPort(settingBag.getPort());
        mailSender.setUsername(settingBag.getUsername());
        mailSender.setPassword(settingBag.getPassword());

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth",settingBag.getSmtpAuth());
        properties.setProperty("mail.smtp.starttls.enable", settingBag.getSmtpSecured());
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }


    public static Customer getCustomerLoggedIn(CustomerService customerService) throws CustomerNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomerUserDetail) {
                CustomerUserDetail customerUserDetail = (CustomerUserDetail) principal;
                return customerUserDetail.getCustomer();
            }else if(principal instanceof OAuth2User) {
                CustomerOAuth2User oAuth2User = (CustomerOAuth2User) principal;
                String email = oAuth2User.getEmail();
                Customer customer = customerService.findByEmail(email);
                return customer;
            }

            else {
                throw new CustomerNotFoundException("No authenticated customer found");
            }
        } else {
            throw new CustomerNotFoundException("No authenticated customer found");
        }
    }

    public static <T> void setInfoList(Model model, int pageNum, String sortField, String sortDir, String keyword, long endCount, Page<T> page, int startCount, List<T> list) {
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sortField", sortField);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("list", list);
    }
}
