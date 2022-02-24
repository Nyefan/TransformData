package data;

import data.token.Token;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("rawtypes")
@Data
public class Cell {
  //TODO: Tree<Token>
  private List<Token> tokens;
  private List<Cell>  referrers;
  private boolean     recalculateRequiredOnGet;
  private BigDecimal  lastCalculatedValue;
}
