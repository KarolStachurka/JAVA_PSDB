package psbd.models;

public class CurrentSession {
    private static CurrentSession ourInstance = new CurrentSession();

    private User loggedUser;

    public static CurrentSession getInstance() {
        return ourInstance;
    }

    private CurrentSession() {
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public User getLoggedUser() {

        return loggedUser;
    }

    public void logout()
    {
        setLoggedUser(null);
    }
}
