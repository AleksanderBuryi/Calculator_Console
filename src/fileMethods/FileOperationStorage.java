package fileMethods;

import consoleMethods.ConsoleReader;
import consoleMethods.ConsoleWriter;
import entity.Operation;
import interfaces.OperationStorage;
import interfaces.Reader;
import interfaces.Writer;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class FileOperationStorage implements OperationStorage {

    private final InFileWriter writer = new InFileWriter();

    @Override
    public void save(Operation operation) {
        writer.write(operation.getNum1() + " " + operation.getOperator() + " " + operation.getNum2() + " = " + operation.getResult()
                + " | " + operation.getCalcDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }

    @Override
    public List<Operation> findAll() {
        return new ArrayList<>(new FromFileReader().readAll());
    }
}
