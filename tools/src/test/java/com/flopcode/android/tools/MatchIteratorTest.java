package com.flopcode.android.tools;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.flopcode.android.tools.MatchIterator;

public class MatchIteratorTest {

  @Test
  public void testMatcher() {
    Pattern a = Pattern.compile("a");
    Pattern b = Pattern.compile("b");
    MatchIterator i = new MatchIterator("abbbbbab", a, b);
    Assert.assertEquals("a", i.next().group());
    Assert.assertEquals("b", i.next().group());
    Assert.assertEquals("b", i.next().group());
    Assert.assertEquals("b", i.next().group());
    Assert.assertEquals("b", i.next().group());
    Assert.assertEquals("b", i.next().group());
    Assert.assertEquals("a", i.next().group());
    Assert.assertEquals("b", i.next().group());
  }
  
}
