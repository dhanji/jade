package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class ElseNode extends Node {
  private String elseIfExpression;

  @Override public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;

    if (line.startsWith("- "))
      line = line.substring(2);

    String[] split = line.split("[ ]+");
    if (split.length > 2) {
      if (!"if".equals(split[1]))
        throw new RuntimeException("Else-if expression is malformed");
      elseIfExpression = split[2];
    }
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append("@else{");

    if (elseIfExpression != null)
      out.append(elseIfExpression);
    out.append('}');

    for (Node child : getChildren()) {
      child.emit(out, context);
    }
  }
}
