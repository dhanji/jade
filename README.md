Jade
====

Jade-like templating engine for Java. Pretty much clones all the features found
in [jade-lang.com](http://jade-lang.com). The main difference is that it uses
[MVEL](http://mvel.codehaus.org) instead of javascript for the expressions.

In addition to this it also clones most of the (very cool) features in
 [Scalate's Jade](http://scalate.fusesource.org/documentation/jade.html) implementation.

We ship the following filters out of the box:
  * `:markdown` - requires MarkdownJ on the classpath
  * `:javascript` - wraps the inner text inside `<script>` tag
  * `:plain` - raw text dumped out
  * `:cdata` - outputs the inner text inside CDATA tag
  * `:css` - outputs the inner text inside `<style>` tag

My version of Jade is __about 4x faster than Jade4j__, the standard implementation linked from jade-lang.com. You can run the
[benchmark yourself](https://github.com/dhanji/jade/blob/master/test/com/rethrick/jade/JadeVsJade4jBenchmark.java).

Other differences from Jade4j:
  * iteration with __each__
  * `-#` Jade-level comments (no emitted content)
  * all emitted expressions are xml-escaped by default
  * literal tags work (e.g. `'foo.bar'` emits `<foo.bar>`)
  * attribute tags can be MVEL/Java expressions if using braces: `p{ 'time' : new java.util.Date() }`
  * any text in the template is a candidate for xml-escaping (not just `|` text lines)
  * Jade is much more lenient with template parsing than Jade4j

...all this works perfectly in our Jade. whereas I was not able to get any of them
to work in side-by-side tests (plz feel free to correct me if I'm wrong).


## Usage

    new Jade().process("html\n  body\n    p hello", new HashMap<String, Object>());

This will emit:

    <html>
      <body>
        <p>hello</p>
      </body>
    </html>


Note that we use an empty `HashMap` as the page __context__ object. This is the object that
you should fill with goodies (variables) that MVEL can read out and fill into your template.

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

### Keep in mind

  * Jade does not currently support the `block` keyword from Javascript's Jade
  * Please consult the MVEL language for details on how to construct expressions
  * Add your own filters by implementing the `Filter` interface and calling `Jade.register()`


#### A quick note

Thanks to the original author of Jade, and respect to the authors of Jade4j. It was not my
intention to dissect or one-up Jade4j, I implemented a Java version of Jade while not realizing
that Jade4j existed until well into the project(!). So, rather than abandon a day's work,
I ran a quick performance comparison and felt good that my effort was not wasted after all.

Also a copyright notice--I do not own, nor am I in any way affiliated with the original author(s)
of Jade (seen at jade-lang.com). If you are really bothered by my referring to this project as Jade
please feel free to call it *MVEL Jade* or *Dhanji's Jade*. =)

License is MIT. Do what you like with it, but some credit would be nice.

2012 [Dhanji R. Prasanna](http://rethrick.com). Or find me on [twitter](http://twitter.com/dhanji)