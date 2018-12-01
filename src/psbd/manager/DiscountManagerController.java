package psbd.manager;

public class DiscountManagerController {

    private DiscountManagerView view;

    public DiscountManagerController(DiscountManagerView view)
    {
        this.view = view;
    }

    public DiscountManagerView getView() {
        return view;
    }
}
