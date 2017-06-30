/**
 * 
 */
package com.flopcode.android.tools;

import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchIterator {
  public static class MatchData {
    private Matcher fMatcher;
    private MatchResult fMatchResult;
    public MatchData(Pattern pattern, String input) {
      fMatcher = pattern.matcher(input);
      advance();
    }
    public boolean hasNext() {
      return fMatchResult != null;
    }
    public MatchResult getNext() {
      return fMatchResult;
    }
    public void advance() {
     if (fMatcher.find()) {
       fMatchResult = fMatcher.toMatchResult();
     } else {
       fMatchResult = null;
     }
    }
  }

  private ArrayList<MatchIterator.MatchData> fMatchData = new ArrayList<MatchIterator.MatchData>();
  public MatchIterator(String cacheData, Pattern logRow1, Pattern logRow2) {
    fMatchData.add(new MatchData(logRow1, cacheData));
    fMatchData.add(new MatchData(logRow2, cacheData));
  }
  public boolean hasNext() {
    for (MatchIterator.MatchData matchData : fMatchData) {
      if (matchData.hasNext()) {
        return true;
      }
    }
    return false;
  }
  
  public MatchResult next() {
    MatchIterator.MatchData min = null;
    for (MatchIterator.MatchData match : fMatchData) {
      if (match.hasNext()) {
        if (min == null) {
          min = match;
        } else {
          if (match.getNext().start() < min.getNext().start()) {
            min = match;
          }
        }
      }
    }
    if (min == null) {
      throw new IllegalStateException("so nicht");
    }
    
    MatchResult res = min.getNext();
    min.advance();
    return res;
  }

}