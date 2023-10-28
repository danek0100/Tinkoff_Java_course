package edu.hw2;

/**
 * This class represents a mathematical expression evaluator.
 */
public class Task1 {

    /**
     * The {@code Expr} interface defines a basic contract for mathematical expressions.
     */
    public sealed interface Expr {
        /**
         * Evaluate the mathematical expression and return the result.
         *
         * @return the result of evaluating the expression.
         */
        double evaluate();

        /**
         * Return a string representation of the expression.
         *
         * @return a string representation of the expression.
         */
        String toString();

        /**
         * The {@code Constant} record represents a constant value expression.
         */
        record Constant(double value) implements Expr {
            public double evaluate() {
                return value;
            }

            public String toString() {
                return String.valueOf(value);
            }
        }

        /**
         * The {@code Negate} record represents an expression that negates another expression.
         */
        record Negate(Expr expr) implements Expr {
            public double evaluate() {
                return -expr.evaluate();
            }

            public String toString() {
                return "-" + expr.toString();
            }
        }

        /**
         * The {@code Exponent} record represents an expression raised to the power of another expression.
         */
        record Exponent(Expr base, Expr exponent) implements Expr {
            public double evaluate() {
                return Math.pow(base.evaluate(), exponent.evaluate());
            }

            public String toString() {
                return base.toString() + "^" + exponent.toString();
            }
        }

        /**
         * The {@code Addition} record represents the addition of two expressions.
         */
        record Addition(Expr left, Expr right) implements Expr {
            public double evaluate() {
                return left.evaluate() + right.evaluate();
            }

            public String toString() {
                return left.toString() + " + " + right.toString();
            }
        }

        /**
         * The {@code Multiplication} record represents the multiplication of two expressions.
         */
        record Multiplication(Expr left, Expr right) implements Expr {
            public double evaluate() {
                return left.evaluate() * right.evaluate();
            }

            public String toString() {
                return left.toString() + " * " + right.toString();
            }
        }
    }
}
