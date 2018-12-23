package psbd.manager;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class DiscountManagerView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton acceptButton;
    private JList list1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;

    public DiscountManagerView()
    {
        setWindowName(PanelEnum.DISCOUNTMANAGER);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }
}
