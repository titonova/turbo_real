package com.craftinginterpreters.lox;

import java.util.List;

class LoxArray extends LoxInstance {
  private final Object[] elements;

  LoxArray(Object... elems) {
    super(null);
    elements = new Object[10];
  }

  @Override
  Object get(Token name) {
    if (name.lexeme.equals("get")) {
      return new LoxCallable() {
        @Override
        public int arity() {
          return 1;
        }

        @Override
        public Object call(Interpreter interpreter,
                           List<Object> arguments) {
          int index = (int)(double)arguments.get(0);
          return elements[index];
        }
      };
    } else if (name.lexeme.equals("set")) {
      return new LoxCallable() {
        @Override
        public int arity() {
          return 2;
        }

        @Override
        public Object call(Interpreter interpreter,
                           List<Object> arguments) {
          int index = (int)(double)arguments.get(0);
          Object value = arguments.get(1);
          return elements[index] = value;
        }
      };
    } else if (name.lexeme.equals("length")) {
      return elements.length;
    }

    throw new RuntimeError(name, // [hidden]
        "Undefined property '" + name.lexeme + "'.");
  }

  @Override
  void set(Token name, Object value) {
    throw new RuntimeError(name, "Cannot add properties to arrays.");
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[");
    for (int i = 0; i < elements.length; i++) {
      if (i != 0) buffer.append(", ");
      buffer.append(elements[i]);
    }
    buffer.append("]");
    return buffer.toString();
  }
}