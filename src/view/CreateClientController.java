package view;

public class CreateClientController {
    private CreateClientView view;

    CreateClientController(CreateClientView view)
    {
        this.view = view;
    }

    public CreateClientView getView() {
        return view;
    }
}
