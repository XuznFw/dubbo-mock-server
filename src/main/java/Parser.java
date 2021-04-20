import com.alibaba.fastjson.JSONObject;
import com.fshows.fubei.testcenter.service.mock.token.KeywordEnum;
import com.fshows.fubei.testcenter.service.mock.token.OperatorEnum;
import com.fshows.fubei.testcenter.service.mock.token.Token;
import com.fshows.fubei.testcenter.service.mock.token.TokenTypeEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static Boolean parse(List<Token> tokens, Context context) {
        List<Object> newTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getTokenType() == TokenTypeEnum.Variable) {
                Object value = context.value(token.getValue());
                newTokens.add(value);
            } else {
                newTokens.add(token.getValue());
            }
        }

        List<Object> valueStack = new ArrayList<>();

        while (tokens.size() != 0) {
            Token token = tokens.remove(0);
            Object value = newTokens.remove(0);
            if (token.getTokenType() == TokenTypeEnum.Literal || token.getTokenType() == TokenTypeEnum.Variable) {
                valueStack.add(value);
            } else {
                Object value2 = valueStack.remove(valueStack.size() - 1);
                Object value1 = valueStack.remove(valueStack.size() - 1);
                Object result = calculation(value1, value2, token);

                Token t = new Token();
                t.setTokenType(TokenTypeEnum.Literal);
                t.setValue(result.toString());

                valueStack.add(result);
            }
        }
        return (Boolean) JSONObject.parse(valueStack.get(0).toString());
    }

    private static Object calculation(Object value1, Object value2, Token token) {

        Class handler = null;
        String function = null;

        switch (token.getTokenType()) {
            case Keyword:
                KeywordEnum keyword = KeywordEnum.getByValue(token.getValue());
                if (null == keyword) throw new IllegalArgumentException();
                handler = keyword.handler;
                function = keyword.function;
                break;
            case Operator:
                OperatorEnum operator = OperatorEnum.getByValue(token.getValue());
                if (null == operator) throw new IllegalArgumentException();
                handler = operator.handler;
                function = operator.function;
                break;
            default:
                throw new IllegalArgumentException();
        }

        try {
            Method method = handler.getMethod(function, Object.class, Object.class);
            return method.invoke(null, value1, value2);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }

    }

}
