package com.flopcode.android.tools;

import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

public class LogFinderTest {

  @Test
  @Ignore
  public void testLogCount() throws Exception {
    FileInputStream in = new FileInputStream("ottobrunn_all.html");
    String help = IOHelper.getContent(in, "UTF-8");
    System.out.println(help);
    Pattern cacheLogs = Pattern.compile("<span id=\"CacheLogs\">(.*)</span>");

    Matcher m = cacheLogs.matcher(help);
    System.out.println(m.find());
    System.out.println(m.group(1));
  }
}
