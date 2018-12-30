package psbd.user;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class RestorePasswordView extends ViewTemplate {
    private JButton sendMailButton;
    private JPanel WindowPanel;
    private JTextField loginTextInput;
    private JButton backButton;
    private JLabel messagesLabel;

    public RestorePasswordView()
    {
        setWindowName(PanelEnum.RESTOREPASSWORD);

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

    public JTextField getLoginTextInput() {
        return loginTextInput;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    @Override
    public void cleanAll() {
            loginTextInput.setText("");
            messagesLabel.setText("");
    }
}
