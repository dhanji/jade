package com.rethrick.jade;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class JavascriptFilter implements Filter {
  @Override public String filter(String indent, String text) {
    return indent
        + "<script type=\"text/javascript\">\n"
        + indent
        + "  //<![CDATA[\n"
        + text
        + indent
        + "  //]]>\n"
        + indent
        + "</script>";
  }
}
