package psbd.manager;

public class RecipeManagerController {

    private RecipeManagerView view;

    public RecipeManagerController(RecipeManagerView view)
    {
        this.view = view;
    }

    public RecipeManagerView getView() {
        return view;
    }
}
