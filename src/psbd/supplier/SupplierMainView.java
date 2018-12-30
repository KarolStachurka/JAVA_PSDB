package psbd.supplier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

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

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getCreateNewDeliveryButton() {
        return createNewDeliveryButton;
    }

    public JButton getEditStorageButton() {
        return editStorageButton;
    }

    public JButton getStatisticsButton() {
        return statisticsButton;
    }

    public JButton getReceiveDeliveryButton() {
        return receiveDeliveryButton;
    }
}
