package data;

import data.token.Token;
import lombok.Data;

import java.util.List;

@Data
public class Cell {
  private List<Token> tokens;
  private List<Cell>  referrers;
  private boolean     recalculateRequiredOnGet;
}
