package com.rethrick.jade;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class SimpleTemplateTest {
  @Test
  public final void loadLinked() throws IOException {
    HashMap<String, Object> context = new HashMap<String, Object>();
    context.put("message", "Cooommmmeeennt!");

    JadeOptions options = new JadeOptions();
    options.setBaseDir("views");

    Jade jade = new Jade(options);
    System.out.println(jade.execute("layout", context));
  }

  @Test
  public final void toHtml() throws IOException {
    HashMap<String, Object> context = new HashMap<String, Object>();
    context.put("message", "Cooommmmeeennt!");

    List<String> out = Util.toLines(new StringReader(new Jade().process(
        Util.toString(SimpleTemplateTest.class.getResourceAsStream("simple.jade")),
        context)));

    List<String> expected = Util.toLines(
        new InputStreamReader(SimpleTemplateTest.class.getResourceAsStream("simple_assertion.txt")));

    // Line-by-line comparison without trailing whitespace.
    for (int i = 0, expectedSize = expected.size(); i < expectedSize; i++) {
      String expectedLine = expected.get(i).replaceAll("[ ]+$", "");
      String actualLine = out.get(i).replaceAll("[ ]+$", "");

      assertEquals(expectedLine, actualLine);
    }
  }

  @Test
  public final void exprRegex() {
    String str = "yabba #{message} doo";

    Matcher matcher = TextNode.START_OF_EXPR.matcher(str);
    assertTrue(matcher.find());

    assertEquals(6, matcher.start());
    assertEquals(8, matcher.end());
  }
}
