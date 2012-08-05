package com.rethrick.jade;

import com.petebevin.markdown.MarkdownProcessor;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class MarkdownFilter implements Filter {
  private final boolean pretty;
  private final MarkdownProcessor markdown = new MarkdownProcessor();

  public MarkdownFilter(JadeOptions options) {
    pretty = options.isPretty();
  }

  @Override public String filter(String indent, String text) {
    // Strip indentation before processing text. Because markdown is so indentation sensitive.
    StringBuilder stripped = new StringBuilder();
    try {
      for (String line : Util.toLines(new StringReader(text))) {
        if (pretty)
          stripped.append(line.substring(indent.length() + 2));
        else
          stripped.append(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String processed = markdown.markdown(stripped.toString());

    // Now re-indent the text for prettiness.
    if (pretty) {
      StringBuilder unstripped = new StringBuilder();
      try {
        for (String line : Util.toLines(new StringReader(processed))) {
          unstripped.append(indent).append(line);
        }

        processed = unstripped.toString();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return processed;
  }
}
