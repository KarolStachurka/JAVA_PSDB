package psbd.admin;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JButton addNewUserButton;
    private JButton editReviewsButton;
    private JButton editUserButton;
    private JButton editDiscountsButton;
    private JTable usersTable;
    private JLabel AdminUserDataLabel;

    public AdminMainView()
    {
        setWindowName(PanelEnum.ADMINMAIN);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);
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

    public JButton getEditDiscountsButton() {
        return editDiscountsButton;
    }

    public JTable getUsersTable() {
        return usersTable;
    }

}
