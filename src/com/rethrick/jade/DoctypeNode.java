package com.rethrick.jade;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
class DoctypeNode extends Node {
  private static final Map<String, String> DOCTYPES = new HashMap<String, String>();
  static {
    DOCTYPES.put("Basic", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\" \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">");
    DOCTYPES.put("Strict", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
    DOCTYPES.put("1.1", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
    DOCTYPES.put("Frameset", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">");
    DOCTYPES.put("Mobile", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
    DOCTYPES.put("5", "<!DOCTYPE html>");
  }

  private String charset;

  @Override public void emit(StringBuilder out, Map<String, Object> context) {
    if (text == null)
      out.append("<!DOCTYPE html>");
    else if (text.startsWith("xml") || text.startsWith("XML")) {
      if (charset == null) {
        charset = "utf-8";
        String[] split = text.split("[ ]+", 2);
        if (split.length > 1)
          charset = split[1];
      }

      out.append("<?xml version=\"1.0\" encoding=\"")
          .append(charset)
          .append("\" ?>");
    }
  }
}
