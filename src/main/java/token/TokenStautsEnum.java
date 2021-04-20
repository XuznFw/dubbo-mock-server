package token;

public enum TokenStautsEnum {

    Literal(0, TokenStatusTypeEnum.Normal),

    Operator_0(1, TokenStatusTypeEnum.Intermediate),
    Operator_1(2, TokenStatusTypeEnum.Final),
    KeywordAnd_0(3, TokenStatusTypeEnum.Intermediate),
    KeywordAnd_1(4, TokenStatusTypeEnum.Intermediate),
    KeywordAnd_2(5, TokenStatusTypeEnum.Final),
    KeywordOr_0(6, TokenStatusTypeEnum.Intermediate),
    KeywordOr_1(7, TokenStatusTypeEnum.Final),

    LeftBrackets(8, TokenStatusTypeEnum.Final),
    RightBrackets(9, TokenStatusTypeEnum.Final),

    KeywordRegex_0(10, TokenStatusTypeEnum.Intermediate),
    KeywordRegex_1(11, TokenStatusTypeEnum.Intermediate),
    KeywordRegex_2(12, TokenStatusTypeEnum.Intermediate),
    KeywordRegex_3(13, TokenStatusTypeEnum.Intermediate),
    KeywordRegex_4(14, TokenStatusTypeEnum.Final);

    public Integer code;
    public TokenStatusTypeEnum statusType;

    private TokenStautsEnum(Integer code, TokenStatusTypeEnum statusType) {
        this.code = code;
        this.statusType = statusType;
    }

}
