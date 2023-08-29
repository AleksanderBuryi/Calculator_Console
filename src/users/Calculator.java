package users;

import consoleMethods.ConsoleReader;
import consoleMethods.ConsoleWriter;
import entity.Operation;
import interfaces.Reader;
import interfaces.Writer;
import services.OperationService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class Calculator {

    private final Reader reader = new ConsoleReader();
    private final Writer writer = new ConsoleWriter();
    private final JDBCUserHistory jdbcUserHistory = new JDBCUserHistory();

    private User user;

    public Calculator(User user) {
        this.user = user;
    }

    private void calculate(Operation operation) {
        switch (operation.getOperator()) {
            case "+" -> {
                operation.setResult(operation.getNum1() + operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                jdbcUserHistory.setOperation(user, operation);
            }
            case "-" -> {
                operation.setResult(operation.getNum1() - operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                jdbcUserHistory.setOperation(user, operation);
            }
            case "*" -> {
                operation.setResult(operation.getNum1() * operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                jdbcUserHistory.setOperation(user, operation);
            }
            case "/" -> {
                operation.setResult(operation.getNum1() / operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                jdbcUserHistory.setOperation(user, operation);
            }
        }
    }

    public void getHistory() {
        List<Operation> history = jdbcUserHistory.getAll(user);
        for (Operation operation : history) {
            writer.write(operation.getNum1() + " " + operation.getOperator() + " " + operation.getNum2() + " = " + operation.getResult()
                    + " | " + operation.getCalcDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        }
    }

    public void createOperation() {
        writer.write("Enter num1:");
        double num1 = reader.readNumber();
        writer.write("Enter num2:");
        double num2 = reader.readNumber();
        writer.write("Choose operator: +, -, *, /");
        String operator = reader.readOperator();
        Operation operation = new Operation(num1, num2, operator);
        calculate(operation);
        writer.write("Result = " + operation.getResult());
    }
}
