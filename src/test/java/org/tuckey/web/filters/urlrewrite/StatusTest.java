/**
 * Copyright (c) 2005-2007, Paul Tuckey
 * All rights reserved.
 * ====================================================================
 * Licensed under the BSD License. Text as follows.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   - Neither the name tuckey.org nor the names of its contributors
 *     may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.tuckey.web.filters.urlrewrite;

import junit.framework.TestCase;
import org.tuckey.web.testhelper.MockRequest;
import org.tuckey.web.testhelper.MockServletContext;
import org.tuckey.web.filters.urlrewrite.utils.Log;

import javax.servlet.http.Cookie;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * @author Paul Tuckey
 * @version $Revision: 1 $ $Date: 2006-08-01 21:40:28 +1200 (Tue, 01 Aug 2006) $
 */
public class StatusTest extends TestCase {

    public void setUp() {
        Log.setLevel("DEBUG");
    }


    public void testSimple() throws IOException {

        MockRequest hsRequest = new MockRequest();
        InputStream is = ConfTest.class.getResourceAsStream(ConfTest.BASE_XML_PATH + "conf-test1.xml");
        Conf conf = new Conf(new MockServletContext(), is, "conf-test1.xml", "conf-test1.xml");
        assertTrue(conf.getErrors().toString(), conf.isOk());
        UrlRewriter urlRewriter = new UrlRewriter(conf);
        UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();

        Status status = new Status(urlRewriter.getConf(), urlRewriteFilter);
        status.displayStatusInContainer(hsRequest);
        assertNotNull(status.getBuffer());
        assertFalse(status.getBuffer().length() == 0);

        // save it so we can view it
        File buildDir = new File("target");
        if (!buildDir.exists()) {
            assertTrue(buildDir.mkdir());
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(buildDir, "status.html")));
        try {
            bos.write(status.getBuffer().toString().getBytes());
        } finally {
            bos.close();
        }

    }

    public void testRequestInfoShowsSpecialCharactersAsText() {
        String value = "Tom & Jerry's \"notes\" <draft> été";
        String expected = "Tom &amp; Jerry&#39;s &quot;notes&quot; &lt;draft&gt; été";

        MockRequest hsRequest = new MockRequest("/status");
        hsRequest.setRequestURI("/status/" + value);
        hsRequest.setContextPath("/" + value);
        hsRequest.setMethod(value);
        hsRequest.setAuthType(value);
        hsRequest.setProtocol(value);
        hsRequest.setScheme(value);
        hsRequest.setRemoteAddr(value);
        hsRequest.setRemoteHost(value);
        hsRequest.setRequestedSessionId(value);
        hsRequest.setPathInfo("/" + value);
        hsRequest.setPathTranslated("/" + value);
        hsRequest.setQueryString(value);
        hsRequest.setRequestURL("http://example.com/" + value);
        hsRequest.setServerName(value);
        hsRequest.setRemoteUser(value);
        hsRequest.addHeader("x-" + value, value);
        Cookie cookie = new Cookie("c", value);
        cookie.setPath("/" + value);
        cookie.setDomain(value);
        cookie.setComment(value);
        hsRequest.addCookie(cookie);
        hsRequest.getSession(true).setAttribute("a" + value, value);

        InputStream is = ConfTest.class.getResourceAsStream(ConfTest.BASE_XML_PATH + "conf-test1.xml");
        Conf conf = new Conf(new MockServletContext(), is, "conf-test1.xml", "conf-test1.xml");
        Status status = new Status(conf, new UrlRewriteFilter());
        status.displayStatusInContainer(hsRequest);
        String html = status.getBuffer().toString();

        assertFalse(html, html.contains("<draft>"));
        assertTrue(html, html.contains("method: " + expected));
        assertTrue(html, html.contains("auth-type: " + expected));
        assertTrue(html, html.contains("context-path: /" + expected));
        assertTrue(html, html.contains("protocol: " + expected));
        assertTrue(html, html.contains("remote-addr: " + expected));
        assertTrue(html, html.contains("remote-host: " + expected));
        assertTrue(html, html.contains("requested-session-id: " + expected));
        assertTrue(html, html.contains("request-uri: /status/" + expected));
        assertTrue(html, html.contains("scheme: " + expected));
        assertTrue(html, html.contains("domain   : " + expected.toLowerCase(Locale.ENGLISH)));
        assertTrue(html, html.contains("path-info: /" + expected));
        assertTrue(html, html.contains("path-translated: /" + expected));
        assertTrue(html, html.contains("query-string: " + expected));
        assertTrue(html, html.contains("request-url: http://example.com/" + expected));
        assertTrue(html, html.contains("server-name: " + expected));
        assertTrue(html, html.contains("remote-user: " + expected));
        assertTrue(html, html.contains("x-" + expected + ": " + expected));
        assertTrue(html, html.contains("value    : " + expected));
        assertTrue(html, html.contains("path     : /" + expected));
        assertTrue(html, html.contains("comment  : " + expected));
        assertTrue(html, html.contains("session-attribute a" + expected + ": " + expected));
    }

}
