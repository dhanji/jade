package com.rethrick.jade;

import org.mvel2.templates.CompiledTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class FileTemplateReader implements TemplateReader {
  private final ConcurrentMap<String, CompiledTemplate> templates =
      new ConcurrentHashMap<String, CompiledTemplate>();

  private final Jade jade;
  private final String baseDir;

  public FileTemplateReader(Jade jade) {
    this.jade = jade;
    this.baseDir = jade.options().getBaseDir();
  }

  public CompiledTemplate load(String name) {
    CompiledTemplate template = templates.get(name);
    if (null != template)
      return template;

    try {
      FileInputStream input = new FileInputStream(new File(baseDir + "/" + name + ".jade"));
      String text = Util.toString(input);

      templates.putIfAbsent(name, template = jade.compile(text));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return template;
  }
}
