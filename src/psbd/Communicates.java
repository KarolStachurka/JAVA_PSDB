package psbd;

import com.mysql.jdbc.StringUtils;

public class Communicates {
    public static final String passwordNotMatch = "Password is not confirmed!";
    public static final String emailNotMatch = "Email is not confirmed!";
    public static final String alreadyExists = "Account with this data already exists";
    public static final String notExists = "This account not exists in database";
    public static final String unfilledNecessaryFields = "Please fill all necessary fields";
    public static final String databaseError = "Error with database connection";
    public static final String accountCreated = "New account was properly created";
    public static final String accountEdited = "Account data has been successfully edited";
    public static final String accountRemoved = "Account has been removed";

    private static Communicates ourInstance = new Communicates();

    public static Communicates getInstance() {
        return ourInstance;
    }

    private Communicates() {
    }
}
