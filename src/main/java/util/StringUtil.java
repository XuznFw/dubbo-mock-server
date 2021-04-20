package util;

import com.alibaba.fastjson.JSONObject;

public class StringUtil {

    public static Object parse(String s) {
        Object result = s;
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
            result = s;
        }

        try {
            result = JSONObject.parse(s);
        } catch (Exception ignored) {
        }

        return result;
    }

}
