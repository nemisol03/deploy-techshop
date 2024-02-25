package com.myshop.admin.export;

import com.myshop.common.entity.Product;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductExportToEXCEL extends AbstractExporter{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public ProductExportToEXCEL() {
        workbook = new XSSFWorkbook();
    }
    public void writeHeaderLine() {
        sheet = workbook.createSheet("Products");
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row,0,"Product ID",cellStyle);
        createCell(row,1,"Product Name",cellStyle);
        createCell(row,2,"Price",cellStyle);
        createCell(row,3,"List price - MSRP",cellStyle);
        createCell(row,4,"Short description",cellStyle);
        createCell(row,5,"Full description",cellStyle);
        createCell(row,6,"Enabled",cellStyle);
        createCell(row,7,"In stock",cellStyle);
        createCell(row,8,"Discount percent",cellStyle);
        createCell(row,9,"Brand",cellStyle);
        createCell(row,10,"Category",cellStyle);
        createCell(row,11,"Created at",cellStyle);
        createCell(row,12,"Updated at",cellStyle);


    }

    public void createCell(XSSFRow row, int columnIndex, Object value, CellStyle cellStyle) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        sheet.autoSizeColumn(columnIndex);
        if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if(value instanceof Float) {
            cell.setCellValue((float) value);
        }else if(value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        }
        else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
    }
    public void export(HttpServletResponse response, List<Product> productList) throws IOException {
        super.setHeader(response,"application/octet-stream","products_",".xlsx");
        writeHeaderLine();
        writeDataLines(productList);
        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<Product> productList) {
        int rowIndex =1;
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);
        for(var product : productList) {
            int columnIndex =0;
            XSSFRow row = sheet.createRow(rowIndex++);
            createCell(row,columnIndex++,product.getId(),cellStyle);
            createCell(row,columnIndex++,product.getName(),cellStyle);
            createCell(row,columnIndex++,product.getPrice(),cellStyle);
            createCell(row,columnIndex++,product.getListPrice(),cellStyle);
            createCell(row,columnIndex++,product.getShortDescription().replaceAll("<[^>]*>", ""),cellStyle);
            createCell(row,columnIndex++,product.getFullDescription().replaceAll("<[^>]*>", ""),cellStyle);
            createCell(row,columnIndex++,product.isEnabled(),cellStyle);
            createCell(row,columnIndex++,product.isInStock(),cellStyle);
            createCell(row,columnIndex++,product.getDiscountPercent(),cellStyle);
            createCell(row,columnIndex++,product.getBrand().getName(),cellStyle);
            createCell(row,columnIndex++,product.getCategory().getName(),cellStyle);
            createCell(row,columnIndex++,product.getCreatedAt() !=null ? product.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd _ HH:mm:ss")) : null,cellStyle);
            createCell(row,columnIndex++,product.getUpdatedAt() != null ? product.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd _ HH:mm:ss")) : null,cellStyle);

        }
    }
}
