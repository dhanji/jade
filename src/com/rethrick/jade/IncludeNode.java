package com.rethrick.jade;

import org.mvel2.templates.TemplateRuntime;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class IncludeNode extends Node {
  private final TemplateReader templateReader;

  IncludeNode(JadeOptions options, TemplateReader templateReader) {
    super(options);
    this.templateReader = templateReader; }

  @Override public void setTemplate(int indent, String line) {
    super.setTemplate(indent, line);

    // Pre-compile.
    templateReader.load(text);
  }

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    String processed = TemplateRuntime.execute(templateReader.load(text), context).toString();

    // Re-indent.
    StringBuilder indented = new StringBuilder(processed.length());
    try {
      for (String line : Util.toLines(new StringReader(processed))) {
        indented.append(indent()).append(line).append('\n');
      }

      // Chew last EOL.
      indented.deleteCharAt(indented.length() - 1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    out.append(indented.toString());
  }
}
