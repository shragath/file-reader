package dev.demo.app;

import java.io.File;
import java.io.IOException;

public class CsvFileReader extends FileReader {
  public InputFile read(String path) throws IOException {
    File file = new File(path);
    if (!file.exists() || !file.canRead()) {
      throw new IllegalArgumentException("El archivo no existe o no puede ser leido");
    }
    InputFile inputFile = new CsvFile(file, path);
    inputFile.parse();

    return inputFile;
  }
}
