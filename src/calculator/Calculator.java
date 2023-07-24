package calculator;

import exceptions.IncorrectOperatorException;
import exceptions.MoreThenTwoOperandsException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Calculator {
    private String expression;
    private Double result;
    private Stack<String> history = new Stack<>();

    public void start() {
        boolean check = true;
        while (check) {
            System.out.println("==============================================");
            System.out.println("1) Calculate expression");
            System.out.println("2) Show last expression");
            System.out.println("3) Show history");
            System.out.println("4) Exit");

            System.out.print("\nEnter your action: ");
            int action = cin().nextInt();

            switch (action) {
                case 1:
                    try {
                        calculate();
                        break;
                    } catch (MoreThenTwoOperandsException | IncorrectOperatorException e) {
                        e.printStackTrace();
                        break;
                    }
                case 2:
                    if (history.isEmpty()) {
                        System.out.println("History is empty");
                        break;
                    } else {
                        showLast();
                        break;
                    }
                case 3:
                    if (history.isEmpty()) {
                        System.out.println("History is empty");
                        break;
                    } else {
                        showHistory();
                        break;
                    }
                case 4:
                    check = false;
                    writeToFile();
                    break;
                default:
                    System.out.println("Enter correct action.");
            }
        }
    }

    private void calculate() throws MoreThenTwoOperandsException, IncorrectOperatorException {
        System.out.println("Enter expression: ");
        expression = cin().nextLine();
        List<String> operandsList = new ArrayList<>();
        for (String listElement : expression.trim().split("[-+/\\*]")) {
            operandsList.add(listElement.trim());
        }
        if (operandsList.size() != 2) {
            throw new MoreThenTwoOperandsException("There must be two operands.");
        }
        String operator = getOperator(expression);
        result = getResult(operandsList, operator);
        history.push(expression);
        System.out.println("result: " + result);
    }

    private double getResult(List<String> operandsList, String operator) {
        return switch (operator) {
            case "+" -> Double.parseDouble(operandsList.get(0)) + Double.parseDouble(operandsList.get(1));
            case "-" -> Double.parseDouble(operandsList.get(0)) - Double.parseDouble(operandsList.get(1));
            case "*" -> Double.parseDouble(operandsList.get(0)) * Double.parseDouble(operandsList.get(1));
            default -> Double.parseDouble(operandsList.get(0)) / Double.parseDouble(operandsList.get(1));
        };
    }

    private String getOperator(String expression) throws IncorrectOperatorException {
        if(expression.contains("+")) return "+";
        else if (expression.contains("-")) return "-";
        else if (expression.contains("/")) return "/";
        else if (expression.contains("*")) return "*";
        else throw new IncorrectOperatorException("Incorrect operator."); //will never throw, because expression will not be split if not -+/*
    }

    private void showLast() {
        System.out.println("Last expression: ");
        expression = history.peek();
        System.out.println(expression + " = " + result);
    }

    private void showHistory() {
        Iterator iterator = history.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private void writeToFile() {
        try {
            // Creating writer
            File historyFile = new File("src/history.txt");
            FileWriter fw = new FileWriter(historyFile);
            BufferedWriter writer = new BufferedWriter(fw);

            Iterator<String> iterator = history.iterator();
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scanner cin() {
        return new Scanner(System.in);
    }
}
