package psbd.supplier;

import psbd.utils.IngredientsEnum;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SupplierIngredientListView extends ViewTemplate {
    private JButton backButton;
    private JButton addButton;
    private JTextField nameTextInput;
    private JTextField priceTextInput;
    private JButton removeButton;
    private JButton editButton;
    private JTable ingredientsTable;
    private JComboBox ingredientTypeComboBox;
    private JLabel messagesLabel;
    private JPanel WindowPanel;

    public SupplierIngredientListView()
    {
        setWindowName(PanelEnum.INGREDIENTLIST);
        setIngredientTypesList();
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        nameTextInput.setText("");
        priceTextInput.setText("");
        DefaultTableModel model = (DefaultTableModel) ingredientsTable.getModel();
        model.setRowCount(0);
    }

    private void setIngredientTypesList()
    {
        for(IngredientsEnum type: IngredientsEnum.values())
        {
            ingredientTypeComboBox.addItem(type.toString());
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    public JTextField getPriceTextInput() {
        return priceTextInput;
    }

    public JTextField getNameTextInput() {
        return nameTextInput;
    }

    public JTable getIngredientsTable() {
        return ingredientsTable;
    }

    public JComboBox getIngredientTypeComboBox() {
        return ingredientTypeComboBox;
    }
}
