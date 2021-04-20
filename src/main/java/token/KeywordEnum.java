package token;

public enum KeywordEnum {

    And("and", 0, "and"),
    Or("or", 0, "or"),
    Regex("regex", 0, "regex");

    public String value;
    public Integer level;

    public Class handler = KeywordHandler.class;
    public String function;

    private KeywordEnum(String value, Integer level, String function) {
        this.value = value;
        this.level = level;
        this.function = function;
    }

    public static KeywordEnum getByValue(String value) {
        for (KeywordEnum keywordEnum : KeywordEnum.values()) {
            if (value.equals(keywordEnum.value)) {
                return keywordEnum;
            }
        }
        return null;
    }

}
