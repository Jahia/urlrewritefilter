package org.sample;

import javax.servlet.http.HttpServletRequest;

public class CustomRun {

    public void foo(HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("foo executed", true);
    }
}
