package psbd.user;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class UserLoginView extends ViewTemplate {
    private JPanel WindowPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JButton createAccountButton;

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

    @Override
    public void cleanAll() {

    }
}
