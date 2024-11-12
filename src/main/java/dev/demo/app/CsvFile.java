package dev.demo.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CsvFile implements InputFile {
  File file;
  String extension;
  String savePath;

  public CsvFile(File file, String savePath) {
    this.file = file;
    this.extension = Util.getFileExtension(file);
    this.savePath = Util.SAVE_PATH + "new" + file.getName();
  }

  @Override
  public void parse() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(this.file.getAbsolutePath()))) {
      br.lines().forEach(System.out::println);
      System.out.println("Archivo guardado en: " + this.file.getAbsolutePath());
      System.out.println();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
