package view.supplier;

import view.PanelEnum;
import view.ViewTemplate;

import javax.swing.*;

public class SupplierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JList list1;
    private JButton receiveDeliveryButton;
    private JButton createNewDeliveryButton;
    private JButton editStorageButton;
    private JButton statisticsButton;
    private JList list2;

    public SupplierMainView()
    {
        setWindowName(PanelEnum.SUPPLIERMAIN);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }
}
