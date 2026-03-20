package com.intern.adminservice.utility;


import com.intern.adminservice.domain.BloodStock;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class StorageHelper {
    public static byte[] downloadExcellFromListOfBloodStock(List<BloodStock> list){
        String[] headers = {"ID" , "BLOOD GROUP" , "UNITS AVAILABLE" , "LAST UPDATED"};

        try(ByteArrayOutputStream os = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Student Data");

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }
            int i = 1;

            for (BloodStock stock : list) {
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(stock.getId());
                row.createCell(1).setCellValue(stock.getBloodGroup());
                row.createCell(2).setCellValue(stock.getUnitsAvailable());
                row.createCell(3).setCellValue(stock.getLastUpdated());
                i++;
            }

            for (int j = 0; j < headers.length; j++) {
                sheet.autoSizeColumn(j);
            }

            workbook.write(os);

            return os.toByteArray();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

//    public static byte[] downloadExcellFromListOfUsers(List<Users> list){
//        String[] headers = {"ID" , "EMAIL" , "NAME" , "PASSWORD" , "PHONE" , "ROLE" , "STATUS"};
//
//        try(ByteArrayOutputStream os = new ByteArrayOutputStream(); Workbook workbook = new XSSFWorkbook()) {
//
//            Sheet sheet = workbook.createSheet("Student Data");
//
//            Row headerRow = sheet.createRow(0);
//
//            for (int i = 0; i < headers.length; i++) {
//                headerRow.createCell(i).setCellValue(headers[i]);
//            }
//            int i = 1;
//
//            for (Users users : list) {
//                Row row = sheet.createRow(i);
//                row.createCell(0).setCellValue(users.getId());
//                row.createCell(1).setCellValue(users.getEmail());
//                row.createCell(2).setCellValue(users.getName());
//                row.createCell(3).setCellValue(users.getPassword());
//                row.createCell(4).setCellValue(users.getPhone());
//                row.createCell(5).setCellValue(users.getRole());
//                row.createCell(6).setCellValue(users.getStatus());
//                i++;
//            }
//
//            for (int j = 0; j < headers.length; j++) {
//                sheet.autoSizeColumn(j);
//            }
//
//            workbook.write(os);
//
//            return os.toByteArray();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
