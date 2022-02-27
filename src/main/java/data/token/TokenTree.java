package data.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static data.token.Operator.ADD;
import static data.token.Operator.DIVIDE;
import static data.token.Operator.EQUALS;
import static data.token.Operator.MULTIPLY;
import static data.token.Operator.SUBTRACT;

public class TokenTree {

  @Getter
  private TokenNode root;
  private TokenNode lastAddedNode;

  public TokenTree() {
  }

  @SuppressWarnings("rawtypes")
  public TokenTree(Token rootToken) {
    root = new TokenNode(rootToken);
  }

  @SuppressWarnings("rawtypes")
  public void add(Token token) {
    switch (token) {
      case CellReferenceToken cellReferenceToken -> addValueTypeToken(cellReferenceToken);
      case NumberToken numberToken -> addValueTypeToken(numberToken);
      case OperatorToken operatorToken -> addOperatorToken(operatorToken);
      case StringToken stringToken -> addValueTypeToken(stringToken);
      default -> throw new UnsupportedOperationException();
    }
  }

  private void addOperatorToken(OperatorToken token) {
    var operator = token.value();
    if (operator.equals(EQUALS)) {
      if (!(root == null)) {
        throw new IllegalStateException();
      } else {
        root = new TokenNode(token);
        root.left = new TokenNode(new NumberToken(BigDecimal.ZERO));
        lastAddedNode = root;
        return;
      }
    }

    if (lastAddedNode.data instanceof OperatorToken) {
      throw new IllegalStateException();
    }

    if (operator.equals(ADD) || operator.equals(SUBTRACT)) {
      var currentNode = new TokenNode(token, root, root.right, null);
      root.right.setParent(currentNode);
      root.setRight(currentNode);
      lastAddedNode = currentNode;
      return;
    }

    if (operator.equals(MULTIPLY) || operator.equals(DIVIDE)) {
      var parent      = findLastOperatorNodeOfType(List.of(ADD, SUBTRACT, EQUALS));
      var currentNode = new TokenNode(token, parent, parent.right, null);
      parent.right.setParent(currentNode);
      parent.setRight(currentNode);
      lastAddedNode = currentNode;
      return;
    }

    throw new UnsupportedOperationException();
  }

  private <V, T extends Token<V>> void addValueTypeToken(T token) {
    if (root == null) {
      root = new TokenNode(token);
      return;
    }

    if (lastAddedNode == root &&
        lastAddedNode.data instanceof OperatorToken &&
        ((OperatorToken) lastAddedNode.data).value().equals(EQUALS)) {
      lastAddedNode.right = new TokenNode(token);
      lastAddedNode.right.setParent(lastAddedNode);
      lastAddedNode = lastAddedNode.right;
      return;
    }

    if (lastAddedNode.data instanceof OperatorToken &&
        lastAddedNode.right == null &&
        lastAddedNode.left != null) {
      lastAddedNode.right = new TokenNode(token);
      lastAddedNode.right.setParent(lastAddedNode);
      lastAddedNode = lastAddedNode.right;
      return;
    }

    throw new IllegalStateException();
  }

  private TokenNode findLastOperatorNodeOfType(List<Operator> types) {
    var returnNode = lastAddedNode;
    if (returnNode.data instanceof OperatorToken) {
      if (types.contains(returnNode.data.value())) {
        return returnNode;
      }
    }
    while (returnNode.parent != null) {
      returnNode = returnNode.parent;
      if (returnNode.data instanceof OperatorToken) {
        if (types.contains(returnNode.data.value())) {
          return returnNode;
        }
      }
    }
    throw new IllegalStateException();
  }

  @SuppressWarnings("rawtypes")
  @AllArgsConstructor
  @RequiredArgsConstructor
  @Data
  public static class TokenNode {
    private final Token     data;
    private       TokenNode parent;
    private       TokenNode left;
    private       TokenNode right;
  }
}
