package data.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Operator {
  PLUS("+"),
  MINUS("-"),
  TIMES("*"),
  DIVIDED_BY("/");

  @Getter
  private final String symbol;
}
