package com.myshop.admin.export;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CategoryExportToEXCEL extends AbstractExporter{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public CategoryExportToEXCEL() {
        workbook = new XSSFWorkbook();
    }
    public void writeHeaderLine() {
        sheet = workbook.createSheet("Categories");
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row,0,"Category ID",cellStyle);
        createCell(row,1,"Category name",cellStyle);
        createCell(row,2,"Alias",cellStyle);
        createCell(row,3,"Enabled",cellStyle);
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
    public void export(HttpServletResponse response, List<Category> categoryList) throws IOException {
        super.setHeader(response,"application/octet-stream","categories_",".xlsx");
        writeHeaderLine();
        writeDataLines(categoryList);
        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<Category> categoryList) {
        int rowIndex =1;
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);
        for(var category : categoryList) {
            int columnIndex =0;
            XSSFRow row = sheet.createRow(rowIndex++);
            createCell(row,columnIndex++,category.getId(),cellStyle);
            createCell(row,columnIndex++,category.getName(),cellStyle);
            createCell(row,columnIndex++,category.getAlias().toString(),cellStyle);
            createCell(row,columnIndex++,category.isEnabled(),cellStyle);

        }
    }
}
