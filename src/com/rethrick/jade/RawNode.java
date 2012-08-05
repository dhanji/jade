package com.rethrick.jade;

import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class RawNode extends Node {
  RawNode(JadeOptions options) {
    super(options);
  }

  @Override
  public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;


    Matcher matcher = TextNode.START_OF_EXPR.matcher(line);
    if (matcher.find()) {
      // Compile using MVEL templates.
      this.line = matcher.replaceAll("@{");
    }

    // Do not call super!
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent()).append(text());
    for (Node child : getChildren()) {
      child.emit(out, context);
    }
  }
}
