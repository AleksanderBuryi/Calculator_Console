package calculator;

import calculator.Calculator;
import exceptions.IncorrectOperatorException;
import exceptions.MoreThenTwoOperandsException;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.start();
    }
}
