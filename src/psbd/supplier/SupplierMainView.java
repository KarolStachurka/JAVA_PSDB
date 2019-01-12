package psbd.supplier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class SupplierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JButton receiveDeliveryButton;
    private JButton createNewDeliveryButton;
    private JButton StorageStateButton;
    private JButton statisticsButton;
    private JList list2;
    private JLabel userDataLabel;
    private JButton ingredientListButton;

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

    public JButton getStorageStateButton() {
        return StorageStateButton;
    }

    public JButton getStatisticsButton() {
        return statisticsButton;
    }

    public JButton getIngredientListButton() {
        return ingredientListButton;
    }

    public JButton getReceiveDeliveryButton() {
        return receiveDeliveryButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }
}
