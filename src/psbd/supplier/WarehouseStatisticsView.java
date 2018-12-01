package psbd.supplier;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class WarehouseStatisticsView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JList list1;
    private JButton getIngredientSummaryButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton ordersSummaryButton;

    public WarehouseStatisticsView()
    {
        setWindowName(PanelEnum.WAREHOUSESTATS);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }
}
