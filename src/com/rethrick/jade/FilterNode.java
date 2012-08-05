package com.rethrick.jade;

import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class FilterNode extends Node {
  private final Jade jade;
  private Filter filter;

  public FilterNode(Jade jade) {
    super(jade.options());
    this.jade = jade;
  }

  @Override
  public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    filter = jade.filters.get(line);
    if (filter == null)
      throw new RuntimeException("No such filter registered :" + line);
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    StringBuilder text = new StringBuilder();
    for (Node child : getChildren()) {
      text.append(child.indent());
      child.emit(text, context);
    }

    out.append('\n').append(filter.filter(indent(), text.toString()));
  }
}
