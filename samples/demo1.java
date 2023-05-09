import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {

    public static void main(String[] args) {
        List<MyObject> dataList = getDataList(); // Assuming you have a list of objects

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Customized header with beautiful formatting
            CellStyle headerCellStyle = createHeaderCellStyle(workbook);
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Header 1", "Header 2", "Header 3"}; // Replace with your actual header names
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
                sheet.autoSizeColumn(i); // Adjust column width to fit content
            }

            // Insert rows from the Java object list
            int rowIndex = 1; // Start from the second row
            for (MyObject obj : dataList) {
                Row dataRow = sheet.createRow(rowIndex++);
                // Set data values to the corresponding cells in each row
                Cell cell1 = dataRow.createCell(0);
                cell1.setCellValue(obj.getValue1());
                // Repeat for other columns and data fields

                // Apply all borders to the data row
                CellStyle dataCellStyle = createDataCellStyle(workbook);
                for (int i = 0; i < headers.length; i++) {
                    dataRow.getCell(i).setCellStyle(dataCellStyle);
                }
            }

            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream("output.xlsx")) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a cell style for the header row with beautiful formatting
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    // Create a cell style for the data rows with all borders
    private static CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    // Replace with your actual implementation to retrieve the data list
    private static List<MyObject> getDataList() {
        // Your logic to fetch data from a source and return the list
        return null;
    }

    // Define your object class with corresponding fields and getters
    private static class MyObject {
        private String value1;
        // Other fields and getters
    }
}
