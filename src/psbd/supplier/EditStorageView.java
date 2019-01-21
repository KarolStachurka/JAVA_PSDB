package psbd.supplier;

import datechooser.beans.DateChooserCombo;
import psbd.utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public class EditStorageView extends ViewTemplate {
    private Messages messages;
    private JButton backButton;
    private JButton addButton;
    private JButton removeButton;
    private JTextField nameTextInput;
    private JTextField quantityTextInput;
    private DateChooserCombo dateTextInput;
    private JPanel WindowPanel;
    private JButton editButton;
    private JTable ingredientsTable;
    private JComboBox storageListComboBox;
    private JComboBox ingredientTypeComboBox;
    private JLabel messagesLabel;

    public EditStorageView()
    {
        setWindowName(PanelEnum.EDITSTORAGE);
        DefaultTableModel model = new DefaultTableModel(messages.MANAGER_WAREHOUSE_EDIT_TABLE_HEADERS,0);
        ingredientsTable.setModel(model);
        setIngredientTypesList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dateTextInput.setDateFormat(format);
    }

    @Override
    public void cleanAll() {
        dateTextInput.setText("");
        nameTextInput.setText("");
        quantityTextInput.setText("");
        DefaultTableModel model = (DefaultTableModel) ingredientsTable.getModel();
        model.setRowCount(0);
        ingredientTypeComboBox.removeAllItems();
        setIngredientTypesList();

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

    public DateChooserCombo getDateTextInput() {
        return dateTextInput;
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
