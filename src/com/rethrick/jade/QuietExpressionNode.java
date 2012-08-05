package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class QuietExpressionNode extends Node {

  QuietExpressionNode(JadeOptions options) {super(options);}

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    if (!line.isEmpty())
      out.append("\n@code{\n").append(indent()).append(line).append("\n}");

    if (!getChildren().isEmpty()) {
      out.append("@code{\n");
      for (Node child : getChildren()) {
        child.emit(out, context);
      }
      out.append("\n}");
    }
  }
}
