package com.myshop.admin.export;

import com.myshop.common.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserExportToEXCEL extends AbstractExporter{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public UserExportToEXCEL() {
        workbook = new XSSFWorkbook();
    }
    public void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row,0,"User ID",cellStyle);
        createCell(row,1,"Email",cellStyle);
        createCell(row,2,"First name",cellStyle);
        createCell(row,3,"Last name",cellStyle);
        createCell(row,4,"Roles",cellStyle);
        createCell(row,5,"Enabled",cellStyle);
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
    public void export(HttpServletResponse response, List<User> userList) throws IOException {
        super.setHeader(response,"application/octet-stream","users_",".xlsx");
        writeHeaderLine();
        writeDataLines(userList);
        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<User> userList) {
        int rowIndex =1;
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);
        for(var user : userList) {
            int columnIndex =0;
            XSSFRow row = sheet.createRow(rowIndex++);
            createCell(row,columnIndex++,user.getId(),cellStyle);
            createCell(row,columnIndex++,user.getEmail(),cellStyle);
            createCell(row,columnIndex++,user.getFirstName(),cellStyle);
            createCell(row,columnIndex++,user.getLastName(),cellStyle);
            createCell(row,columnIndex++,user.getRoles().toString(),cellStyle);
            createCell(row,columnIndex++,user.isEnabled(),cellStyle);

        }
    }
}
