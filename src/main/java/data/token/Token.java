package data.token;

import java.util.regex.Pattern;

public interface Token<T> {
  Pattern pattern();
  T value();
}

