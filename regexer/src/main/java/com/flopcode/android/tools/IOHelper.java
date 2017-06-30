package com.flopcode.android.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class IOHelper {
  public interface Filter {

    public boolean accept(String line);
    
  }

  public static List<String> getLines(InputStream in, String encoding, Filter f) throws IOException {
    BufferedReader bReader = getReader(in, encoding);
    ArrayList<String> res = new ArrayList<String>();
    String line = bReader.readLine();
    while (line != null) {
      if (f.accept(line)) {
        res.add(line);
      }
      line = bReader.readLine();
    }
    return res;
  }
  
  public static List<String> getLines(InputStream in, String encoding) throws IOException {
    return getLines(in, encoding, new Filter() {
      @Override
      public boolean accept(String line) {
        return true;
      }
    });
  }
  
  private static BufferedReader getReader(InputStream in, String encoding) throws UnsupportedEncodingException {
    InputStreamReader reader = new InputStreamReader(in, encoding);
    return new BufferedReader(reader);
  }
  
  public static List<String> getLinesWithoutComments(InputStream in, String encoding) throws IOException {
    return getLines(in, encoding, new Filter() {
      @Override
      public boolean accept(String line) {
        return line.trim().startsWith("#") == false;
      }
    });
  }

  public static String getContent(InputStream in, String encoding) throws IOException {
    Reader reader = new InputStreamReader(in, encoding);
    StringBuilder res = new StringBuilder();
    
    char[] buffer = new char[8192];
    int read = reader.read(buffer);
    while (read != -1) {
      res.append(buffer, 0, read);
      read = reader.read(buffer);
    }
    return res.toString();
  }

  public static Writer getWriter(File logsFile) throws IOException {
    return new OutputStreamWriter(new FileOutputStream(logsFile), "UTF-8");
  }
  
  public static void store(String logs, File logsFile) throws IOException {
    Writer help = IOHelper.getWriter(logsFile);
    help.write(logs);
    help.close();
  }

}
