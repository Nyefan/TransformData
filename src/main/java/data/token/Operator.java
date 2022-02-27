package data.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

@AllArgsConstructor
public enum Operator {
  EQUALS("=", (a, b) -> b),
  ADD("+", BigDecimal::add),
  SUBTRACT("-", BigDecimal::subtract),
  MULTIPLY("*", BigDecimal::multiply),
  DIVIDE("/", (a, b) -> a.divide(b, 8, RoundingMode.HALF_EVEN)); //TODO: curry the (a,b) so a scale can be set by the caller

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
      case "+" -> ADD;
      case "-" -> SUBTRACT;
      case "*" -> MULTIPLY;
      case "/" -> DIVIDE;
      default -> throw new IllegalArgumentException("Operator can only be created from one of the following symbols: " + symbols());
    };
  }
}
