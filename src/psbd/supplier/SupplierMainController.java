package psbd.supplier;

import psbd.models.CurrentSession;

public class SupplierMainController {

    private SupplierMainView view;

    public SupplierMainController(SupplierMainView view)
    {
        this.view = view;
    }

    public SupplierMainView getView() {
        return view;
    }

    public void login()
    {
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " +  session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    public void logout(){
        CurrentSession session = CurrentSession.getInstance();
        session.setLoggedUser(null);
    }
}
