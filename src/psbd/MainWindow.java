package psbd;

import psbd.client.CreateClientView;
import psbd.user.RestorePasswordView;
import psbd.user.UserLoginView;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private JFrame mainFrame;
    private UserLoginView userLoginView;
    private RestorePasswordView restorePasswordView;
    private CreateClientView createClientView;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainWindow()
    {
        mainFrame = new JFrame("PSDB");
        userLoginView = new UserLoginView();
        mainPanel.setLayout(new CardLayout());
        mainPanel.add(userLoginView.getWindowPanel(), userLoginView.getWindowName().toString());
        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout =  (CardLayout) mainPanel.getLayout();

    }

    public CardLayout getCardLayout() {
        cardLayout =  (CardLayout) mainPanel.getLayout();
        return cardLayout;
    }


    public void addToMainPanel(JPanel windowPanel,PanelEnum name)
    {
        mainPanel.add(windowPanel,name.toString());
    }

    void setWindowActive(PanelEnum name)
    {
        cardLayout =  (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, name.toString());
        mainFrame.revalidate();
    }

}