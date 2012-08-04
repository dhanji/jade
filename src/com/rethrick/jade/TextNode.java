package com.rethrick.jade;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class TextNode extends Node {
  static final Pattern START_OF_EXPR = Pattern.compile("[#][{]");

  @Override
  public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;
    // Do not call super!
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent()).append(Escaper.escapeXml(line));
    for (Node child : getChildren()) {
      child.emit(out, context);
    }
  }
}
