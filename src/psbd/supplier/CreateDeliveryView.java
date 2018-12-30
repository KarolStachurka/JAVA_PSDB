package psbd.supplier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CreateDeliveryView extends ViewTemplate {
    private JButton backButton;
    private JPanel WindowPanel;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton addButton;
    private JButton removeButton;
    private JTextField textField5;
    private JButton confirmDeliveryButton;

    public CreateDeliveryView()
    {
        setWindowName(PanelEnum.CREATEDELIVERY);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }

}
