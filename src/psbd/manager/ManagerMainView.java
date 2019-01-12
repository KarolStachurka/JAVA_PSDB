package psbd.manager;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class ManagerMainView extends ViewTemplate {
    private JButton logoutButton;
    private JPanel WindowPanel;
    private JButton recipesControlButton;
    private JButton discountsControlButton;
    private JButton modifyAccountBalanceButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton acceptForChoosenDayButton;
    private JButton acceptForChoosenMonthButton;
    private JButton acceptForChoosenWeekButton;
    private JLabel userDataLabel;

    public ManagerMainView()
    {
        setWindowName(PanelEnum.MANAGERMAIN);
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

    public JButton getDiscountsControlButton() {
        return discountsControlButton;
    }

    public JButton getRecipesControlButton() {
        return recipesControlButton;
    }

    public JButton getModifyAccountBalanceButton() {
        return modifyAccountBalanceButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }
}
