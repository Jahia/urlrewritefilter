package org.tuckey.web.filters.urlrewrite.sample;

import org.tuckey.web.filters.urlrewrite.Conf;
import org.w3c.dom.Document;

import java.io.InputStream;


public class SampleConfExt extends Conf {

    public SampleConfExt() {
        // do something
    }

    protected synchronized void loadDom(InputStream inputStream, ClassLoader classLoader) {
        // do something
        super.loadDom(inputStream, classLoader);
    }

    protected void processConfDoc(Document doc) {
        // do something else
        super.processConfDoc(doc, null);
    }
}
