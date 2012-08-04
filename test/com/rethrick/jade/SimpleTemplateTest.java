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
  public final void toHtml() throws IOException {
    HashMap<String, Object> context = new HashMap<String, Object>();
    context.put("message", "Cooommmmeeennt!");

    String out = new Jade().process(
        "!!!\n" +
        "html\n" +
        "  head\n" +
        "    title Spectacularr!\n" +
        "    :javascript\n" +
        "      alert('hi');\n" +
        "      console.log('hi');\n" +
        "  body\n" +
        "    br\n" +
        "    p#f.doo foo\n" +
        "    'pbj.doo'\n" +
        "      /This is\n" +
        "         a #{message}\n" +
        "      |Scrub Kindle!\n" +
        "      -#Scrub Kindle2\n" +
        "      |Scrub Kindle3\n" +
        "      :markdown\n" +
        "        Now is the *winter* of our poop.\n" +
        "      = 1 + 2\n" +
        "      .doo\n" +
        "        <div id='blah'>sup</div>\n",
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
