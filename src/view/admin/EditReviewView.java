package view.admin;

import view.PanelEnum;
import view.ViewTemplate;

import javax.swing.*;

public class EditReviewView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JButton removeThisReviewButton;
    private JButton editThisReviewButton;
    private JList list1;
    private JList list2;
    private JTextArea textArea1;
    private JButton removeThisUserButton;

    public EditReviewView()
    {
        setWindowName(PanelEnum.EDITREVIEW);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }
}
