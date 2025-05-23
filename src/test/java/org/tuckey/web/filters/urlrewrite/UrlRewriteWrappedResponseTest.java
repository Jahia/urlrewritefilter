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
import org.tuckey.web.testhelper.MockResponse;
import org.tuckey.web.filters.urlrewrite.utils.Log;

/**
 * @author Paul Tuckey
 * @version $Revision: 12 $ $Date: 2006-08-20 20:53:09 +1200 (Sun, 20 Aug 2006) $
 */
public class UrlRewriteWrappedResponseTest extends TestCase {

    MockResponse response;

    public void setUp() {
        Log.setLevel("DEBUG");
        response = new MockResponse();
    }

    public void testUrlEncode() {
        Conf conf = new Conf();
        OutboundRule rule1 = new OutboundRule();
        rule1.setFrom("/aaa");
        rule1.setTo("/bbb");
        conf.addOutboundRule(rule1);
        conf.initialise();
        UrlRewriter urlRewriter = new UrlRewriter(conf);

        MockRequest request = new MockRequest("doesn't matter");
        UrlRewriteWrappedResponse urlRewriteWrappedResponse = new UrlRewriteWrappedResponse(response, request, urlRewriter);

        assertEquals("/bbb;mockencoded=test", urlRewriteWrappedResponse.encodeURL("/aaa"));

    }


    /**
     * Goal is to be able to process 1000 urls a second for a simple rule set of 1000.
     */
    public void testLoadsOfOutboundRules() {
        // turn off logging
        Log.setLevel("ERROR");

        float testAmount = 10000; // number of times to run test
        float timePerRule = 3;  // ms per rule

        // test with 1000 rules
        Conf conf = new Conf();
        for (int i = 0; i < 1000; i++) {
            OutboundRule rule = new OutboundRule();
            rule.setFrom("^/([a-z]+)/([0-9]+)/" + i + "/$");
            rule.setTo("/blah/a/$2/");
            conf.addOutboundRule(rule);
        }
        conf.initialise();
        UrlRewriter urlRewriter = new UrlRewriter(conf);

        MockRequest request = new MockRequest("/dir/999/45/");
        // warm up
        UrlRewriteWrappedResponse urlRewriteWrappedResponse = new UrlRewriteWrappedResponse(response, request, urlRewriter);
        urlRewriteWrappedResponse.encodeURL("/aaa");

        long timeStart = System.currentTimeMillis();
        for (float i = 0; i < testAmount; i++) {
            urlRewriteWrappedResponse.encodeURL("/sdasd/asdasd/asdasd");
            if (i % 500 == 0 && i > 0) {
                System.out.println("avg so far " + ((System.currentTimeMillis() - timeStart) / i) + "ms per rule");
            }
        }
        long took = System.currentTimeMillis() - timeStart;
        System.out.println("took " + took + "ms " + (took / testAmount) + "ms per rule");
        assertTrue("should take less than " + timePerRule + "ms per rule", (took / testAmount) < timePerRule);
    }

}

