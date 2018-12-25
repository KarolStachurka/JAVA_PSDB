package psbd.courier;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class CourierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JList list1;
    private JButton getNewListButton;

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
}