package com.rethrick.jade;

import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class SilentExpressionNode extends Node {
  private Serializable expression;

  @Override
  public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    if (!line.isEmpty())
      expression = MVEL.compileExpression(line);
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    if (expression != null)
      MVEL.executeExpression(expression, context);

    for (Node child : getChildren()) {
      if (!child.line.isEmpty())
        MVEL.eval(child.line, context, context);
    }
  }
}
