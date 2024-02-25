package com.myshop.admin.export;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryExportToCSV extends AbstractExporter{
    public void export(HttpServletResponse response, List<Category> categoryList) throws IOException {
        super.setHeader(response,"text/csv","categories_",".csv");

        CsvBeanWriter writer = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String headerFiled [] = {"Category ID","Category name","Alias","Enabled"};
        writer.writeHeader(headerFiled);
        String fieldMapping [] = {"id","name","alias","enabled"};
        for(var category : categoryList) {
            category.setName(category.getName().replaceAll("--",""));
            writer.write(category,fieldMapping);
        }
        writer.close();
    }
}
