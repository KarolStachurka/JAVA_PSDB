package psbd.client;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CreateReviewView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JButton confirmButton;
    private JEditorPane editorPane1;
    private JTextField fileDialogTextField;

    public CreateReviewView()
    {
        setWindowName(PanelEnum.CREATEREVIEW);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

}
