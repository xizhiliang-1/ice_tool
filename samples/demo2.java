import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelAppender {
    public static void main(String[] args) {
        String filePath = "path/to/existing/file.xlsx";

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet

            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell cell1 = newRow.createCell(0);
            cell1.setCellValue("Data 1");

            Cell cell2 = newRow.createCell(1);
            cell2.setCellValue("Data 2");

            // Add more cells as needed

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
