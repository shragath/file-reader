package dev.demo.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadSheetFile implements InputFile {
  File file;
  String extension;
  String savePath;

  public SpreadSheetFile(File file, String extension) {
    this.file = file;
    this.extension = Util.getFileExtension(file);
    this.savePath = "new" + file.getName();
  }

  @Override
  public void parse() throws IOException {
    // Open the Excel file
    FileInputStream fis = new FileInputStream(this.file);

    // Create a workbook object based on the file extension
    Workbook workbook = null;
    Workbook newWorkbook = null;

    if (this.extension.endsWith("xlsx")) {
      workbook = new XSSFWorkbook(fis); // For .xlsx files
      newWorkbook = new XSSFWorkbook();
    } else if (this.extension.endsWith("xls")) {
      workbook = new HSSFWorkbook(fis); // For .xls files
      newWorkbook = new HSSFWorkbook();
    } else {
      System.out.println("Invalid file format. Please provide a .xls or .xlsx file.");
      fis.close();
      return;
    }

    // Read the first sheet
    Sheet sheet = workbook.getSheetAt(0); // Get the first sheet (index 0)
    Sheet newSheet = newWorkbook.createSheet("Copy");

    int lastRow = sheet.getLastRowNum();
    int newValuesStartRow = lastRow + 4;
    System.out.println("Número de filas: " + (lastRow + 1));

    // Iterate through rows
    for (Row row : sheet) {
      Row oldRow = newSheet.createRow(row.getRowNum());
      Row newRow = newSheet.createRow(row.getRowNum() + newValuesStartRow);
      // Iterate through columns in the row
      for (Cell cell : row) {
        // Print the cell's value
        switch (cell.getCellType()) {
          case STRING:
            System.out.print(cell.getStringCellValue() + "\t");
            // Valor viejo
            oldRow.createCell(cell.getColumnIndex()).setCellValue(cell.getStringCellValue());
            // Valor nuevo
            newRow
                .createCell(cell.getColumnIndex())
                .setCellValue(new StringBuilder(cell.getStringCellValue()).reverse().toString());
            break;
          case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
              // If the cell contains a date, retrieve the date value
              SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(cell.getDateCellValue());
              calendar.add(Calendar.DAY_OF_MONTH, 1);
              Date newDate = calendar.getTime();
              // Valor viejo
              oldRow
                  .createCell(cell.getColumnIndex())
                  .setCellValue(dateFormat.format(cell.getDateCellValue()));
              // Valor nuevo
              newRow.createCell(cell.getColumnIndex()).setCellValue(dateFormat.format(newDate));
              System.out.print(dateFormat.format(cell.getDateCellValue()) + "\t");
              break;
            }
            System.out.print(cell.getNumericCellValue() + "\t");
            // Valor viejo
            oldRow.createCell(cell.getColumnIndex()).setCellValue(cell.getNumericCellValue());
            // Valor nuevo
            newRow
                .createCell(cell.getColumnIndex())
                .setCellValue(cell.getNumericCellValue() + row.getRowNum());
            break;
          case BOOLEAN:
            System.out.print(cell.getBooleanCellValue() + "\t");
            // Valor viejo
            oldRow.createCell(cell.getColumnIndex()).setCellValue(cell.getBooleanCellValue());
            // Valor nuevo
            newRow.createCell(cell.getColumnIndex()).setCellValue(!cell.getBooleanCellValue());
            break;
          case FORMULA:
            System.out.print(cell.getCellFormula() + "\t");
            // Valor viejo
            oldRow.createCell(cell.getColumnIndex()).setCellValue(cell.getCellFormula());
            // Valor nuevo
            newRow.createCell(cell.getColumnIndex()).setCellValue(cell.getCellFormula());
            break;
          default:
            System.out.print("Unknown Type" + "\t");
            break;
        }
      }
      System.out.println(); // Print a new line after each row
    }

    // Write the destination workbook to a file
    File newFile = new File(this.savePath);
    FileOutputStream fileOut = new FileOutputStream(newFile);
    newWorkbook.write(fileOut);
    System.out.println("Archivo guardado en: " + newFile.getAbsolutePath());
    System.out.println();
    // Close the workbook and input stream
    workbook.close();
    newWorkbook.close();
    fis.close();
  }
}
