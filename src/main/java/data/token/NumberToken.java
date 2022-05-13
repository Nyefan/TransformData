package data.token;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public record NumberToken(BigDecimal value) implements Token<BigDecimal> {
  public static final Pattern pattern = Pattern.compile("^\\d*\\.?\\d+$");

  public Pattern pattern() {return pattern;}
}
