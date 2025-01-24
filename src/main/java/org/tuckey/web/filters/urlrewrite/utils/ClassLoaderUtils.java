/*
 * ==========================================================================================
 * =                            JAHIA'S ENTERPRISE DISTRIBUTION                             =
 * ==========================================================================================
 *
 *                                  http://www.jahia.com
 *
 * JAHIA'S ENTERPRISE DISTRIBUTIONS LICENSING - IMPORTANT INFORMATION
 * ==========================================================================================
 *
 *     Copyright (C) 2002-2025 Jahia Solutions Group. All rights reserved.
 *
 *     This file is part of a Jahia's Enterprise Distribution.
 *
 *     Jahia's Enterprise Distributions must be used in accordance with the terms
 *     contained in the Jahia Solutions Group Terms &amp; Conditions as well as
 *     the Jahia Sustainable Enterprise License (JSEL).
 *
 *     For questions regarding licensing, support, production usage...
 *     please contact our team at sales@jahia.com or go to http://www.jahia.com/license.
 *
 * ==========================================================================================
 */
package org.tuckey.web.filters.urlrewrite.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for class loading.
 */
public class ClassLoaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtils.class);

    /**
     * Load a class by name using an optional class loader.
     *
     * @param className   the class name
     * @param classLoader the optional class loader
     * @return the class
     */
    public static Class<?> loadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (classLoader == null) {
            logger.debug("Loading class '{}' with the default class loader of the current class", className);
            return Class.forName(className);
        } else {
            logger.debug("Loading class '{}' with the class loader {}", className, classLoader);
            return Class.forName(className, true, classLoader);
        }
    }
}
