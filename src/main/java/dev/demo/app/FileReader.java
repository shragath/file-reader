package dev.demo.app;

import java.io.IOException;

public abstract class FileReader {
  public InputFile readFile(String path) throws IOException {
    InputFile inputFile = read(path);
    inputFile.parse();

    return inputFile;
  }

  public abstract InputFile read(String path) throws IOException;
}
