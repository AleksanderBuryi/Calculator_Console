package users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation implements Validation{
    @Override
    public boolean validate(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
