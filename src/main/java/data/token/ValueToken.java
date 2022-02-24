package data.token;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ValueToken extends Token<Number> {
  Number value;
}
