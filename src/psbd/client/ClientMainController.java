package psbd.client;

import psbd.models.CurrentSession;

public class ClientMainController {

    private ClientMainView view;

    public ClientMainController(ClientMainView view)
    {
        this.view = view;
    }

    public ClientMainView getView() {
        return view;
    }

    public void login()
    {
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    public void logout(){
        CurrentSession session = CurrentSession.getInstance();
        session.setLoggedUser(null);
    }
}
