package com.myshop.site.oauth;

import com.myshop.common.entity.AuthenticationType;
import com.myshop.common.entity.Customer;
import com.myshop.site.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        CustomerOAuth2User oAuth2User = (CustomerOAuth2User) authentication.getPrincipal();

        String name = oAuth2User.getName();
        String email = oAuth2User.getEmail();
        String clientName = oAuth2User.getClientName();
//        System.out.println("Client name: " + clientName);
//        System.out.println("onAuthenticationSuccess: name: " + name + " - email: " + email );

        AuthenticationType authType = getAuthType(clientName);

        Customer customer = customerService.getByEmail(email);
        if(customer== null) {
            customerService.addCustomerUponOAuthLogin(name,email,authType);
        }else {
            customerService.updateAuthType(customer, authType);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    public AuthenticationType getAuthType(String clientName) {
        if(clientName.equals("Google")) {
            return AuthenticationType.GOOGLE;
        }
        else return AuthenticationType.DATABASE;
    }
}
