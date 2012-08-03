package com.rethrick.jade;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class CssFilter implements Filter {
  @Override public String filter(String indent, String text) {
    return indent + "<style>\n" + text + indent + "</style>";
  }
}
