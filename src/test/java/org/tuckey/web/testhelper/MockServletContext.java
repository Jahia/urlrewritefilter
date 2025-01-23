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
package org.tuckey.web.testhelper;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * @author Paul Tuckey
 * @version $Revision: 1 $ $Date: 2006-08-01 21:40:28 +1200 (Tue, 01 Aug 2006) $
 */
public class MockServletContext implements ServletContext {

    private final Hashtable<String,Object> attributes = new Hashtable<>();

    public ServletContext getContext(String s) {
        return new MockServletContext();
    }

    public Object getAttribute(String name) {
    	return this.attributes.get(name);
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 0;
    }

    public String getMimeType(String s) {
        return null;
    }

    public Set<String> getResourcePaths(String s) {
        return null;
    }

    public URL getResource(String s) throws MalformedURLException {
        return null;
    }

    public InputStream getResourceAsStream(String s) {
        return null;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    public RequestDispatcher getNamedDispatcher(String s) {
        return null;
    }

    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    public Enumeration<Servlet> getServlets() {
        return null;
    }

    public Enumeration<String> getServletNames() {
        return null;
    }

    public void log(String s) {

    }

    public void log(Exception e, String s) {

    }

    public void log(String s, Throwable throwable) {

    }

    public String getRealPath(String s) {
        return null;
    }

    public String getServerInfo() {
        return null;
    }

    public String getInitParameter(String s) {
        return null;
    }

    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    public void setAttribute(String name, Object value) {
    	if (value != null) {
    		this.attributes.put(name, value);
    	}
    	else {
    		 this.attributes.remove(name);
    	}
    }

    public void removeAttribute(String s) {

    }

    public String getServletContextName() {
        return null;
    }

	@Override
	public String getContextPath() {
		return "/";
	}

	@Override
	public int getEffectiveMajorVersion() {
		return 0;
	}

	@Override
	public int getEffectiveMinorVersion() {
		return 0;
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public Dynamic addServlet(String servletName, String className) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public Dynamic addServlet(String servletName, Servlet servlet) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return null;
	}

	@Override
	public FilterRegistration.Dynamic addFilter(String filterName, String className) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public FilterRegistration getFilterRegistration(String filterName) {
		return null;
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return null;
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void addListener(String className) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public <T extends EventListener> void addListener(T t) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void addListener(Class<? extends EventListener> listenerClass) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public ClassLoader getClassLoader() {
		return this.getClassLoader();
	}

	@Override
	public void declareRoles(String... roleNames) {
		throw new IllegalStateException("Not supported");
	}

	@Override
	public String getVirtualServerName() {
		throw new IllegalStateException("Not supported");
	}

}
