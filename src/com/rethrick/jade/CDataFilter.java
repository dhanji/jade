package com.rethrick.jade;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class CDataFilter implements Filter {
  @Override public String filter(String indent, String text) {
    return indent
        + "//<![CDATA["
        + text
        + "\n"
        + indent + "//]]>";
  }
}
