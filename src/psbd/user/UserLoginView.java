package psbd.user;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class UserLoginView extends ViewTemplate {
    private JPanel WindowPanel;
    private JTextField loginTextInput;
    private JTextField passwordTextInput;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JButton createAccountButton;
    private JLabel messagesLabel;

    public UserLoginView()
    {
        setWindowName(PanelEnum.LOGIN);
    }

    public JPanel getWindowPanel(){
        return WindowPanel;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    public JTextField getPasswordTextInput() {
        return passwordTextInput;
    }

    public JTextField getLoginTextInput() {
        return loginTextInput;
    }

    @Override
    public void cleanAll() {
        messagesLabel.setText("");
        passwordTextInput.setText("");
        loginTextInput.setText("");
    }
}
