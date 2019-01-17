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
    private JComboBox dayComboBox;
    private JLabel userDataLabel;
    private JTextField timeOpenTextInput;
    private JTextField timeCloseTextInput;
    private JButton hoursChangeButton;

    public ManagerMainView()
    {
        setWindowName(PanelEnum.MANAGERMAIN);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        timeOpenTextInput.setText("");
        timeCloseTextInput.setText("");

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

    public JButton getHoursChangeButton() {
        return hoursChangeButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTextField getTimeCloseTextInput() {
        return timeCloseTextInput;
    }

    public JTextField getTimeOpenTextInput() {
        return timeOpenTextInput;
    }

    public JComboBox getDayComboBox() {
        return dayComboBox;
    }
}
