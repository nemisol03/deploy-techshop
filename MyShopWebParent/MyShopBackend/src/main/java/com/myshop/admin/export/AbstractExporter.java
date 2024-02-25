package com.myshop.admin.export;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {
    public  void setHeader(HttpServletResponse response, String contentType, String prefixFileName, String extension) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = prefixFileName + timestamp + extension;
        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=" + fileName;

        response.setHeader(headerKey,headerValue);
    }

}
