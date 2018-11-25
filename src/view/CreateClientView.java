package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateClientView extends ViewTemplate{
    private JPanel WindowPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton confirmButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JComboBox comboBox1;
    private JButton backButton;
    private JTextField textField8;

    CreateClientView()
    {
        setWindowName(PanelEnum.CREATECLIENACCOUNT);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    @Override
    public void cleanAll() {

    }
}
