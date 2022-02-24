package data;

import data.token.CellReference;
import data.token.CellReferenceToken;
import data.token.Operator;
import data.token.OperatorToken;
import data.token.Token;
import data.token.ValueToken;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Sheet {

  private final Map<String, String> sheet = new HashMap<>();

  public Sheet() {

  }

  public Optional<Integer> getCell(String cellId) {
    var rawData = sheet.get(cellId);
    if (rawData == null) {
      return Optional.empty();
    }

    if (rawData.startsWith("=")) {
      return Optional.of(handleEquation(rawData));
    }

    try {
      return Optional.of(Integer.parseInt(rawData));
    } catch (NumberFormatException e) {
      //TODO: handle malformed data
    }

    throw new RuntimeException();
  }

  public Optional<String> setCell(String cellId, String value) {
    //TODO: match on regex; reject if fail
    //TODO: tokenize value
    return Optional.ofNullable(sheet.put(cellId, value));
  }

  private int handleEquation(String rawData) {
    String[] equation = rawData.substring(1).split("\\+");

    return Stream.of(equation)
                 .map(this::getCell)
                 .filter(Optional::isPresent)
                 .mapToInt(Optional::get)
                 .sum();
  }

  @SuppressWarnings("rawtypes")
  private List<Token> tokenize(String tokenString) {
    var tokens = new StringTokenizer(tokenString, Operator.symbols(), true).asIterator();
    return StreamSupport
        .stream(Spliterators.spliteratorUnknownSize(tokens, Spliterator.ORDERED), false)
        .map(str -> createTokenFromString((String) str)) //StringTokenizer's asIterator returns Iterator<Object> because java 1
        .collect(Collectors.toList());
  }

  @SuppressWarnings("rawtypes")
  private Token createTokenFromString(String tokenString) {
    if (OperatorToken.pattern.matcher(tokenString).matches()) {
      return new OperatorToken(Operator.fromString(tokenString));
    }

    if (CellReferenceToken.pattern.matcher(tokenString).matches()) {
      return new CellReferenceToken(new CellReference(tokenString));
    }

    if (ValueToken.pattern.matcher(tokenString).matches()) {
      return new ValueToken(new BigDecimal(tokenString));
    }

    throw new IllegalArgumentException("Unable to create token from string: " + tokenString);
  }
}
