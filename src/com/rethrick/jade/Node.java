package com.rethrick.jade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class Node {
  int indent;
  String line;
  String text;
  String tag;
  private String id;
  private String classes;
  private List<Node> children = new ArrayList<Node>();

  public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;

    // Treat literal tags differently.
    String[] split;
    if (line.startsWith("'") && line.length() > 1) {
      int end = line.indexOf("'", 1);
      tag = line.substring(1, end);
      text = line.substring(end + 1);

      if (text.isEmpty())
        text = null;
      return;
    }

    split = line.split("[ ]+", 2);
    this.tag = split[0];
    if (split.length > 1)
      this.text = split[1];

    split = tag.split("[#]", 2);
    if (split.length > 1) {
      tag = split[0];
      id = split[1];
    }

    String toSplit = id == null ? tag : id;
    split = toSplit.split("[.]", 2);
    if (split.length > 1) {
      if (id != null)
        id = split[0];
      else
        tag = split[0];
      classes = split[1];
    }
  }

  public void emit(StringBuilder out, Map<String, Object> context) {
    out.append("\n");
    // self closed tags.
    if (text == null && children.isEmpty()) {
      startTag(out).append("/>");
      return;
    }

    startTag(out).append('>');

    if (text != null)
      out.append(text);
    else {
      for (Node child : children) {
        child.emit(out, context);
      }
      out.append('\n').append(indent());
    }

    // close tag.
    out.append("</").append(tag).append('>');
  }

  private StringBuilder startTag(StringBuilder out) {
    out.append(indent()).append('<').append(tag);
    if (id != null)
      out.append(" id=\"").append(id).append("\"");

    if (classes != null)
      out.append(" class=\"").append(classes).append("\"");

    return out;
  }

  public String indent() {
    StringBuilder out = new StringBuilder(indent);
    for (int i = 0; i < indent; i++) {
      out.append(' ');
    }
    return out.toString();
  }

  public List<Node> getChildren() {
    return children;
  }
}
