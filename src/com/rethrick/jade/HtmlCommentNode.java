package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class HtmlCommentNode extends Node {
  HtmlCommentNode(JadeOptions options) {super(options);}

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent()).append("<!-- ").append(line);

    for (Node child : getChildren()) {
      child.emit(out, context);
    }

    out.append(" -->");
  }
}
