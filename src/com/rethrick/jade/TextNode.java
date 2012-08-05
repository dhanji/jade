package com.rethrick.jade;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class TextNode extends Node {
  static final Pattern START_OF_EXPR = Pattern.compile("[#][{]");
  private boolean escape = true;

  @Override
  public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;
    // Do not call super!

    if (line.startsWith("!")) {
      this.line = line.substring(1);
      escape = false;
    }
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent());

    if (escape)
      out.append(Escaper.escapeXml(line));
    else
      out.append(line);


    for (Node child : getChildren()) {
      child.emit(out, context);
    }
  }
}
