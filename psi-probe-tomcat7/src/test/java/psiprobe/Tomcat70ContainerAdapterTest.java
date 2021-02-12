/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package psiprobe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.catalina.Context;
import org.apache.catalina.Valve;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.jasper.JspCompilationContext;
import org.apache.naming.resources.Resource;
import org.apache.naming.resources.ResourceAttributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import psiprobe.model.ApplicationResource;

/**
 * The Class Tomcat70ContainerAdapterTest.
 */
class Tomcat70ContainerAdapterTest {

  /** The context. */
  @Mocked
  Context context;

  /** The resource. */
  @Mocked
  DirContext resource;

  /**
   * Creates the valve.
   */
  @Test
  void createValve() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    Valve valve = adapter.createValve();
    assertEquals("psiprobe.Tomcat70AgentValve[Container is null]", valve.toString());
  }

  /**
   * Can bound to null.
   */
  @Test
  void canBoundToNull() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertFalse(adapter.canBoundTo(null));
  }

  /**
   * Can bound to tomcat7.
   */
  @Test
  void canBoundToTomcat7() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertTrue(adapter.canBoundTo("Apache Tomcat/7.0"));
  }

  /**
   * Can bound to tom ee.
   */
  @Test
  void canBoundToTomEE() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertTrue(adapter.canBoundTo("Apache Tomcat (TomEE)/7.0"));
  }

  /**
   * Can bound to nsjsp7.
   */
  @Test
  void canBoundToNsJsp7() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertTrue(adapter.canBoundTo("NonStop(tm) Servlets For JavaServer Pages(tm) v7.0"));
  }

  /**
   * Can bound to pivotal7.
   */
  @Test
  void canBoundToPivotal7() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertTrue(adapter.canBoundTo("Pivotal tc..../7.0"));
  }

  /**
   * Can bound to other.
   */
  @Test
  void canBoundToOther() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertFalse(adapter.canBoundTo("Other"));
  }

  /**
   * Filter mappings.
   */
  @Test
  void filterMappings() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    FilterMap map = new FilterMap();
    map.addServletName("psi-probe");
    map.addURLPattern("/psi-probe");
    assertEquals(2, adapter.getFilterMappings(map, "dispatcherMap", "filterClass").size());
  }

  /**
   * Creates the jsp compilation context.
   */
  @Test
  void createJspCompilationContext() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    JspCompilationContext context = adapter.createJspCompilationContext("name", null, null, null,
        ClassLoader.getSystemClassLoader());
    assertEquals("org.apache.jsp.name", context.getFQCN());
  }

  /**
   * Adds the context resource link.
   *
   * @throws NamingException the naming exception
   */
  @Test
  void addContextResourceLink() throws NamingException {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    adapter.addContextResourceLink(context, new ArrayList<ApplicationResource>(), false);
  }

  /**
   * Adds the context resource.
   *
   * @throws NamingException the naming exception
   */
  @Test
  void addContextResource() throws NamingException {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    adapter.addContextResource(context, new ArrayList<ApplicationResource>(), false);
  }

  /**
   * Gets the application filter maps.
   */
  @Test
  void applicationFilterMaps() {
    Assertions.assertNotNull(new Expectations() {
      {
        context.findFilterMaps();
        result = new FilterMap();
      }
    });

    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertEquals(0, adapter.getApplicationFilterMaps(context).size());
  }

  /**
   * Application filters.
   */
  @Test
  void applicationFilters() {
    Assertions.assertNotNull(new Expectations() {
      {
        context.findFilterDefs();
        result = new FilterDef();
      }
    });

    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertEquals(1, adapter.getApplicationFilters(context).size());
  }

  /**
   * Application init params.
   */
  @Test
  void applicationInitParams() {
    Assertions.assertNotNull(new Expectations() {
      {
        context.findApplicationParameters();
        result = new ApplicationParameter();
      }
    });
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertEquals(0, adapter.getApplicationInitParams(context).size());
  }

  /**
   * Resource exists.
   */
  @Test
  void resourceExists() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertFalse(adapter.resourceExists("name", context));
  }

  /**
   * Resource stream.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws NamingException Signals that a Naming exception has occurred.
   */
  @Test
  void resourceStream() throws IOException, NamingException {
    Assertions.assertNotNull(new Expectations() {
      {
        resource.lookup(this.anyString);
        this.result = new Resource();
      }
    });

    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    adapter.getResourceStream("name", context);
  }

  /**
   * Resource attributes.
   *
   * @throws NamingException Signals that a Naming exception has occurred.
   */
  @Test
  void resourceAttributes() throws NamingException {
    Assertions.assertNotNull(new Expectations() {
      {
        resource.getAttributes(this.anyString);
        this.result = new ResourceAttributes();
      }
    });

    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    adapter.getResourceAttributes("name", context);
  }

  /**
   * Gets the naming token.
   */
  @Test
  void getNamingToken() {
    final Tomcat70ContainerAdapter adapter = new Tomcat70ContainerAdapter();
    assertEquals(context, adapter.getNamingToken(context));
  }

}
