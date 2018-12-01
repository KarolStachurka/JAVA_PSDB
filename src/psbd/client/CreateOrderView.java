package psbd.client;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class CreateOrderView extends ViewTemplate {
    private JButton completeOrderButton;
    private JList list1;
    private JList list2;
    private JList list3;
    private JComboBox comboBox1;
    private JButton backButton;
    private JPanel WindowPanel;
    private JComboBox comboBox2;
    private JButton addToOrderButton;
    private JButton removeFromOrderButton;

    public CreateOrderView()
    {
        setWindowName(PanelEnum.CREATEORDER);
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

    public JButton getAddToOrderButton() {
        return addToOrderButton;
    }

    public JButton getCompleteOrderButton() {
        return completeOrderButton;
    }

    public JButton getRemoveFromOrderButton() {
        return removeFromOrderButton;
    }

}
