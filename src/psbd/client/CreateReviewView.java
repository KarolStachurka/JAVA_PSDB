package psbd.client;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CreateReviewView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JButton confirmButton;
    private JEditorPane reviewEditTextInput;
    private JTextField fileDialogTextField;
    private JTable dishListTable;
    private JButton chooseImageButton;

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

    public JButton getChooseImageButton() {
        return chooseImageButton;
    }

    public JEditorPane getReviewEditTextInput() {
        return reviewEditTextInput;
    }

    public JTable getDishListTable() {
        return dishListTable;
    }

    public JTextField getFileDialogTextField() {
        return fileDialogTextField;
    }
}
