package users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameUserValidation implements Validation{
    @Override
    public boolean validate(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]{4,}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
