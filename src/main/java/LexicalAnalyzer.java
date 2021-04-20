
import token.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    private static Map<TokenStautsEnum, Map<String, List<TokenStautsEnum>>> relation = new HashMap<>();

    private List<Token> token;

    static {
        Map<String, List<TokenStautsEnum>> literalMap = new HashMap<String, List<TokenStautsEnum>>() {
            {
                put(">", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.Operator_0);
                    add(TokenStautsEnum.Operator_1);
                }});
                put("<", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.Operator_0);
                    add(TokenStautsEnum.Operator_1);
                }});
                put("!", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.Operator_0);
                }});
                put("=", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.Operator_0);
                }});

                put("a", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.KeywordAnd_0);
                }});
                put("o", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.KeywordOr_0);
                }});

                put("(", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.LeftBrackets);
                }});
                put(")", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.RightBrackets);
                }});

                put("r", new ArrayList<TokenStautsEnum>() {{
                    add(TokenStautsEnum.KeywordRegex_0);
                }});
            }
        };
        Map<String, List<TokenStautsEnum>> operator0Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("=", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.Operator_1);
            }});
        }};
        Map<String, List<TokenStautsEnum>> operator1Map = new HashMap<>();
        Map<String, List<TokenStautsEnum>> keywordAnd0Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("n", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordAnd_1);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordAnd1Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("d", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordAnd_2);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordAnd2Map = new HashMap<>();
        Map<String, List<TokenStautsEnum>> keywordOr0Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("r", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordOr_1);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordOr1Map = new HashMap<>();
        Map<String, List<TokenStautsEnum>> leftBrackets = new HashMap<>();
        Map<String, List<TokenStautsEnum>> rightBrackets = new HashMap<>();

        Map<String, List<TokenStautsEnum>> keywordRegex0Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("e", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordRegex_1);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordRegex1Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("g", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordRegex_2);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordRegex2Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("e", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordRegex_3);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordRegex3Map = new HashMap<String, List<TokenStautsEnum>>() {{
            put("x", new ArrayList<TokenStautsEnum>() {{
                add(TokenStautsEnum.KeywordRegex_4);
            }});
        }};
        Map<String, List<TokenStautsEnum>> keywordRegex4Map = new HashMap<>();


        relation.put(TokenStautsEnum.Literal, literalMap);
        relation.put(TokenStautsEnum.Operator_0, operator0Map);
        relation.put(TokenStautsEnum.Operator_1, operator1Map);
        relation.put(TokenStautsEnum.KeywordAnd_0, keywordAnd0Map);
        relation.put(TokenStautsEnum.KeywordAnd_1, keywordAnd1Map);
        relation.put(TokenStautsEnum.KeywordAnd_2, keywordAnd2Map);
        relation.put(TokenStautsEnum.KeywordOr_0, keywordOr0Map);
        relation.put(TokenStautsEnum.KeywordOr_1, keywordOr1Map);
        relation.put(TokenStautsEnum.LeftBrackets, leftBrackets);
        relation.put(TokenStautsEnum.RightBrackets, rightBrackets);
        relation.put(TokenStautsEnum.KeywordRegex_0, keywordRegex0Map);
        relation.put(TokenStautsEnum.KeywordRegex_1, keywordRegex1Map);
        relation.put(TokenStautsEnum.KeywordRegex_2, keywordRegex2Map);
        relation.put(TokenStautsEnum.KeywordRegex_3, keywordRegex3Map);
        relation.put(TokenStautsEnum.KeywordRegex_4, keywordRegex4Map);
    }

    public LexicalAnalyzer(String message) {
        List<List<TokenStautsEnum>> statusAwareness = statusAnalysis(message);
        List<String> tokenStr = tokenization(message, statusAwareness);
        tokenStr = format(tokenStr);
        this.token = evaluat(tokenStr);
    }

    private TokenStautsEnum forecastStatus(String message, List<TokenStautsEnum> stautsList) {
        if (stautsList.size() == 1) {
            return stautsList.get(0);
        }

        char item = message.toCharArray()[0];
        List<TokenStautsEnum> choiceList = new ArrayList<>();

        for (TokenStautsEnum possibleStatus : stautsList) {
            Map<String, List<TokenStautsEnum>> value = relation.get(possibleStatus);
            if (null == value || value.size() == 0) {
                choiceList.add(possibleStatus);
                continue;
            }
            List<TokenStautsEnum> temp = value.get(Character.toString(item));
            if (null != temp && temp.size() != 0) {
                TokenStautsEnum status = forecastStatus(message.substring(1), temp);
                if (null != status) {
                    return possibleStatus;
                }
            }
        }

        if (choiceList.size() != 0) {
            return choiceList.get(0);
        }
        return null;
    }

    private List<List<TokenStautsEnum>> statusAnalysis(String message) {

        TokenStautsEnum status = TokenStautsEnum.Literal;
        List<List<TokenStautsEnum>> statusAwareness = new ArrayList<>();

        int count = 0;
        for (Character c : message.toCharArray()) {
            TokenStautsEnum newStatus = TokenStautsEnum.Literal;
            List<TokenStautsEnum> statusList = relation.get(status).get(c.toString());
            if (null != statusList && statusList.size() != 0) {
                newStatus = forecastStatus(message.substring(count + 1), statusList);
                if (null == newStatus) {
                    newStatus = TokenStautsEnum.Literal;
                }
            }

            List<TokenStautsEnum> change = new ArrayList<>();
            change.add(status);
            change.add(newStatus);
            statusAwareness.add(change);

            status = newStatus;

            count++;
        }
        return statusAwareness;
    }

    private List<String> tokenization(String message, List<List<TokenStautsEnum>> statusAwareness) {
        int count = 0;
        int point = 0, possiblePoint = 0;
        List<String> token = new ArrayList<>();

        for (List<TokenStautsEnum> awareness : statusAwareness) {
            TokenStatusTypeEnum beforeStatusType = awareness.get(0).statusType;
            TokenStatusTypeEnum afterStatusType = awareness.get(1).statusType;

            if (beforeStatusType == TokenStatusTypeEnum.Normal && afterStatusType == TokenStatusTypeEnum.Final) {
                token.add(message.substring(point, count));
                token.add(message.substring(count, count + 1));
                point = count + 1;
            } else if (beforeStatusType == TokenStatusTypeEnum.Normal && afterStatusType == TokenStatusTypeEnum.Intermediate) {
                possiblePoint = count;
            } else if (beforeStatusType == TokenStatusTypeEnum.Intermediate && afterStatusType == TokenStatusTypeEnum.Final) {
                token.add(message.substring(point, possiblePoint));
                token.add(message.substring(possiblePoint, count + 1));
                point = count + 1;
            }
            count++;
        }
        token.add(message.substring(point));
        return token;
    }

    private List<String> format(List<String> token) {
        List<String> temp = new ArrayList<>();
        for (String t : token) {
            String s = t.trim();
            if (!s.isEmpty()) {
                temp.add(s);
            }
        }
        return temp;
    }

    private List<Token> evaluat(List<String> tokenList) {

        List<Token> temp = new ArrayList<>();

        for (String t : tokenList) {
            Token token = new Token();
            token.setValue(t);
            token.setTokenType(TokenTypeEnum.Literal);

            KeywordEnum keywordEnum = KeywordEnum.getByValue(t);
            OperatorEnum operatorEnum = OperatorEnum.getByValue(t);
            SeparatorEnum separatorEnum = SeparatorEnum.getByValue(t);
            Matcher matcher = Pattern.compile("\\$\\{(.*?)}").matcher(t);

            if (null != keywordEnum) {
                token.setTokenType(TokenTypeEnum.Keyword);
            } else if (null != operatorEnum) {
                token.setTokenType(TokenTypeEnum.Operator);
            } else if (null != separatorEnum) {
                token.setTokenType(TokenTypeEnum.Separator);
            } else if (matcher.matches()) {
                token.setTokenType(TokenTypeEnum.Variable);
                token.setValue(matcher.group(1));
            }

            temp.add(token);
        }
        return temp;
    }

    public List<Token> toReversePolishNotation() {
        List<Token> output = new ArrayList<>();
        List<Token> symbolStack = new ArrayList<>();

        for (Token t : token) {
            if (t.getTokenType() == TokenTypeEnum.Keyword || t.getTokenType() == TokenTypeEnum.Operator) {

                if (symbolStack.size() != 0 && (SeparatorEnum.getByValue(symbolStack.get(symbolStack.size() - 1).getValue()) != SeparatorEnum.LeftBrackets)) {
                    Token lastToken = symbolStack.get(symbolStack.size() - 1);
                    Boolean p = lastToken.getTokenType() != TokenTypeEnum.Keyword;
                    while (p) {
                        output.add(symbolStack.remove(symbolStack.size() - 1));
                        if (symbolStack.size() != 0 && (SeparatorEnum.getByValue(symbolStack.get(symbolStack.size() - 1).getValue()) != SeparatorEnum.LeftBrackets)) {
                            p = lastToken.getTokenType() != TokenTypeEnum.Keyword;
                        } else {
                            p = Boolean.FALSE;
                        }

                    }
                }
                symbolStack.add(t);

            } else if (t.getTokenType() == TokenTypeEnum.Separator) {
                SeparatorEnum separator = SeparatorEnum.getByValue(t.getValue());
                if (null == separator) {
                    throw new IllegalArgumentException();
                }
                switch (separator) {
                    case LeftBrackets:
                        symbolStack.add(t);
                        break;
                    case RightBrackets:
                        Token lastToken = symbolStack.remove(symbolStack.size() - 1);
                        while (SeparatorEnum.getByValue(lastToken.getValue()) != SeparatorEnum.LeftBrackets) {
                            output.add(lastToken);
                            lastToken = symbolStack.remove(symbolStack.size() - 1);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } else {
                output.add(t);
            }
        }

        while (symbolStack.size() != 0) {
            output.add(symbolStack.remove(symbolStack.size() - 1));
        }
        return output;

    }

    public List<Token> getToken() {
        return this.token;
    }

}
