package com.rethrick.jade;

import org.mvel2.templates.TemplateCompiler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class TextNode extends Node {
  static final Pattern START_OF_EXPR = Pattern.compile("[#][{]");

  @Override
  public void setTemplate(int indent, String line) {
    this.indent = indent;
    this.line = line;

    Matcher matcher = TextNode.START_OF_EXPR.matcher(line);
    if (matcher.find()) {
      // Compile using MVEL templates.
      compiledTemplate = TemplateCompiler.compileTemplate(matcher.replaceAll("@{"));
    }

    // Do not call super!
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    out.append('\n').append(indent()).append(text(context));
    for (Node child : getChildren()) {
      child.emit(out, context);
    }
  }
}
