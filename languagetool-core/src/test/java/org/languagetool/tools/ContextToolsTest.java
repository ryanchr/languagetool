/* LanguageTool, a natural language style checker
 * Copyright (C) 2012 Daniel Naber (http://www.danielnaber.de)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.tools;

import junit.framework.TestCase;

public class ContextToolsTest extends TestCase {

  public void testGetContext() throws Exception {
    final ContextTools contextTools = new ContextTools();
    final String context = contextTools.getContext(4, 8, "Hi, this is some nice text waiting for its error markers.");
    assertEquals("Hi, <b><font bgcolor=\"#ff8b8b\">this</font></b> is some nice text waiting for its error...", context);
    final String context2 = contextTools.getContext(3, 5, "xxx\n \nyyy");
    assertEquals("xxx<b><font bgcolor=\"#ff8b8b\">&nbsp;&nbsp;</font></b> yyy", context2);
  }

  public void testPlainTextContext() throws Exception {
    final ContextTools contextTools = new ContextTools();
    contextTools.setContextSize(5);
    final String input = "This is a test sentence. Here's another sentence with more text.";
    final String result = contextTools.getPlainTextContext(8, 14, input);
    assertEquals("...s is a test sent...\n        ^^^^^^     ", result);
  }

  public void testPlainTextContextWithLineBreaks() throws Exception {
    final ContextTools contextTools = new ContextTools();
    contextTools.setContextSize(5);
    final String input = "One.\nThis is a test sentence.\nHere's another sentence.";
    final String result = contextTools.getPlainTextContext(15, 19, input);
    assertEquals("...is a test sent...\n        ^^^^     ", result);
  }

  public void testPlainTextContextWithDosLineBreaks() throws Exception {
    final ContextTools contextTools = new ContextTools();
    contextTools.setContextSize(5);
    final String input = "One.\r\nThis is a test sentence.\r\nHere's another sentence.";
    final String result = contextTools.getPlainTextContext(16, 20, input);
    assertEquals("...is a test sent...\n        ^^^^     ", result);
  }

  public void testLargerContext() throws Exception {
    final ContextTools contextTools = new ContextTools();
    contextTools.setContextSize(100);
    final String context = contextTools.getContext(4, 8, "Hi, this is some nice text waiting for its error markers.");
    assertEquals("Hi, <b><font bgcolor=\"#ff8b8b\">this</font></b> is some nice text waiting for its error markers.", context);
  }

  public void testHtmlEscape() throws Exception {
    final ContextTools contextTools = new ContextTools();
    final String context1 = contextTools.getContext(0, 2, "Hi, this is <html>.");
    assertEquals("<b><font bgcolor=\"#ff8b8b\">Hi</font></b>, this is &lt;html&gt;.", context1);

    contextTools.setEscapeHtml(false);
    final String context2 = contextTools.getContext(0, 2, "Hi, this is <html>.");
    assertEquals("<b><font bgcolor=\"#ff8b8b\">Hi</font></b>, this is <html>.", context2);
  }

  public void testMarkers() throws Exception {
    final ContextTools contextTools = new ContextTools();
    contextTools.setErrorMarkerStart("<X>");
    contextTools.setErrorMarkerEnd("</X>");
    final String context = contextTools.getContext(0, 2, "Hi, this is it.");
    assertEquals("<X>Hi</X>, this is it.", context);
  }

}
