package psbd.admin;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class CreateAccountView extends ViewTemplate {
    private JPanel WindowPanel;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField2;
    private JButton confirmButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JComboBox comboBox1;
    private JButton backButton;
    private JTextField textField8;
    private JComboBox comboBox2;
    private JButton removeThisAccountButton;

    public CreateAccountView()
    {
        setWindowName(PanelEnum.CREATEACCOUNT);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }
}
