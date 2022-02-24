package data.token;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OperatorToken extends Token<Operator> {
  Operator value;
}
