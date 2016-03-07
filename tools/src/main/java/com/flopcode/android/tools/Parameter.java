package com.flopcode.android.tools;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Parameter implements Serializable {
  public float fValue;
  public float fMin;
  public float fMax;
	public Parameter(float min, float max, float value) {
    fMin = min;
    fMax = max;
    fValue = value;
  }
  public float getValue() {
    return fValue;
  }
  public float getMin() {
    return fMin;
  }
  public float getMax() {
    return fMax;
  }
}
