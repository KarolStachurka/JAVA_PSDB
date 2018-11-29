package view.user;

public class UserLoginController {
    private UserLoginView view;

    public UserLoginController(UserLoginView view)
    {
        this.view = view;
    }

    public UserLoginView getView() {
        return view;
    }

    public boolean userLogin()
    {
        return true;
    }

}
