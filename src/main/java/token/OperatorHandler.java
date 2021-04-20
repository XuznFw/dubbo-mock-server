package token;


import util.StringUtil;

import java.math.BigDecimal;

public class OperatorHandler {

    public static Boolean equal(Object value1, Object value2) {

        if (value1 == null && value2 == null) return Boolean.TRUE;
        if (value1 == null && value2 != null) return Boolean.FALSE;
        if (value1 != null && value2 == null) return Boolean.FALSE;

        if (String.class.isAssignableFrom(value1.getClass())) {
            value1 = StringUtil.parse(value1.toString());
        }
        if (String.class.isAssignableFrom(value2.getClass())) {
            value2 = StringUtil.parse(value2.toString());
        }

        if (Number.class.isAssignableFrom(value1.getClass()) && Number.class.isAssignableFrom(value2.getClass())) {
            return new BigDecimal(value1.toString()).compareTo(new BigDecimal(value2.toString())) == 0;
        }

        return value1.equals(value2);
    }

    public static Boolean notEqual(Object value1, Object value2) {
        return !equal(value1, value2);
    }

    public static Boolean greaterThan(Object value1, Object value2) {
        if (value1 == null || value2 == null) return Boolean.FALSE;

        BigDecimal decimalValue1 = new BigDecimal(value1.toString());
        BigDecimal decimalValue2 = new BigDecimal(value2.toString());
        return decimalValue1.compareTo(decimalValue2) > 0;
    }

    public static Boolean lessThan(Object value1, Object value2) {
        return !greaterThan(value1, value2) && !equal(value1, value2);
    }

    public static Boolean greaterThanOrEqual(Object value1, Object value2) {
        return !lessThan(value1, value2);
    }

    public static Boolean lessThanOrEqual(Object value1, Object value2) {
        return !greaterThan(value1, value2);
    }
}
