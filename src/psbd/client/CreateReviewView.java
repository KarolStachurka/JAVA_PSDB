package psbd.client;

import psbd.utils.Messages;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CreateReviewView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JButton confirmButton;
    private JEditorPane reviewEditTextInput;
    private JTextField fileDialogTextField;
    private JTable dishTable;
    private JButton chooseImageButton;
    private JButton starButton1;
    private JButton starButton2;
    private JButton starButton3;
    private JButton starButton4;
    private JButton starButton5;

    public CreateReviewView()
    {
        setWindowName(PanelEnum.CREATEREVIEW);
        DefaultTableModel menuModel = new DefaultTableModel(Messages.CLIENT_REVIEW_TABLE_HEADERS,0);
        dishTable.setModel(menuModel);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel dishTableModel = (DefaultTableModel) dishTable.getModel();
        dishTableModel.setRowCount(0);
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

    public JTable getDishTable() {
        return dishTable;
    }

    public JTextField getFileDialogTextField() {
        return fileDialogTextField;
    }
}
