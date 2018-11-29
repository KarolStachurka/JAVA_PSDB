package view.user;

import view.PanelEnum;
import view.ViewTemplate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestorePasswordView extends ViewTemplate {
    private JButton sendMailButton;
    private JPanel WindowPanel;
    private JTextField textField1;
    private JButton backButton;

    public RestorePasswordView()
    {
        setWindowName(PanelEnum.RESTOREPASSWORD);
        sendMailButton.addActionListener(new ActionListener() {
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

    public JButton getSendMailButton() {
        return sendMailButton;
    }

    @Override
    public void cleanAll() {

    }
}
