package com.rethrick.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateRuntime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class JadeVsJade4jBenchmark {

  private static final int WARM_RUNS = 15000;
  private static final int RUNS = 100000;

//  @Test
  public void compare() throws IOException {
    String template = Util.toString(JadeVsJade4jBenchmark.class.getResourceAsStream("bench.jade"));

    JadeConfiguration config = new JadeConfiguration();
    config.setPrettyPrint(true);
    JadeTemplate jade4jTemplate = config.getTemplate("test/com/rethrick/jade/bench");
    CompiledTemplate mvelTemplate = new Jade().compile(template);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", "Booya");

    assertEquals(config.renderTemplate(jade4jTemplate, map),
        TemplateRuntime.execute(mvelTemplate, map));

  }

  public static void main(String[] args) throws IOException {
    String template = Util.toString(JadeVsJade4jBenchmark.class.getResourceAsStream("bench.jade"));

    JadeConfiguration config = new JadeConfiguration();
    config.setPrettyPrint(true);
    JadeTemplate jade4jTemplate = config.getTemplate("test/com/rethrick/jade/bench");
    CompiledTemplate mvelTemplate = new Jade().compile(template);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", "Booya");

    // Warm up JVM.
    for (int i = 0; i < WARM_RUNS; i++) {
      TemplateRuntime.execute(mvelTemplate, map);
    }
    for (int i = 0; i < WARM_RUNS; i++) {
      config.renderTemplate(jade4jTemplate, map);
    }

    long start = System.currentTimeMillis();
    for (int i = 0; i < RUNS; i++) {
      TemplateRuntime.execute(mvelTemplate, map);
    }
    System.out.println("Jade-backed by MVEL: " + (System.currentTimeMillis() - start) + "ms");

    start = System.currentTimeMillis();
    for (int i = 0; i < RUNS; i++) {
      config.renderTemplate(jade4jTemplate, map);
    }
    System.out.println("Jade-backed by Jade4j: " + (System.currentTimeMillis() - start) + "ms");
  }
}
