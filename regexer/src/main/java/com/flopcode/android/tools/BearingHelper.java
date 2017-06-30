package com.flopcode.android.tools;

public class BearingHelper {
  static String[] SKY_DIRECTIONS = new String[] {"N", "NE", "NE", "E", "E", "SE", "SE", "S", "S", "SW", "SW", "W", "W", "NW", "NW", "N"};
  
  public static String getSkyDirection(float degree) {
    degree = normalizeHeading(degree);
    int idx = (int)(degree / 22.5f);
    return SKY_DIRECTIONS[idx];
  }

  public static float normalizeHeading(float value) {
    while (value < 0f) {
      value += 360f;
    }
    
    while (value >= 360f) {
      value -= 360f;
    }
    return value;
  }

}
