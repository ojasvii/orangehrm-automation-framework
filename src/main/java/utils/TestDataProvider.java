package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class TestDataProvider {

    public static Object[][] getTestData(String dataFileName, String sheetName, String testName) throws IOException {
        String filePath = System.getProperty("user.dir") + "/TestData/" + dataFileName;
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Find starting row of the test
        int testStartRow = -1;
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(0) != null && testName.equalsIgnoreCase(row.getCell(0).getStringCellValue())) {
                testStartRow = i + 1; // data starts from next row
                break;
            }
        }

        if (testStartRow == -1) {
            workbook.close();
            fis.close();
            throw new RuntimeException("Test name '" + testName + "' not found in sheet: " + sheetName);
        }

        // Get number of columns
        Row headerRow = sheet.getRow(testStartRow);
        int colCount = headerRow.getLastCellNum();

        // Count rows with data
        int dataRowCount = 0;
        for (int i = testStartRow + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null || row.getCell(0).toString().trim().isEmpty()) {
                break;
            }
            dataRowCount++;
        }

        Object[][] data = new Object[dataRowCount][1];

        for (int i = 0; i < dataRowCount; i++) {
            Hashtable<String, String> table = new Hashtable<>();
            Row row = sheet.getRow(testStartRow + 1 + i);
            for (int j = 0; j < colCount; j++) {
                String key = headerRow.getCell(j).getStringCellValue();
                Cell cell = row.getCell(j);
                String value = (cell == null) ? "" : cell.toString();
                table.put(key, value);
            }
            data[i][0] = table;
        }

        workbook.close();
        fis.close();
        return data;
    }
}
