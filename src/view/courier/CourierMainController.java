package view.courier;

public class CourierMainController {
    private CourierMainView view;

    public CourierMainController(CourierMainView view)
    {
        this.view = view;
    }

    public CourierMainView getView() {
        return view;
    }
}
