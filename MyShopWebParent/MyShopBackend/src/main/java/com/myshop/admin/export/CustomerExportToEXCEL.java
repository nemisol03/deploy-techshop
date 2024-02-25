package com.myshop.admin.export;

import com.myshop.common.entity.Customer;
import com.myshop.common.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CustomerExportToEXCEL extends AbstractExporter{
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public CustomerExportToEXCEL() {
        workbook = new XSSFWorkbook();
    }
    public void writeHeaderLine() {
        sheet = workbook.createSheet("Customers");
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row,0,"Customer ID",cellStyle);
        createCell(row,1,"Email",cellStyle);
        createCell(row,2,"First name",cellStyle);
        createCell(row,3,"Last name",cellStyle);
        createCell(row,4,"Phone number",cellStyle);
        createCell(row,5,"Address",cellStyle);
        createCell(row,6,"Enabled",cellStyle);
        createCell(row,7,"Created at",cellStyle);
        createCell(row,7,"Last modified at",cellStyle);
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
    public void export(HttpServletResponse response, List<Customer> customerList) throws IOException {
        super.setHeader(response,"application/octet-stream","customers_",".xlsx");
        writeHeaderLine();
        writeDataLines(customerList);
        OutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<Customer> customerList) {
        int rowIndex =1;
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);
        for(var customer : customerList) {
            int columnIndex =0;
            XSSFRow row = sheet.createRow(rowIndex++);
            createCell(row,columnIndex++,customer.getId(),cellStyle);
            createCell(row,columnIndex++,customer.getEmail(),cellStyle);
            createCell(row,columnIndex++,customer.getFirstName(),cellStyle);
            createCell(row,columnIndex++,customer.getLastName(),cellStyle);
            createCell(row,columnIndex++, customer.getPhoneNumber(),cellStyle);
            createCell(row,columnIndex++,customer.getAddress(),cellStyle);
            createCell(row,columnIndex++,customer.getCreatedAtStr(),cellStyle);
            createCell(row,columnIndex++,customer.getModifiedAtStr(),cellStyle);

        }
    }
}
