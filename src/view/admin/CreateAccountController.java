package view.admin;

import view.admin.CreateAccountView;

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
