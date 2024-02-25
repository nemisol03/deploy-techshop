package com.myshop.admin.setting;

import com.myshop.common.Constants;
import com.myshop.common.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Setting> settingList = settingService.listGeneralSetting();
        settingList.forEach(setting -> servletRequest.setAttribute(setting.getKey(),setting.getValue()));
        request.setAttribute("AWS_BASE_URI", Constants.AWS_BASE_URI);
        chain.doFilter(request, response);
    }
}
