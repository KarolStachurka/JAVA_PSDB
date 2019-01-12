package psbd.supplier;

import psbd.utils.IngredientsEnum;
import psbd.utils.PanelEnum;
import psbd.utils.UserEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditStorageView extends ViewTemplate {
    private JButton backButton;
    private JButton addButton;
    private JButton removeButton;
    private JTextField nameTextInput;
    private JTextField quantityTextInput;
    private JTextField dateTextInput;
    private JTextField priceTextInput;
    private JPanel WindowPanel;
    private JButton editButton;
    private JTable ingredientsTable;
    private JComboBox storageListComboBox;
    private JComboBox ingredientTypeComboBox;
    private JLabel messagesLabel;

    public EditStorageView()
    {
        setWindowName(PanelEnum.EDITSTORAGE);
        setIngredientTypesList();
    }

    @Override
    public void cleanAll() {
        dateTextInput.setText("");
        nameTextInput.setText("");
        quantityTextInput.setText("");
        priceTextInput.setText("");
        DefaultTableModel model = (DefaultTableModel) ingredientsTable.getModel();
        model.setRowCount(0);

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JTextField getNameTextInput() {
        return nameTextInput;
    }

    public JTextField getDateTextInput() {
        return dateTextInput;
    }

    public JTextField getPriceTextInput() {
        return priceTextInput;
    }

    public JTextField getQuantityTextInput() {
        return quantityTextInput;
    }

    public JComboBox getIngredientTypeComboBox() {
        return ingredientTypeComboBox;
    }

    public JComboBox getStorageListComboBox() {
        return storageListComboBox;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    public JTable getIngredientsTable() {
        return ingredientsTable;
    }

    private void setIngredientTypesList()
    {
        for(IngredientsEnum type: IngredientsEnum.values())
        {
            ingredientTypeComboBox.addItem(type.toString());
        }
    }
}
