package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Calendar;

public class ExcelDataReader {

    private String path;
    private FileInputStream fis;
    private FileOutputStream fos;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;

    // Constructor
    public ExcelDataReader(String path) throws IOException {
        this.path = path;
        createWorkbookIfNotExists(path);
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }

    /** Create empty workbook if file doesn't exist */
    private void createWorkbookIfNotExists(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            XSSFWorkbook wb = new XSSFWorkbook();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
            wb.close();
        }
    }

    /** Get row count */
    public int getRowCount(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) return 0;
        return sheet.getLastRowNum() + 1;
    }

    /** Get column count */
    public int getColumnCount(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null || sheet.getRow(0) == null) return 0;
        return sheet.getRow(0).getLastCellNum();
    }

    /** Get cell data by column name */
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0) return "";

            int colNum = -1;
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) return "";

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    colNum = i;
                }
            }
            if (colNum == -1) return "";

            row = sheet.getRow(rowNum);
            if (row == null) return "";
            cell = row.getCell(colNum);
            return formatCellValue(cell);

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /** Get cell data by column index */
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0) return "";
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) return "";

            row = sheet.getRow(rowNum);
            if (row == null) return "";
            cell = row.getCell(colNum);
            return formatCellValue(cell);

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /** Format cell value (handles string, numeric, boolean, date, formula) */
    private String formatCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return String.valueOf(cell.getDateCellValue());
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case STRING: return cellValue.getStringValue();
                    case NUMERIC: return String.valueOf(cellValue.getNumberValue());
                    case BOOLEAN: return String.valueOf(cellValue.getBooleanValue());
                    default: return "";
                }
            case BLANK: return "";
            default: return "";
        }
    }

    /** Set cell data */
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            if (rowNum <= 0) return false;

            int colNum = -1;
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) sheet = workbook.createSheet(sheetName);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
                    colNum = i;
                }
            }
            if (colNum == -1) return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);

            cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(data);

            fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /** Convert entire sheet to Object[][] (for TestNG DataProvider) */
    public Object[][] getSheetData(String sheetName) {
        int rows = getRowCount(sheetName);
        int cols = getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][cols];
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = getCellData(sheetName, j, i);
            }
        }
        return data;
    }

    /** Close workbook */
    public void close() throws IOException {
        if (workbook != null) workbook.close();
    }
}
