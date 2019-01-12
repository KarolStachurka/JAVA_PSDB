package psbd.cook;

import psbd.models.CurrentSession;

public class CookMainController {
    private CookMainView view;

    public CookMainController(CookMainView view)
    {
        this.view = view;
    }

    public CookMainView getView() {
        return view;
    }

    public void login(){
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }
}
