package users;

import consoleMethods.ConsoleReader;
import consoleMethods.ConsoleWriter;
import interfaces.Reader;
import interfaces.Writer;

public class Registration {
    private final Reader reader = new ConsoleReader();
    private final Writer writer = new ConsoleWriter();
    private final Validation nameUserValidation = new NameUserValidation();
    private final Validation passwordValidation = new PasswordValidation();
    private final JDBCUserHistory userHistory = new JDBCUserHistory();

    public User signUp() {
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
            writer.write("This user already exists");
            user = null;
        }
        return user;
    }

}
