package psbd.supplier;

import datechooser.beans.DateChooserCombo;
import datechooser.model.multiple.MultyModelBehavior;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public class WarehouseStatisticsView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JButton getIngredientSummaryButton;
    private JButton ordersSummaryButton;
    private DateChooserCombo startDayChooser;
    private DateChooserCombo endDayChooser;
    private JTable statisticsTable;

    public WarehouseStatisticsView()
    {
        setWindowName(PanelEnum.WAREHOUSESTATS);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        startDayChooser.setDateFormat(format);
        endDayChooser.setDateFormat(format);
        startDayChooser.setBehavior(MultyModelBehavior.SELECT_SINGLE);
        endDayChooser.setBehavior(MultyModelBehavior.SELECT_SINGLE);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
        model.setRowCount(0);


    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getGetIngredientSummaryButton() {
        return getIngredientSummaryButton;
    }

    public JButton getOrdersSummaryButton() {
        return ordersSummaryButton;
    }

    public DateChooserCombo getStartDayChooser(){ return startDayChooser; }

    public DateChooserCombo getEndDayChooserDayChooser(){ return endDayChooser; }

    public JTable getStatisticsTable() {
        return statisticsTable;
    }
}
