package psbd.manager;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DiscountManagerView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JComboBox discountMilestonesValues;
    private JTextField discountTextInput;
    private JButton acceptDiscountThresholdButton;
    private JTextField companyNameTextInput;
    private JTextField companyDiscountTextInput;
    private JButton addCompanyButton;
    private JButton removeCompanyButton;
    private JButton editCompanyButton;
    private JTable companiesTable;
    private JTextField nipTextInput;
    private JLabel messageslabel;

    public DiscountManagerView()
    {
        setWindowName(PanelEnum.DISCOUNTMANAGER);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel model = (DefaultTableModel) companiesTable.getModel();
        model.setRowCount(0);
        nipTextInput.setText("");
        companyNameTextInput.setText("");
        discountTextInput.setText("");
        companyDiscountTextInput.setText("");
        messageslabel.setText("");
    }

    public JButton getRemoveCompanyButton() {
        return removeCompanyButton;
    }

    public JButton getAddCompanyButton() {
        return addCompanyButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAcceptDiscountThresholdButton() {
        return acceptDiscountThresholdButton;
    }

    public JButton getEditCompanyButton() {
        return editCompanyButton;
    }

    public JLabel getMessageslabel() {
        return messageslabel;
    }
    public JComboBox getDiscountMilestonesValues() {
        return discountMilestonesValues;
    }

    public JTable getCompaniesTable() {
        return companiesTable;
    }

    public JTextField getCompanyDiscountTextInput() {
        return companyDiscountTextInput;
    }

    public JTextField getCompanyNameTextInput() {
        return companyNameTextInput;
    }

    public JTextField getDiscountTextInput() {
        return discountTextInput;
    }

    public JTextField getNipTextInput() {
        return nipTextInput;
    }

}
