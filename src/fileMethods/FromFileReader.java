package fileMethods;

import entity.Operation;
import interfaces.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FromFileReader {

    private final String path = "src/history.txt";

    public List<Operation> readAll() {
        List<Operation> operations = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(path))) {
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                operations.add(getOperation(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return operations;
    }

    private Operation getOperation(String line) {
        String[] el = line.trim().split("\\|");
        String[] el1 = el[0].trim().split(" ");
        Operation operation = new Operation(Double.parseDouble(el1[0]), Double.parseDouble(el1[2]), el1[1], Double.parseDouble(el1[4]));
        operation.setCalcDateTime(LocalDateTime.parse(el[1].trim(), DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        return operation;
    }
}
