package psbd.admin;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

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

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getEditThisReviewButton() {
        return editThisReviewButton;
    }

    public JButton getRemoveThisReviewButton() {
        return removeThisReviewButton;
    }

    public JButton getRemoveThisUserButton() {
        return removeThisUserButton;
    }
}
