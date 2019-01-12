package psbd.courier;

import psbd.models.CurrentSession;

public class CourierMainController {
    private CourierMainView view;

    public CourierMainController(CourierMainView view)
    {
        this.view = view;
    }

    public CourierMainView getView() {
        return view;
    }

    public void login(){
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }
}
