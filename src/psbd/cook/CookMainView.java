package psbd.cook;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CookMainView extends ViewTemplate {
    private JButton logoutButton;
    private JPanel WindowPanel;
    private JList list1;
    private JList list2;
    private JList list3;
    private JButton setOrderReadyButton;
    private JButton updateListButton;

    public CookMainView()
    {
        setWindowName(PanelEnum.COOKMAIN);
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
