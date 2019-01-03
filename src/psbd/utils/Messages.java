package psbd.utils;

public class Messages {
    public static final String passwordNotMatch = "Password is not confirmed!";
    public static final String emailNotMatch = "Email is not confirmed!";
    public static final String restorePassword = "Email with instructions was send to your email address";
    public static final String alreadyExists = "Account with this data already exists";
    public static final String notExists = "This account not exists in database";
    public static final String unfilledNecessaryFields = "Please fill all necessary fields";
    public static final String invalidInput = "Invalid input";
    public static final String databaseError = "Error with database connection";
    public static final String accountCreated = "New account was properly created";
    public static final String accountEdited = "Account data has been successfully edited";
    public static final String accountRemoved = "Account has been removed";

    // table headers
    public static final String[] userTableHeaders = {"Type","Login", "Name", "Surname", "Email","Phone Number","Company","PESEL"};
    public static final String[] companyTableHeaders = {"NIP","Company name", "Discount value"};
    public static final String[] ingredientTableHeaders  = {"Name", "Type", "Price"};
    public static final String[] deliveriesTableHeaders = {"ID","Type","Warehouse","Quantity","Date of order","Received","Date of receiving","Expiration date"};

    private static Messages ourInstance = new Messages();

    public static Messages getInstance() {
        return ourInstance;
    }

    private Messages() {
    }
}
