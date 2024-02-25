package com.myshop.site.setting;

import com.myshop.common.Constants;
import com.myshop.common.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class SettingFilter implements Filter {
    @Autowired
    private SettingService settingService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)   request;
        String url = servletRequest.getRequestURL().toString();
        if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".jpg")  || url.endsWith(".png")) {
            chain.doFilter(request,response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isOAuth2 = authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof OAuth2User;


        List<Setting> settingList = settingService.listGeneralSetting();
        settingList.forEach(setting -> servletRequest.setAttribute(setting.getKey(),setting.getValue()));
        request.setAttribute("AWS_BASE_URI", Constants.AWS_BASE_URI);
        request.setAttribute("isOAuth2", isOAuth2);
        chain.doFilter(request, response);
    }
}
