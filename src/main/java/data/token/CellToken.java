package data.token;

import data.Cell;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class CellToken extends Token<Cell> {
  Cell value;
}
