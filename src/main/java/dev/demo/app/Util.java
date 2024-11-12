package dev.demo.app;

import java.io.File;
import java.util.regex.Pattern;

public final class Util {
  public static final String SAVE_PATH = "./";

  public static final String HELP_TEXT =
      "Ejecuta la aplicacion y agrega el path del archivo al comando: java -jar"
          + " spreadsheet-reader-app.jar path/to/file.xlsx/xls/csv/txt";

  public static final Pattern FILES_PATTERN = Pattern.compile(".*\\.(xlsx|xls|csv|txt)");

  public static String getFileExtension(File file) {
    // Get the file name
    String fileName = file.getName();

    // Find the position of the last dot in the file name
    int dotIndex = fileName.lastIndexOf('.');

    // If a dot is found and it is not the first character
    if (dotIndex > 0) {
      return fileName.substring(dotIndex + 1); // Return the extension without the dot
    }

    // If no dot is found or it is the first character
    return null;
  }

  public static String readExtension(String path) {
    // Find the position of the last dot in the file name
    int dotIndex = path.lastIndexOf('.');

    // If a dot is found and it is not the first character
    if (dotIndex > 0) {
      return path.substring(dotIndex + 1); // Return the extension without the dot
    }

    // If no dot is found or it is the first character
    return null;
  }
}
