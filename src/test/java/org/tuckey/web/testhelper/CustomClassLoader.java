package org.tuckey.web.testhelper;

import io.github.classgraph.ClassGraph;
import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Custom class loader that compiles Java files from `src/test/resources/custom-class-loader/`
 * into `target/custom-class-loader-classes/` and allows them to be loaded with a different class loader.
 */
public class CustomClassLoader extends ClassLoader {
    private static final File compiledClassesDirectory = new File("target", "custom-class-loader-classes");

    static {
        try {
            // build the classpath using ClassGraph
            List<URI> classpathURIs = new ClassGraph().getClasspathURIs();
            String classpath = classpathURIs.stream()
                    .map(URI::getPath)
                    .collect(Collectors.joining(File.pathSeparator));

            // get the java sources to compile
            URL sourceFolderURL = CustomClassLoader.class.getClassLoader().getResource("custom-class-loader");
            assertNotNull(sourceFolderURL);
            Collection<File> javaFiles = FileUtils.listFiles(new File(sourceFolderURL.toURI()), new String[]{"java"}, true);

            // prepare the output folder
            File buildDir = compiledClassesDirectory;
            if (buildDir.exists()) {
                FileUtils.deleteDirectory(buildDir);
            }
            assertTrue(buildDir.mkdir());

            // compile each java file
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            for (File javaFile : javaFiles) {
                String[] args = new String[]{
                        "-d", buildDir.getAbsolutePath(),
                        "-cp", classpath,
                        javaFile.getAbsolutePath()
                };
                compiler.run(null, System.out, System.err, args);
            }
        } catch (URISyntaxException | IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            // Try to load the class from the compiledClassesDirectory
            File classFile = new File(compiledClassesDirectory, name.replace('.', '/') + ".class");
            if (classFile.exists()) {
                try (FileInputStream fis = new FileInputStream(classFile)) {
                    byte[] classData = new byte[(int) classFile.length()];
                    int read = fis.read(classData);
                    assertEquals(classData.length, read);
                    return defineClass(name, classData, 0, classData.length);
                } catch (IOException ioException) {
                    throw new ClassNotFoundException("Could not load class " + name, ioException);
                }
            } else {
                throw new ClassNotFoundException("Class " + name + " not found");
            }
        }
    }

}
