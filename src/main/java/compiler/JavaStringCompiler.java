package compiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;


/**
 * In-memory compile Java source code as String.
 *
 * @author michael
 */
public class JavaStringCompiler {

    private JavaCompiler compiler;
    private StandardJavaFileManager stdManager;

    public JavaStringCompiler() {
        compiler = ToolProvider.getSystemJavaCompiler();

//        String path = String.format("file:%s/lib/tools.jar", System.getProperty("java.home"));
//        try {
//            URL url = new URL(path);
//            URLClassLoader loader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
//            Class<?> compilerClass = loader.loadClass("com.sun.tools.javac.api.JavacTool");
//            this.compiler = (JavaCompiler) compilerClass.newInstance();
//        } catch (MalformedURLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
        System.out.println(System.getProperty("java.home"));
        this.stdManager = compiler.getStandardFileManager(null, null, null);
    }

    /**
     * Compile a Java source file in memory.
     *
     * @param fileName Java file name, e.g. "Test.java"
     * @param source   The source code as String.
     * @return The compiled results as Map that contains class name as key,
     * class binary as value.
     * @throws IOException If compile error.
     */
    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);
            CompilationTask task = compiler.getTask(null, manager, null, null, null, Collections.singletonList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result) {
                throw new RuntimeException("Compilation failed.");
            }
            return manager.getClassBytes();
        }
    }

    /**
     * Load class from compiled classes.
     *
     * @param name       Full class name.
     * @param classBytes Compiled results as a Map.
     * @return The Class instance.
     * @throws ClassNotFoundException If class not found.
     * @throws IOException            If load error.
     */
    public Class<?> loadClass(String name, Map<String, byte[]> classBytes) throws ClassNotFoundException, IOException {
        try (MemoryClassLoader classLoader = new MemoryClassLoader(classBytes)) {
            return classLoader.loadClass(name);
        }
    }
}