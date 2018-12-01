package psbd.admin;

public class CreateAccountController {
    private CreateAccountView view;

    public CreateAccountController(CreateAccountView view)
    {
        this.view = view;
    }

    public CreateAccountView getView() {
        return view;
    }
}
