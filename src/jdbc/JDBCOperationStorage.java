package jdbc;

import entity.Operation;
import interfaces.OperationStorage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class JDBCOperationStorage implements OperationStorage {

    @Override
    public void save(Operation operation) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");

            PreparedStatement preparedStatement = connection.prepareStatement("insert into operations values (?, ?, ?, ?, ?)");
            preparedStatement.setDouble(1, operation.getNum1());
            preparedStatement.setDouble(2, operation.getNum2());
            preparedStatement.setString(3, operation.getOperator());
            preparedStatement.setDouble(4, operation.getResult());
            preparedStatement.setString(5, operation.getCalcDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Operation> findAll() {
        List<Operation> allOperations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from operations");

            while (resultSet.next()) {
                double num1 = resultSet.getDouble(1);
                double num2 = resultSet.getDouble(2);
                String operator = resultSet.getString(3);
                double result = resultSet.getDouble(4);
                String localDateTime = resultSet.getString(5);

                Operation operation = new Operation(
                        num1,
                        num2,
                        operator,
                        result
                );
                operation.setCalcDateTime(LocalDateTime.parse(localDateTime, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

                allOperations.add(operation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allOperations;
    }
}
