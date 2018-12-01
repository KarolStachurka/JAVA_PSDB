package psbd.client;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class ClientMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JList list1;
    private JButton createNewOrderButton;
    private JButton createDishReviewButton;
    private JButton logoutButton;
    private JButton editUserDataButton;

    public ClientMainView()
    {
        setWindowName(PanelEnum.CLIENTMAIN);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }

    public JButton getCreateDishReviewButton() {
        return createDishReviewButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getCreateNewOrderButton() {
        return createNewOrderButton;
    }

    public JButton getEditUserDataButton() {
        return editUserDataButton;
    }
}
