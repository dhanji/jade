package com.rethrick.jade;

import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class ExpressionNode extends Node {
  private Serializable expression;

  @Override
  public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    expression = MVEL.compileExpression(line);
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent()).append(MVEL.executeExpression(expression, context));
  }
}
