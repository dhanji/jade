jade
====

Jade-like templating engine for Java. We pretty much clone all the features found
in [jade-lang.org](http://jade-lang.org). The main difference is that we do use
[MVEL](http://mvel.codehaus.org) instead of javascript for the expressions.

In addition to this we also clone most of the features in
 [Scalate's Jade](http://scalate.fusesource.org/documentation/jade.html) implementation.

We also ship the following filters
  * `:markdown` - requires MarkdownJ on the classpath
  * `:javascript` - wraps the inner text inside `<script>` tag
  * `:plain` - raw text dumped out
  * `:cdata` - outputs the inner text inside CDATA tag
  * `:css` - outputs the inner text inside `<style>` tag

Our Jade is about 6x faster than Jade4j, the standard linked implementation. You can run the
[benchmark yourself](https://github.com/dhanji/jade/blob/master/test/com/rethrick/jade/JadeVsJade4jBenchmark.java).

Other differences from Jade4j are that all of the following works in our impl:
  * iteration with __each__
  * '-#' Jade-level comments (no emitted content)
  * all emitted expressions are html-escaped by default
  * literal tags work (e.g. `'foo.bar'` emits `<foo.bar>`)
  * attribute tags can be MVEL expressions if using braces: `p{ 'time' : new java.util.Date() }`
  * any text in the template is a candidate for string-interpolation (not only text lines)
  * jade is much more lenient with template parsing than Jade4j

...whereas I was not able to get any of them to work in side-by-side tests.


## Usage

    new Jade().process("html\n  body\n    p hello", new HashMap<String, Object>());

This will emit:

    <html>
      <body>
        <p>hello</p>
      </body>
    </html>


Note that we use an empty `HashMap` as the page __context__ object. This is the object that
you should fill with goodies (variables) that MVEL can read out into your template.

To load the template from a file instead, use:

    new Jade().execute("mytemplate.jade", new HashMap<String, Object>());


By default, Jade uses the `FileTemplateReader` which looks for templates in the file system (you
can include templates inside each other using this scheme). To customize Jade, create your own
`JadeOptions` object:

    JadeOptions options = new JadeOptions();
    options.setBaseDir("views");

    Jade jade = new Jade(options);
    jade.execute("home.jade", new HashMap<String, Object>());


This looks for and processes a template named `home.jade` in the `views` sub-directory of the
current working directory.

### Caveats

  * Jade does not currently support the `block` keyword from Javascript's Jade
  * Please consult the MVEL language for details on how to construct expressions


#### A quick note

Thanks to the original author or Jade, and respect to the authors of Jade4j. It was not my
intention to dissect or one-up Jade4j, I implemented a Java version of Jade while not realizing
that Jade4j existed until well into the project(!). So, rather than abandon a day's work,
I ran a quick performance comparison and felt good that my effort was not wasted after all. =)