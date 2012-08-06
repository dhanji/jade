package com.rethrick.jade;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class JadeOptions {
  private boolean pretty = true;
  private boolean cache = false;
  private String baseDir = ".";
  private TemplateReader templateReader;

  public boolean isCache() {
    return cache;
  }

  public void setCache(boolean cache) {
    this.cache = cache;
  }

  public boolean isPretty() {
    return pretty;
  }

  public void setPretty(boolean pretty) {
    this.pretty = pretty;
  }

  public String getBaseDir() {
    return baseDir;
  }

  public void setBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public TemplateReader getTemplateReader() {
    return templateReader;
  }

  public void setTemplateReader(TemplateReader templateReader) {
    this.templateReader = templateReader;
  }
}
