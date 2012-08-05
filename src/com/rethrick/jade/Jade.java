package com.rethrick.jade;

import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class Jade {
  final ConcurrentMap<String, Filter> filters = new ConcurrentHashMap<String, Filter>();
  private final TemplateReader templateReader;
  private final JadeOptions options;

  public Jade() {
    this(new JadeOptions());
  }

  public Jade(JadeOptions options) {
    register("javascript", new JavascriptFilter());
    register("cdata", new CDataFilter());
    register("css", new CssFilter());
    register("markdown", new MarkdownFilter(options));
    register("plain", new PlainFilter());

    this.options = options;
    templateReader = new FileTemplateReader(this);
  }

  public JadeOptions options() {
    return options;
  }

  public void register(String name, Filter filter) {
    filters.put(name, filter);
  }

  public String process(String template, Map<String, Object> context) throws IOException {
    return TemplateRuntime.execute(compile(template), context).toString();

  }
  public String execute(String name, Map<String, Object> context) throws IOException {
    return TemplateRuntime.execute(templateReader.load(name), context).toString();
  }

  public CompiledTemplate compile(String template) throws IOException {
    List<String> lines = Util.toLines(new StringReader(template));

    List<Node> nodes = new ArrayList<Node>(lines.size());
    readNode(nodes, lines.listIterator(), 0, false);

    StringBuilder out = new StringBuilder();
    for (Node node : nodes) {
      node.emit(out, null);
    }

    return TemplateCompiler.compileTemplate(out.toString());
  }

  private void readNode(List<Node> nodes, ListIterator<String> iterator, int lastIndent,
                        boolean treatAsText) {
    boolean treatChildrenAsText = treatAsText;

    while (iterator.hasNext()) {
      String line = iterator.next();

      // Slice indent.
      int indent;
      for (indent = 0; indent < line.length(); indent++) {
        char c = line.charAt(indent);

        if (!Character.isWhitespace(c))
          break;
      }

      String trimmedLine = line.substring(indent);

      Node node;
      if (trimmedLine.startsWith("/")) {
        node = new HtmlCommentNode(options);
        trimmedLine = trimmedLine.substring(1);
      } else if (trimmedLine.startsWith("|")) {
        node = new TextNode(options);
        trimmedLine = trimmedLine.substring(1);
      } else if (trimmedLine.startsWith(":")) {
        node = new FilterNode(this);
        trimmedLine = trimmedLine.substring(1);
        treatChildrenAsText = true;
      } else if (trimmedLine.startsWith("=")) {
        node = new ExpressionNode(options);
        trimmedLine = trimmedLine.substring(1);
      } else if (trimmedLine.startsWith("-#")) {
        node = new IgnoredNode(options);
      } else if (trimmedLine.startsWith("include ")) {
        node = new IncludeNode(options, templateReader);
      } else if (trimmedLine.startsWith("each")) {
        node = new EachNode(options);
      } else if (trimmedLine.startsWith("if") || trimmedLine.startsWith("- if")) {
        node = new IfNode(options);
      } else if (trimmedLine.startsWith("else") || trimmedLine.startsWith("- else")) {
        node = new ElseNode(options);
      } else if (trimmedLine.startsWith("unless")) {
        node = new UnlessNode(options);
      } else if (trimmedLine.startsWith("-")) {
        node = new QuietExpressionNode(options);
        trimmedLine = trimmedLine.substring(1);
      } else if (trimmedLine.startsWith("<")) {
        // Treat raw html as text.
        node = new RawNode(options);
      } else if (trimmedLine.startsWith("!!!")) {
        // Treat raw html as text.
        node = new DoctypeNode(options);
      } else if (treatAsText) {
        node = new RawNode(options);
      } else
        node = new Node(options);

      if (!line.isEmpty()) {
        if (indent > lastIndent) {
          if (iterator.hasPrevious())
            iterator.previous();
          if (nodes.isEmpty())
            throw new RuntimeException("Bad indentation--root node must appear at the top");
          readNode(nodes.get(nodes.size() - 1).getChildren(), iterator, indent,
              treatChildrenAsText);
          treatChildrenAsText = treatAsText;
          continue;
        } else if (indent < lastIndent) {
          iterator.previous();
          return;
        }
      }

      node.setTemplate(indent, trimmedLine);

      // Else nodes are treated specially, they are not actually siblings, but
      // children of the if-node, even though they appear as siblings in the template.
      if (node instanceof ElseNode) {
        Node last = nodes.get(nodes.size() - 1);
        if (!(last instanceof IfNode))
          throw new RuntimeException("Malformed else clause (not aligned correctly with a parent if).");

        last.getChildren().add(node);
      } else
        nodes.add(node);
    }
  }
}
