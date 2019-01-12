package psbd.manager;

import psbd.models.CurrentSession;

public class ManagerMainController {

    private ManagerMainView view;

    public ManagerMainController(ManagerMainView view)
    {
        this.view = view;
    }

    public ManagerMainView getView() {
        return view;
    }
    public void login(){
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }
}
