package com.rethrick.jade;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class JadeOptions {
  private boolean pretty = true;
  private String baseDir = ".";

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
}
