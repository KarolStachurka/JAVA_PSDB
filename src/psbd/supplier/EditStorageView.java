package psbd.supplier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class EditStorageView extends ViewTemplate {
    private JButton backButton;
    private JList list1;
    private JButton addButton;
    private JButton removeButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel WindowPanel;
    private JTextField textField5;

    public EditStorageView()
    {
        setWindowName(PanelEnum.EDITSTORAGE);
    }

    @Override
    public void cleanAll() {

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }
}
