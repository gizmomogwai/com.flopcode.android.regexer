package com.flopcode.android.tools;

import java.text.DecimalFormat;

public class Humanize {
  static DecimalFormat NO_AFTER_COMMA = new DecimalFormat("0");
  static DecimalFormat ONE_AFTER_COMMA = new DecimalFormat("0.0");
  public static String formatDistance(float distanceInM) {
    long roundedDistance = Math.round(distanceInM);
    if (roundedDistance < 0) {
      return "?";
    } else if (roundedDistance < 1) {
      return "<1m";
    } else if (roundedDistance < 10) {
      return "" + ONE_AFTER_COMMA.format(distanceInM) + "m";
    } else if (distanceInM < 1000) {
      return "" + NO_AFTER_COMMA.format(distanceInM) + "m";
    } else {
      return "" + ONE_AFTER_COMMA.format(distanceInM/1000f) + "km";
    }
  }
}
