package dev.demo.app;

import java.util.regex.Matcher;

public class App {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println(Util.HELP_TEXT);
      return;
    }
    configure(args[0]);
  }

  static void configure(String arg) {
    switch (arg) {
      case "--help":
        System.out.println(Util.HELP_TEXT);
        break;
      default:
        Matcher fileMatcher = Util.FILES_PATTERN.matcher(arg);
        Boolean isFile = fileMatcher.matches();
        if (!isFile) {
          System.err.println("Argumento no reconocido: " + arg);
          break;
        }
        System.out.println("Leyendo archivo: " + arg);
        InputFile inputFile;
        try {
          if (Util.readExtension(arg).equals("csv") || Util.readExtension(arg).equals("txt")) {
            inputFile = new CsvFileReader().read(arg);
          } else if (Util.readExtension(arg).equals("xlsx")
              || Util.readExtension(arg).equals("xls")) {
            inputFile = new SpreadSheetFileReader().read(arg);
          } else {
            System.err.println("Formato de archivo no reconocido: " + arg);
          }
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }

        break;
    }
  }
}
