package com.myshop.admin.export;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.Product;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductExportToCSV extends AbstractExporter{
    public void export(HttpServletResponse response, List<Product> productList) throws IOException {
        super.setHeader(response,"text/csv","products_",".csv");

        CsvBeanWriter writer = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String headerFiled [] = {"Product ID","Product name","Price","List price - MSRP","Short description","Full description","Enabled","In stock","Discount percent","Brand","Category","Created at","Updated at",};
        writer.writeHeader(headerFiled);
        String fieldMapping [] = {"id","name","price","listPrice","shortDescription","fullDescription","enabled","inStock","discountPercent","brandName","categoryName","createdAt","modifiedAt"};
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
        for(var product : productList) {
            product.setShortDescription(product.getShortDescription().replaceAll("<[^>]*>", ""));
            product.setFullDescription(product.getFullDescription().replaceAll("<[^>]*>", ""));
            writer.write(product,fieldMapping);
        }
        writer.close();
    }

}
