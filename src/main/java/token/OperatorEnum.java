package token;

public enum OperatorEnum {

    Equal("==", 1, "equal"),
    NotEqual("!=", 1, "notEqual"),
    GreaterThan(">", 1, "greaterThan"),
    LessThan("<", 1, "lessThan"),
    GreaterThanOrEqual(">=", 1, "greaterThanOrEqual"),
    LessThanOrEqual("<=", 1, "lessThanOrEqual");

    public String value;
    public Integer level;

    public Class handler = OperatorHandler.class;
    public String function;

    private OperatorEnum(String value, Integer level, String function) {
        this.value = value;
        this.level = level;
        this.function = function;
    }

    public static OperatorEnum getByValue(String value) {
        for (OperatorEnum operatorEnum : OperatorEnum.values()) {
            if (value.equals(operatorEnum.value)) {
                return operatorEnum;
            }
        }
        return null;
    }


}
