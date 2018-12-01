package psbd.client;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class EditClientDataView extends ViewTemplate {
    private JPanel WindowPanel;
    private JList list1;
    private JButton addNewAdressButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox comboBox1;
    private JTextField textField6;
    private JTextField textField7;
    private JButton backButton;
    private JComboBox comboBox2;
    private JButton confirmButton;

    public EditClientDataView()
    {
        setWindowName(PanelEnum.EDITCLIENTDATA);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getAddNewAdressButton() {
        return addNewAdressButton;
    }
}
