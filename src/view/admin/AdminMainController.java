package view.admin;

public class AdminMainController {
    private AdminMainView view;

    public AdminMainController(AdminMainView view)
    {
        this.view = view;
    }

    public AdminMainView getView() {
        return view;
    }
}
