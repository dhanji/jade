package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class EachNode extends Node {
  private String eachExpression;

  @Override public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    // Some crazy ass rewriting. So what we do here is pass in all our child-nodes,
    // as a callable closure. Then we wrap the "each" expression as an MVEL foreach
    // and call the closure inside.
    eachExpression = line.substring("each".length()).replace(" in ", " : ");
  }

  @Override public void emit(final StringBuilder out, final Map<String, Object> context) {
    out.append("\n@foreach{").append(eachExpression).append("}");
    for (Node child : getChildren()) {
      child.emit(out, context);
    }
    out.append("@end{}");
  }
}
