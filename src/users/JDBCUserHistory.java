package users;

import entity.Operation;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserHistory {
    private final Connection connection;

    public JDBCUserHistory() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUserName(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into postgres.public.users (user_name) values (?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOperation(User user, Operation operation) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into postgres.public.users " +
                "(user_name, num1, num2, operator, result, date) values (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDouble(2, operation.getNum1());
            preparedStatement.setDouble(3, operation.getNum2());
            preparedStatement.setString(4, operation.getOperator());
            preparedStatement.setDouble(5, operation.getResult());
            preparedStatement.setString(6, operation.getCalcDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Operation> getAll(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select num1, num2, operator, result, date from postgres.public.users where user_name = ? and num1 is not null")) {
            preparedStatement.setString(1, user.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Operation> history = new ArrayList<>();
            Operation op;
            while (resultSet.next()) {
                op = new Operation(
                        resultSet.getDouble("num1"),
                        resultSet.getDouble("num2"),
                        resultSet.getString("operator"),
                        resultSet.getDouble("result")
                        );
                op.setCalcDateTime(LocalDateTime.parse(resultSet.getString("date"), DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                history.add(op);
            }
            return history;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserByName(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select distinct user_name from postgres.public.users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("user_name").equals(user.getName()))
                    return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
