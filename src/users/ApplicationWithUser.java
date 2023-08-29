package users;

import consoleMethods.ConsoleReader;
import consoleMethods.ConsoleWriter;
import interfaces.Reader;
import interfaces.Writer;

public class ApplicationWithUser {
    private final Reader reader = new ConsoleReader();
    private final Writer writer = new ConsoleWriter();
    private Registration registration = new Registration();
    private Authorization authorization = new Authorization();
    private Calculator calculator;
    private JDBCUserHistory jdbcUserHistory = new JDBCUserHistory();

    private Session session;
    private boolean mainFlag = true;

    public void start() {
        while (mainFlag) {
            if(session == null) {
                showGuestMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private void showGuestMenu() {
        boolean flag = true;
        int act;

        while (flag) {
            writer.write("""
                    1. Log in
                    2. Sign up
                    3. Exit
                    Enter your action: 
                    """);
            act = (int) reader.readNumber();
            switch (act) {
                case 1:
                    User user = authorization.logIn();
                    if (user == null) {
                        writer.write("You failed. Try Again.");
                    } else {
                        session = new Session(user);
                        calculator = new Calculator(user);
                        flag = false;
                    }
                    break;
                case 2:
                    User userReg = registration.signUp();
                    if (userReg == null) {
                        writer.write("Try Again.");
                    } else {
                        jdbcUserHistory.insertUserName(userReg);
                    }
                    break;
                case 3:
                    flag = false;
                    mainFlag = false;
                    break;
                default:
                    writer.write("Enter correct action!");
                    break;
            }
        }
    }

    private void showUserMenu() {
        boolean flag = true;
        int act;

        while(flag) {
            writer.write("""
                    1. Calculate expression
                    2. Show history
                    3. Log out
                    Enter your action:
                    """);
            act = (int) reader.readNumber();
            switch (act) {
                case 1:
                    calculator.createOperation();
                    break;
                case 2:
                    calculator.getHistory();
                    break;
                case 3:
                    session = null;
                    flag = false;
                    break;
                default:
                    writer.write("Enter correct action!");
                    break;
            }
        }

    }
}
