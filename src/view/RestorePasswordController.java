package view;

public class RestorePasswordController {
    private RestorePasswordView view;

    RestorePasswordController(RestorePasswordView view)
    {
        this.view = view;
    }

    public RestorePasswordView getView() {
        return view;
    }
}
