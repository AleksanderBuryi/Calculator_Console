package users;

import consoleMethods.ConsoleReader;
import consoleMethods.ConsoleWriter;

public class Authorization {
    private final ConsoleReader reader = new ConsoleReader();
    private final ConsoleWriter writer = new ConsoleWriter();
    private final Validation nameUserValidation = new NameUserValidation();
    private final Validation passwordValidation = new PasswordValidation();

    private final JDBCUserHistory userHistory = new JDBCUserHistory();

    public User logIn() {
        writer.write("Enter name: ");
        String name = reader.readOperator();
        while (!nameUserValidation.validate(name)) {
            writer.write("Incorrect name. Try again.");
            name = reader.readOperator();
        }

        writer.write("Enter password: ");
        String password = reader.readOperator();
        while(!passwordValidation.validate(password)) {
            writer.write("Incorrect password. Try again.");
            password = reader.readOperator();
        }

        User user = new User(name, password);

        if (userHistory.checkUserByName(user)) {
            return user;
        } else {
            writer.write("This user doesn't exist.");
            user = null;
            return user;
        }
    }
}
