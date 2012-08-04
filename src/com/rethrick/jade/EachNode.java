package com.rethrick.jade;

import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class EachNode extends Node {
  private Serializable ifExpression;
  private String actionName;

  @Override public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    actionName = "__$action$$" + (int)(Math.random() * 10000);

    // Some crazy ass rewriting. So what we do here is pass in all our child-nodes,
    // as a callable closure. Then we wrap the "each" expression as an MVEL foreach
    // and call the closure inside.
    ifExpression = MVEL.compileExpression(
        "foreach (" + line.substring("each".length()).replace(" in ", " : ") + ") {\n  "
            + actionName + ".call();\n"
            + "}\n");
  }

  @Override public void emit(final StringBuilder out, final Map<String, Object> context) {
    context.put(actionName, new Callable<Object>() {
      @Override public Object call() throws Exception {
        for (Node child : getChildren()) {
          child.emit(out, context);
        }
        return null;
      }
    });

    try {
      MVEL.executeExpression(ifExpression, context);
    } finally {
      context.remove(actionName);
    }
  }
}
