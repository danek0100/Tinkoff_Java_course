package edu.hw2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    @Test
    @DisplayName("Basic Expression Evaluation and Stringification")
    void testBasicExpressions() {
        Task1.Expr two = new Task1.Expr.Constant(2);
        Task1.Expr four = new Task1.Expr.Constant(4);
        Task1.Expr negOne = new Task1.Expr.Negate(new Task1.Expr.Constant(1));
        Task1.Expr sumTwoFour = new Task1.Expr.Addition(two, four);
        Task1.Expr mult = new Task1.Expr.Multiplication(sumTwoFour, negOne);
        Task1.Expr exp = new Task1.Expr.Exponent(mult, two);
        Task1.Expr res = new Task1.Expr.Addition(exp, new Task1.Expr.Constant(1));

        assertThat(res.evaluate()).isEqualTo(37);
    }

    @Test
    @DisplayName("Negation Expression Evaluation and Stringification")
    void testNegationExpressions() {
        Task1.Expr five = new Task1.Expr.Constant(5);
        Task1.Expr negFive = new Task1.Expr.Negate(five);

        assertThat(negFive.evaluate()).isEqualTo(-5);
        assertThat(negFive.toString()).isEqualTo("-5.0");
    }

    @Test
    @DisplayName("Exponent Expression Evaluation and Stringification")
    void testExponentExpressions() {
        Task1.Expr three = new Task1.Expr.Constant(3);
        Task1.Expr four = new Task1.Expr.Constant(4);
        Task1.Expr exp = new Task1.Expr.Exponent(three, four);

        assertThat(exp.evaluate()).isEqualTo(81);
        assertThat(exp.toString()).isEqualTo("3.0^4.0");
    }

    @Test
    @DisplayName("Addition Expression Evaluation and Stringification")
    void testAdditionExpressions() {
        Task1.Expr six = new Task1.Expr.Constant(6);
        Task1.Expr seven = new Task1.Expr.Constant(7);
        Task1.Expr sum = new Task1.Expr.Addition(six, seven);

        assertThat(sum.evaluate()).isEqualTo(13);
        assertThat(sum.toString()).isEqualTo("6.0 + 7.0");
    }

    @Test
    @DisplayName("Multiplication Expression Evaluation and Stringification")
    void testMultiplicationExpressions() {
        Task1.Expr eight = new Task1.Expr.Constant(8);
        Task1.Expr nine = new Task1.Expr.Constant(9);
        Task1.Expr product = new Task1.Expr.Multiplication(eight, nine);

        assertThat(product.evaluate()).isEqualTo(72);
        assertThat(product.toString()).isEqualTo("8.0 * 9.0");
    }
}
