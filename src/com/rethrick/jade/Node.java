package com.rethrick.jade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class Node {
  private final JadeOptions options;

  int indent;
  String tag;
  String text;
  String line;

  private boolean empty;
  private String id;
  private String classes;
  private String wrappedExpression;
  private List<Node> children = new ArrayList<Node>();
  private boolean escape = true;

  Node(JadeOptions options) { this.options = options; }

  public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;
    this.empty = line.isEmpty();

    // Treat literal tags differently.
    String[] split;
    if (line.startsWith("'") && line.length() > 1) {
      int end = line.indexOf("'", 1);
      tag = line.substring(1, end);
      text = line.substring(end + 1);

      if (tag.isEmpty())
        tag = "div";

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
      classes = split[1].replace('.', ' ');
    }

    // This is an expression tag.
    if (tag.endsWith("=")) {
      tag = tag.substring(0, tag.length() - 1);

      if (tag.endsWith("!")) {
        escape = false;
        tag = tag.substring(0, tag.length() - 1);
      }

      wrappedExpression = text;
    } else if (text != null) {
      Matcher matcher = TextNode.START_OF_EXPR.matcher(text);
      if (matcher.find()) {
        text = matcher.replaceAll("@{");
      }
    }

    if (tag.isEmpty())
      tag = "div";
  }

  public void emit(StringBuilder out, Map<String, Object> context) {

    if (!empty) {
      out.append("\n");

      // self closed tags.
      if (text == null && children.isEmpty()) {
        startTag(out).append("/>");
        return;
      }

      startTag(out).append('>');
    }

    if (wrappedExpression != null) {
      if (escape)
        out.append("@{com.rethrick.jade.Escaper.escapeXml(");
      else
        out.append("@{");

      out.append(wrappedExpression);

      if (escape)
        out.append(")}");
      else
        out.append("}");

    } else if (text != null)
      out.append(text);
    if (!children.isEmpty()) {
      for (Node child : children) {
        child.emit(out, context);
      }
      out.append('\n').append(indent());
    }

    // close tag.
    if (!empty)
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
    if (!options.isPretty())
      return "";

    StringBuilder out = new StringBuilder(indent);
    for (int i = 0; i < indent; i++) {
      out.append(' ');
    }
    return out.toString();
  }

  public List<Node> getChildren() {
    return children;
  }

  public String text() {
    return text == null ? line : text;
  }
}
