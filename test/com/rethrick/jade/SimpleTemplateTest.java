package com.rethrick.jade;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class SimpleTemplateTest {
  @Test
  public final void toHtml() throws IOException {
    String out = new Jade().process(
        "!!!\n" +
        "html\n" +
        "  head\n" +
        "    title Spectacularr!\n" +
        "    :javascript\n" +
        "      alert('hi');\n" +
        "      console.log('hi');\n" +
        "  body\n" +
        "    p#f.doo foo\n" +
        "    'pbj.doo'\n" +
        "      /This is\n" +
        "         a Comment\n" +
        "      |Scrub Kindle!\n" +
        "      -#Scrub Kindle2\n" +
        "      |Scrub Kindle3\n" +
        "      p.doo\n" +
        "        <div id='blah'>sup</div>\n",
        new HashMap<String, Object>());

    System.out.println(out);
  }
}
