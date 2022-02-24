package data.token;

import java.util.regex.Pattern;

public record CellReferenceToken(CellReference value) implements Token<CellReference> {
  public static final Pattern pattern = Pattern.compile("^[A-Z]+\\d+$");
}
