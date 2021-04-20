package token;


import util.StringUtil;

public class KeywordHandler {

    public static Boolean and(Object value1, Object value2) {

        if (String.class.isAssignableFrom(value1.getClass())) {
            value1 = StringUtil.parse(value1.toString());
        }
        if (String.class.isAssignableFrom(value2.getClass())) {
            value2 = StringUtil.parse(value2.toString());
        }

        if (Boolean.class.isAssignableFrom(value1.getClass()) && Boolean.class.isAssignableFrom(value2.getClass())) {
            return (Boolean) value1 && (Boolean) value2;
        } else {
            return Boolean.FALSE;
        }
    }

    public static Boolean or(Object value1, Object value2) {
        if (String.class.isAssignableFrom(value1.getClass())) {
            value1 = StringUtil.parse(value1.toString());
        }
        if (String.class.isAssignableFrom(value2.getClass())) {
            value2 = StringUtil.parse(value2.toString());
        }

        if (Boolean.class.isAssignableFrom(value1.getClass()) && Boolean.class.isAssignableFrom(value2.getClass())) {
            return (Boolean) value1 || (Boolean) value2;
        } else {
            return Boolean.FALSE;
        }
    }

    public static Boolean regex(Object value1, Object value2) {
        value1 = StringUtil.parse(value1.toString());
        value2 = StringUtil.parse(value2.toString());
        return value1.toString().matches(value2.toString());
    }

}
