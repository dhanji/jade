package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class TextNode extends Node {
  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent()).append(line);
    for (Node child : getChildren()) {
      child.emit(out, context);
    }
  }
}
