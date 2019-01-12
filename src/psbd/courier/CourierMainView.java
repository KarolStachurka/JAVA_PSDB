package psbd.courier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CourierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JList list1;
    private JButton getNewListButton;
    private JLabel userDataLabel;

    public CourierMainView()
    {
        setWindowName(PanelEnum.COURIERMAIN);
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

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }
}
