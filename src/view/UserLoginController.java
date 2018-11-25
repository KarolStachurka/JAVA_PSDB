package view;

public class UserLoginController {
    private UserLoginView view;

    UserLoginController(UserLoginView view)
    {
        this.view = view;
    }

    public UserLoginView getView() {
        return view;
    }
}
