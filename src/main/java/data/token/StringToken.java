package data.token;

import java.util.regex.Pattern;

public record StringToken(String value) implements Token<String> {
  public static final Pattern pattern = Pattern.compile(".*");

  public Pattern pattern() {return pattern;}
}
