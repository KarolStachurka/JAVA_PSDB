package psbd.user;

import psbd.PanelEnum;
import psbd.UserEnum;

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

    public UserEnum userType(){
        return UserEnum.ADMIN;
    }

}
