package com.myshop.site.oauth;

import com.myshop.common.entity.AuthenticationType;
import com.myshop.common.entity.Customer;
import com.myshop.site.customer.CustomerService;
import com.myshop.site.security.CustomerUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        CustomerUserDetail customerUserDetail = (CustomerUserDetail) authentication.getPrincipal();
    Customer customer = customerUserDetail.getCustomer();
        String email = customer.getEmail();

        Customer customerByEmail = customerService.getByEmail(email);
            customerService.updateAuthType(customerByEmail, AuthenticationType.DATABASE);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
