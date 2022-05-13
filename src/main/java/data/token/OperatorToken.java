package data.token;

import java.util.regex.Pattern;

public record OperatorToken(Operator value) implements Token<Operator> {
  public static final Pattern pattern = Pattern.compile("^[=+\\-*/]$");

  public Pattern pattern() {return pattern;}
}
