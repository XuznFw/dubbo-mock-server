
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<String, Object> context = new HashMap<>();

    public void insert(String name, Map<String, Object> value) {
        context.put(name, value);
    }

    public Object value(String key) {
        String[] keyList = key.split("\\.");

        Object result = context;
        for (String s : keyList) {
            result = this.get(result, s);
        }

        return result;
    }

    private Object get(Object source, String name) {

        if (Map.class.isAssignableFrom(source.getClass())) {
            return ((Map) source).get(name);
        }

        try {
            Field field = source.getClass().getDeclaredField(name);
            boolean af = field.isAccessible();
            if (!af) field.setAccessible(true);
            Object result = field.get(source);
            if (!af) field.setAccessible(af);
            return result;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("VARIABLE PARSE ERROR");
        }
    }

}
