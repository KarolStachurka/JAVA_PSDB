package psbd.manager;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class ManagerMainView extends ViewTemplate {
    private JButton logoutButton;
    private JPanel WindowPanel;
    private JButton recipesControlButton;
    private JButton discountsControlButton;
    private JButton modifyAccountBalanceButton;
    private JComboBox dayComboBox;
    private JLabel userDataLabel;
    private JSpinner timeOpenTextInput;
    private JSpinner timeCloseTextInput;
    private JButton hoursChangeButton;

    public ManagerMainView()
    {
        setWindowName(PanelEnum.MANAGERMAIN);
        Date date = new Date();
        SpinnerDateModel sm =
                new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        timeOpenTextInput.setModel(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(timeOpenTextInput, "HH:mm");
        timeOpenTextInput.setEditor(de);

        Date date1 = new Date();
        SpinnerDateModel sm1 =
                new SpinnerDateModel(date1, null, null, Calendar.HOUR_OF_DAY);
        timeCloseTextInput.setModel(sm1);
        JSpinner.DateEditor dec = new JSpinner.DateEditor(timeCloseTextInput, "HH:mm");
        timeCloseTextInput.setEditor(dec);
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

    public JButton getHoursChangeButton() {
        return hoursChangeButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JSpinner getTimeCloseTextInput() {
        return timeCloseTextInput;
    }

    public JSpinner getTimeOpenTextInput() {
        return timeOpenTextInput;
    }

    public JComboBox getDayComboBox() { return dayComboBox; }
}
