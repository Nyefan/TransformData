package data;

import data.token.CellReference;
import data.token.TokenTree;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public class Cell {
  private TokenTree           tokens;
  private List<CellReference> referrers;
  private boolean             recalculateRequiredOnGet;
  private BigDecimal          lastCalculatedValue;
}
