package data;

import data.token.CellReference;
import data.token.CellReferenceToken;
import data.token.NumberToken;
import data.token.Operator;
import data.token.OperatorToken;
import data.token.StringToken;
import data.token.Token;
import data.token.TokenTree;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringTokenizer;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

public class Sheet {

  private final Map<String, Cell> sheet = new HashMap<>();

  public Optional<BigDecimal> getCell(String cellId) {
    var cell = sheet.get(cellId);

    if (cell == null || cell.getTokens() == null) {
      return Optional.empty();
    }

    if (!cell.isRecalculateRequiredOnGet() && cell.getLastCalculatedValue() != null) {
      return Optional.of(cell.getLastCalculatedValue());
    }

    var value = calculate(cell.getTokens().getRoot());

    value.ifPresent(cell::setLastCalculatedValue);
    //TODO: also set recalculateRequiredOnGet to false once reference counting on set is complete

    return value;
  }

  // TODO: create TokenTreeIterator
  private Optional<BigDecimal> calculate(TokenTree.TokenNode node) {
    switch (node.getData()) {
      case CellReferenceToken c -> {return getCell(c.value().cellId());}
      case NumberToken n -> {return Optional.ofNullable(n.value());}
      case OperatorToken o -> {return calculateOperatorToken(node, o.value());}
      case StringToken s -> throw new UnsupportedOperationException();
      default -> throw new IllegalStateException();
    }
  }

  // TODO: clarify this with an optional stream
  private Optional<BigDecimal> calculateOperatorToken(TokenTree.TokenNode node, Operator operator) {
    return Optional.of(operator.getOperation().apply(
        calculate(node.getLeft()).orElseThrow(IllegalStateException::new),
        calculate(node.getRight()).orElseThrow(IllegalStateException::new)));
  }

  public Optional<Cell> setCell(String cellId, String value) {
    var tokens = value.startsWith("=") ? tokenizeEquation(value) : new TokenTree(createTokenFromString(value));
    var cell = Cell.builder()
                   .withTokens(tokens)
                   .withRecalculateRequiredOnGet(true)
                   .build();
    return Optional.ofNullable(sheet.put(cellId, cell));
  }

  private TokenTree tokenizeEquation(String tokenString) {
    var tokens = new StringTokenizer(tokenString, Operator.symbols(), true).asIterator();
    return StreamSupport
        .stream(Spliterators.spliteratorUnknownSize(tokens, Spliterator.ORDERED), false)
        .map(str -> createTokenFromString((String) str)) //StringTokenizer's asIterator returns Iterator<Object> because java 1
        .collect(Collector.of(
            TokenTree::new,
            TokenTree::add,
            (a, b) -> {throw new IllegalStateException();})); //Combiner should never be called on a sequential stream
  }

  @SuppressWarnings("rawtypes")
  private Token createTokenFromString(String tokenString) {
    if (OperatorToken.pattern.matcher(tokenString).matches()) {
      return new OperatorToken(Operator.fromString(tokenString));
    }

    if (CellReferenceToken.pattern.matcher(tokenString).matches()) {
      return new CellReferenceToken(new CellReference(tokenString));
    }

    if (NumberToken.pattern.matcher(tokenString).matches()) {
      return new NumberToken(new BigDecimal(tokenString));
    }

    throw new IllegalArgumentException("Unable to create token from string: " + tokenString);
  }
}
