package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class UnlessNode extends Node {
  private String ifExpression;

  @Override public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    ifExpression = line.substring("unless ".length());
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append("@if{!").append(ifExpression).append("}\n");
      for (Node child : getChildren()) {
        child.emit(out, context);
      }
    out.append("@end{}\n");
  }
}
