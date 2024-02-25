package com.myshop.admin.export;

import com.myshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UserExportToCSV extends AbstractExporter{

    public void export(HttpServletResponse response, List<User> userList) throws IOException {
        super.setHeader(response,"text/csv","users_",".csv");

        CsvBeanWriter writer = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String headerFiled [] = {"User ID","Email","First name","Last name","Roles","Enabled"};
        writer.writeHeader(headerFiled);
        String fieldMapping [] = {"id","email","firstName","lastName","roles","enabled"};
        for(var user : userList) {
            writer.write(user,fieldMapping);
        }
        writer.close();
    }
}
