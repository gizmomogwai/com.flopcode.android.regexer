/**
 * 
 */
package com.flopcode.android.tools;

public class HeadingAverage {

  private final float[] fItems;
  private int fIndex;
  private float fAverage;
  private int fCount;

  public HeadingAverage(int maxItems) {
    fItems = new float[maxItems];
    fIndex = 0;
    fCount = 0;
  }
  
  public float getValue() {
    return fAverage;
  }
  
  private void updateAverage() {
    fAverage = 0.0f;
    for (float f : fItems) {
      fAverage += f;
    }
    fAverage = fAverage / fCount;
  }

  public void add(float angleInDegree) {
    if (angleInDegree > fAverage) {
      while (angleInDegree - fAverage > 180f) {
        angleInDegree -= 360f;
      }
    } else if (angleInDegree < fAverage) {
      while (angleInDegree - fAverage < -180f) {
        angleInDegree += 360f;
      }
    }
    fItems[fIndex] = angleInDegree;
    if (fCount < fItems.length) fCount++;
    nextIndex();
    updateAverage();
  }

  private void nextIndex() {    
    fIndex = (fIndex + 1) % fItems.length;
  }

}