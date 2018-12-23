package psbd.admin;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class AdminMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JButton addNewUserButton;
    private JButton editReviewsButton;
    private JButton editUserButton;
    private JList list1;

    public AdminMainView()
    {
        setWindowName(PanelEnum.ADMINMAIN);
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

    public JButton getAddNewUserButton() {
        return addNewUserButton;
    }

    public JButton getEditReviewsButton() {
        return editReviewsButton;
    }

    public JButton getEditUserButton() {
        return editUserButton;
    }
}
