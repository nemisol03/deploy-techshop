package com.myshop.admin.export;

import com.myshop.common.entity.Brand;
import com.myshop.common.entity.Category;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BrandExportToEXCEL extends AbstractExporter{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public BrandExportToEXCEL() {
        workbook = new XSSFWorkbook();
    }
    public void writeHeaderLine() {
        sheet = workbook.createSheet("Brands");
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row,0,"Brand ID",cellStyle);
        createCell(row,1,"Brand name",cellStyle);
        createCell(row,2,"Categories",cellStyle);
    }

    public void createCell(XSSFRow row, int columnIndex, Object value, CellStyle cellStyle) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        sheet.autoSizeColumn(columnIndex);
        if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
    }
    public void export(HttpServletResponse response, List<Category> categoryList, List<Brand> brandList) throws IOException {
        super.setHeader(response,"application/octet-stream","brands_",".xlsx");
        writeHeaderLine();
        writeDataLines(categoryList,brandList);
        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<Category> categoryList, List<Brand> brandList) {
        int rowIndex =1;
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        //allow break line when use "\n"
        cellStyle.setWrapText(true);
        //allow set text-align
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);
        for(var brand : brandList) {
            int columnIndex =0;
            XSSFRow row = sheet.createRow(rowIndex++);
            createCell(row,columnIndex++,brand.getId(),cellStyle);
            createCell(row,columnIndex++,brand.getName(),cellStyle);
            createCell(row,columnIndex++,writeCategoriesRespective(brand,categoryList),cellStyle);

        }
    }

    public String writeCategoriesRespective(Brand brand, List<Category> categoryList) {

        StringBuilder categoriesStr = new StringBuilder();
        for(var category : brand.getCategories()) {
           for(var cate : categoryList) {
               if(cate.getName().endsWith(category.getName())) {
                   categoriesStr.append(category.getName()).append("\n");
               }
           }
        }
        return categoriesStr.toString();
    }
}
