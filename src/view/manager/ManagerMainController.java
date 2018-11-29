package view.manager;

public class ManagerMainController {

    private ManagerMainView view;

    public ManagerMainController(ManagerMainView view)
    {
        this.view = view;
    }

    public ManagerMainView getView() {
        return view;
    }
}
