import compiler.JavaStringCompiler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Component
public class ExceptionFactory {

    private Map<String, Class> cache = new HashMap<>();

    private static String template = "" +
            "package %s;" +
            "" +
            "public class %s extends RuntimeException {" +
            "" +
            "    protected String msg;" +
            "    protected String code;" +
            "" +
            "    public void setCode(String code) {" +
            "        this.code = code;" +
            "    }" +
            "" +
            "    public void setMsg(String msg) {" +
            "        this.msg = msg;" +
            "    }" +
            "}";


    public RuntimeException create(String packagi, String clazz, String code, String msg) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        String filename = String.format("%s.java", clazz);
        String clazzname = String.format("%s.%s", packagi, clazz);

        Class<?> exception;

        if (!cache.containsKey(clazzname)) {
            JavaStringCompiler compiler = new JavaStringCompiler();
            Map<String, byte[]> results = compiler.compile(filename, String.format(template, packagi, clazz));
            exception = compiler.loadClass(clazzname, results);
            cache.put(clazzname, exception);
        } else {
            exception = cache.get(clazzname);
        }

        Method setCode = exception.getMethod("setCode", String.class);
        Method setMsg = exception.getMethod("setMsg", String.class);

        Object o = exception.newInstance();

        setCode.invoke(o, code);
        setMsg.invoke(o, msg);

        return (RuntimeException) o;
    }
}
