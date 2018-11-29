package view.manager;

import view.PanelEnum;
import view.ViewTemplate;

import javax.swing.*;

public class RecipeManagerView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton backButton;
    private JList list1;
    private JButton addButton;
    private JTextField textField1;
    private JTextField textField2;
    private JList list2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton removeButton;

    public RecipeManagerView()
    {
        setWindowName(PanelEnum.RECIPEMANAGER);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

    }
}
