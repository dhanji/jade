package com.rethrick.jade;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
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

    String out = new Jade().process(
        Util.toString(SimpleTemplateTest.class.getResourceAsStream("simple.jade")),
        context);

    System.out.println(out);
  }

  @Test
  public final void exprRegex() {
    String str = "yabba #{message} doo";

    Matcher matcher = TextNode.START_OF_EXPR.matcher(str);
    assertTrue(matcher.find());

    assertEquals(5, matcher.start());
    assertEquals(8, matcher.end());
  }
}
