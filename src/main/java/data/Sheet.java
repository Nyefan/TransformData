package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Sheet {

  private Map<String, String> sheet = new HashMap<>();

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
    //TODO: create cell object with
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
}
