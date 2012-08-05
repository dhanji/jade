package com.rethrick.jade;

import org.mvel2.templates.CompiledTemplate;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public interface TemplateReader {
  CompiledTemplate load(String name);
}
