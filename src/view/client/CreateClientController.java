package view.client;

public class CreateClientController {
    private CreateClientView view;

    public CreateClientController(CreateClientView view)
    {
        this.view = view;
    }

    public CreateClientView getView() {
        return view;
    }
}
