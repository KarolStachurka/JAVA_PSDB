package view.user;

public class RestorePasswordController {
    private RestorePasswordView view;

    public RestorePasswordController(RestorePasswordView view)
    {
        this.view = view;
    }

    public RestorePasswordView getView() {
        return view;
    }
}
