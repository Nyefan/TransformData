package data.token;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public record StringToken(String value) implements Token<String> {
  public static final Pattern pattern = Pattern.compile(".*");
}
