package psbd.cook;

public class CookMainController {
    private CookMainView view;

    public CookMainController(CookMainView view)
    {
        this.view = view;
    }

    public CookMainView getView() {
        return view;
    }
}
