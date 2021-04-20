package token;

public enum SeparatorEnum {

    LeftBrackets("("),
    RightBrackets(")");

    public String value;

    private SeparatorEnum(String value) {
        this.value = value;
    }

    public static SeparatorEnum getByValue(String value) {
        for (SeparatorEnum separatorEnum : SeparatorEnum.values()) {
            if (value.equals(separatorEnum.value)) {
                return separatorEnum;
            }
        }
        return null;
    }

}
