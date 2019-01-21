package psbd.utils;

public class Messages {
    public static final String PASSWORD_NOT_MATCH = "New password is not confirmed!";
    public static final String INVALID_PASSWORD = "Invalid password";
    public static final String EMAIL_NOT_MATCH = "Email is not confirmed!";
    public static final String RESTORE_PASSWORD = "Email with instructions was send to your email address";
    public static final String ALREADY_EXISTS = "Account with this data already exists";
    public static final String NOT_EXISTS = "This account does not exist in database";
    public static final String UNFILLED_NECESSARY_FIELDS = "Please fill all necessary fields";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String DATABASE_ERROR = "Error with database connection";
    public static final String ACCOUNT_CREATED = "New account was properly created";
    public static final String ACCOUNT_EDITED = "Account data has been successfully edited";
    public static final String ACCOUNT_REMOVED = "Account has been successfully removed";
    public static final String RECORD_CREATED = "New element was properly created";
    public static final String RECORD_EDITED = "Element data has been successfully edited";
    public static final String RECORD_REMOVED = "Element has been successfully removed";
    public static final String ORDER_COMPLETED = "Your order has been created";


    // table headers
    public static final String[] USER_TABLE_HEADERS = {"Type","Login", "Name", "Surname", "Email","Phone Number","Company","PESEL"};
    public static final String[] COMPANY_TABLE_HEADERS = {"NIP","Company name", "Discount value"};
    public static final String[] INGREDIENT_TABLE_HEADERS = {"Name", "Type", "Price"};
    public static final String[] DELIVERIES_MANAGER_TABLE_HEADERS = {"ID","Type","Warehouse","Quantity","Expiration date","Date of order"};
    public static final String[] DELIVERIES_LIST_TABLE_HEADERS = {"ID","Type","Warehouse","Quantity","Received","Date of order","Date of receiving"};
    public static final String[] MANAGER_WAREHOUSE_EDIT_TABLE_HEADERS = {"Name", "Type", "Quantity", "Expiration date", "ID"};
    public static final String[] RECIPES_TABLE_HEADERS = {"Name", "Price", "Availability"};
    public static final String[] MENU_ORDER_TABLE_HEADERS = {"Name", "Price"};
    public static final String[] RECIPE_INGREDIENTS_TABLE_HEADERS = {"Name", "Type", "Price", "Quantity", "Optional"};
    public static final String[] MENU_INGREDIENTS_TABLE_HEADERS = {"Name", "Type","Quantity", "Included"};
    public static final String[] ORDER_DETAILS_TABLE_HEADERS = {"Name", "Ready"};
    public static final String[] CLIENT_ADDRESS_TABLE_HEADERS = {"Address details"};
    public static final String[] CLIENT_ORDER_TABLE_HEADERS = {"Address","Delivery time","Price","Status"};
    public static final String[] CLIENT_REVIEW_TABLE_HEADERS = {"Dish name", "Rating"};
    public static final String[] COOK_ORDER_TABLE_HEADERS = {"Order ID", "Realization time", "Status"};
    public static final String[] COURIER_ORDER_TABLE_HEADERS = {"Order ID", "Address","Delivery time", "Status"};

    private static Messages ourInstance = new Messages();

    public static Messages getInstance() {
        return ourInstance;
    }

    private Messages() {
    }
}
