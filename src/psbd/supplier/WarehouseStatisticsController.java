package psbd.supplier;

public class WarehouseStatisticsController {

    private WarehouseStatisticsView view;

    public WarehouseStatisticsController(WarehouseStatisticsView view)
    {
        this.view = view;
    }

    public WarehouseStatisticsView getView() {
        return view;
    }
}
