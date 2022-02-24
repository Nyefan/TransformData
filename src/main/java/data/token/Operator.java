package data.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

@AllArgsConstructor
public enum Operator {
  EQUALS("=", (a, b) -> b),
  PLUS("+", BigDecimal::add),
  MINUS("-", BigDecimal::subtract),
  TIMES("*", BigDecimal::multiply),
  DIVIDED_BY("/", (a, b) -> a.divide(b, RoundingMode.HALF_EVEN));

  @Getter
  private final String                                         symbol;
  @Getter
  private final BiFunction<BigDecimal, BigDecimal, BigDecimal> operation;

  // TODO: write a compile-time annotation to create this method as
  //       Arrays.stream(Operators.values()).map(Operator::symbol).join(Collectors.toString());
  public static String symbols() {
    return "=+-*/";
  }

  public static Operator fromString(String symbol) {
    return switch (symbol) {
      case "=" -> EQUALS;
      case "+" -> PLUS;
      case "-" -> MINUS;
      case "*" -> TIMES;
      case "/" -> DIVIDED_BY;
      default -> throw new IllegalArgumentException("Operator can only be created from one of the following symbols: " + symbols());
    };
  }
}
