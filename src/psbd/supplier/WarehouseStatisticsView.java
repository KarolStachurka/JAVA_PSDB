package psbd.supplier;

import datechooser.beans.DateChooserCombo;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class WarehouseStatisticsView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JList list1;
    private JButton getIngredientSummaryButton;
    private JButton ordersSummaryButton;
    private DateChooserCombo startDayChooser;
    private DateChooserCombo endDayChooser;

    public WarehouseStatisticsView()
    {
        setWindowName(PanelEnum.WAREHOUSESTATS);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        startDayChooser.setDateFormat(format);
        endDayChooser.setDateFormat(format);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }

    public JButton getBackButton() {
        return backButton;
    }

    public DateChooserCombo getStartDayChooser(){ return startDayChooser; }

    public DateChooserCombo getEndDayChooserDayChooser(){ return endDayChooser; }
}
