package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class ExpressionNode extends Node {
  ExpressionNode(JadeOptions options) {super(options);}

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n')
        .append(indent())
        .append("@{com.rethrick.jade.Escaper.escapeXml(").append(line).append(")}");
  }
}
