package com.myshop.admin.export;

import com.myshop.common.entity.Customer;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class CustomerExportToCSV extends AbstractExporter{

    public void export(HttpServletResponse response, List<Customer> customerList) throws IOException {
        super.setHeader(response,"text/csv","customers_",".csv");

        CsvBeanWriter writer = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String headerFiled [] = {"Customer ID","Email","First name","Last name","Address","Phone number","Enabled"};
        writer.writeHeader(headerFiled);
        String fieldMapping [] = {"id","email","firstName","lastName","address","phoneNumber","enabled"};
        for(var customer : customerList) {
            writer.write(customer,fieldMapping);
        }
        writer.close();
    }
}
