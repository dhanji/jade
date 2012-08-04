package com.rethrick.jade;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class Escaper {
  public static String escapeXml(String xml) {
    StringBuilder escaped = new StringBuilder(xml.length());
    for (int i = 0; i < xml.length(); i++) {
      char c = xml.charAt(i);

      if (c == '&')
        escaped.append("&amp;");
      else if (c == '<')
        escaped.append("&lt;");
      else if (c == '>')
        escaped.append("&gt;");
      else if (c == '\'')
        escaped.append("&apos;");
      else if (c == '"')
        escaped.append("&quot;");

      // Do not allow ASCII control chars, except \t, \n, \r
      else if (!(c >= 0x00 && c <= 0x1F
          && c != '\t' && c != '\n' && c != '\r'))
        escaped.append(c);
    }

    return escaped.toString();
  }
}
