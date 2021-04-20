
import token.Token;
import token.TokenTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzerV2 {

    private String[] statusIndex = new String[]{"LITERAL", "VARIABLE_POSSIBLE", "VARIABLE_START", "VARIABLE_PROGRESSING", "VARIABLE_FINISH"};
    private String[] conditionIndex = new String[]{".", "$", "{", "}"};

    private String[][] statusTable = new String[][]{
            {"LITERAL", "LITERAL", "VARIABLE_PROGRESSING", "VARIABLE_PROGRESSING", "LITERAL"},
            {"VARIABLE_POSSIBLE", "VARIABLE_POSSIBLE", "VARIABLE_PROGRESSING", "VARIABLE_PROGRESSING", "VARIABLE_POSSIBLE"},
            {"LITERAL", "VARIABLE_START", "VARIABLE_PROGRESSING", "VARIABLE_PROGRESSING", "LITERAL"},
            {"LITERAL", "LITERAL", "VARIABLE_PROGRESSING", "VARIABLE_FINISH", "LITERAL"}
    };

    private List<Token> tokens = new ArrayList<>();

    public LexicalAnalyzerV2(String message) {
        List<String> statusList = new ArrayList<>();

        String status = statusIndex[0];

        for (Character c : message.toCharArray()) {
            Integer ci = getConditionIndex(String.valueOf(c));
            Integer si = getStatusIndex(status);
            status = statusTable[ci][si];

            statusList.add(status);
        }

        Integer tag = 0;

        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).equals("VARIABLE_START")) {
                Token token = new Token();
                token.setTokenType(TokenTypeEnum.Literal);
                token.setValue(message.substring(tag, i - 1));
                tokens.add(token);

                tag = i - 1;
            } else if (statusList.get(i).equals("VARIABLE_FINISH")) {
                Token token = new Token();
                token.setTokenType(TokenTypeEnum.Variable);
                token.setValue(message.substring(tag, i + 1));
                tokens.add(token);

                tag = i + 1;
            }
        }

        Token token = new Token();
        token.setTokenType(TokenTypeEnum.Literal);
        token.setValue(message.substring(tag, statusList.size()));
        tokens.add(token);
    }

    private Integer getStatusIndex(String v) {
        for (int i = 0; i < statusIndex.length; i++) {
            if (v.equals(statusIndex[i])) {
                return i;
            }
        }
        throw new RuntimeException("not found status");
    }

    private Integer getConditionIndex(String v) {
        for (int i = 0; i < conditionIndex.length; i++) {
            if (v.equals(conditionIndex[i])) {
                return i;
            }
        }
        return 0;
    }

    public List<Token> getTokens() {
        return tokens;
    }

}
