package entity;

import consoleMethods.ConsoleReader;
import consoleMethods.ConsoleWriter;
import interfaces.Reader;
import interfaces.Writer;
import services.OperationService;

import java.util.List;

public class Application {

    private final Reader reader = new ConsoleReader();
    private final Writer writer = new ConsoleWriter();
    private final OperationService operationService = new OperationService();

    public void start(){
        while(true) {
            writer.write("Enter num1:");
            double num1 = reader.readNumber();
            writer.write("Enter num2:");
            double num2 = reader.readNumber();
            writer.write("Choose operator: +, -, *, /");
            String operator = reader.readOperator();
            Operation operation = new Operation(num1, num2, operator);
            Operation result = operationService.calculate(operation);
            writer.write("Result = " + result.getResult());

            if (question()) break;
        }
    }

    private boolean question() {
        while (true) {
            writer.write("Continue? 1 - yes, 2 - no, 3 - show history");
            double answer = reader.readNumber();
            if (answer == 1) return false;
            if (answer == 2) return true;
            if (answer == 3) {
                List<String> history = operationService.getHistory();
                for (String s : history) {
                    writer.write(s);
                }
            }
        }
    }


}
