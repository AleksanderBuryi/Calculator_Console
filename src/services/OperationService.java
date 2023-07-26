package services;

import consoleMethods.InMemoryOperationStorage;
import entity.Operation;
import fileMethods.FileOperationStorage;
import interfaces.OperationStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class OperationService {

    private final OperationStorage storage = new FileOperationStorage();
//    private final OperationStorage storage = new InMemoryOperationStorage();

    public Operation calculate(Operation operation) {
        switch (operation.getOperator()) {
            case "+":
                operation.setResult(operation.getNum1() + operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                storage.save(operation);
                return operation;
             case "-":
                operation.setResult(operation.getNum1() - operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                storage.save(operation);
                return operation;
             case "*":
                operation.setResult(operation.getNum1() * operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                storage.save(operation);
                return operation;
             case "/":
                operation.setResult(operation.getNum1() / operation.getNum2());
                operation.setCalcDateTime(LocalDateTime.now());
                storage.save(operation);
                return operation;
        }
        throw new RuntimeException();
    }

    public List<String> getHistory() {
        List<Operation> all = storage.findAll();
        List<String> result = new ArrayList<>();
        for (Operation op : all) {
            result.add(op.getNum1() + " " + op.getOperator() + " " + op.getNum2() + " = " + op.getResult()
                    + " | " + op.getCalcDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        }
        return result;
    }

}
