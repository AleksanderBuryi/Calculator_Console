package entity;

import java.time.LocalDateTime;

public class Operation {
    private double num1;
    private double num2;
    private String operator;
    private double result;
    private LocalDateTime calcDateTime;

    public Operation(double num1, double num2, String operator) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
    }

    public Operation(double num1, double num2, String operator, double result) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.result = result;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public LocalDateTime getCalcDateTime() {
        return calcDateTime;
    }

    public void setCalcDateTime(LocalDateTime calcDateTime) {
        this.calcDateTime = calcDateTime;
    }

    @Override
    public String toString() {
        return "entity.Operation{" +
                "num1=" + num1 +
                ", num2=" + num2 +
                ", operator='" + operator + '\'' +
                ", result=" + result +
                ", calcDateTime=" + calcDateTime +
                '}';
    }
}
