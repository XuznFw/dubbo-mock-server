package token;

import lombok.Data;

@Data
public class Token {

    private TokenTypeEnum tokenType;
    private String value;

}
