package psbd.manager;

import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RecipeManagerView extends ViewTemplate {
    private Messages messages;
    private JPanel WindowPanel;
    private JButton backButton;
    private JButton addRecipeButton;
    private JTextField recipeNameTextInput;
    private JTextField recipePriceTextInput;
    private JComboBox IngredientsComboBox;
    private JButton removeRecipeButton;
    private JTable recipesTable;
    private JButton editRecipeButton;
    private JTable ingredientTable;
    private JTextField quantityTextInput;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JLabel messagesLabel;

    public RecipeManagerView()
    {
        setWindowName(PanelEnum.RECIPEMANAGER);
        MyTableModel recipesModel = new MyTableModel(messages.RECIPES_TABLE_HEADERS,2);
        recipesTable.setModel(recipesModel);
        MyTableModel model = new MyTableModel(messages.RECIPE_INGREDIENTS_TABLE_HEADERS,4);
        ingredientTable.setModel(model);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        recipeNameTextInput.setText("");
        recipePriceTextInput.setText("");
        quantityTextInput.setText("");
        messagesLabel.setText("");
        IngredientsComboBox.removeAllItems();
        DefaultTableModel model1 = (DefaultTableModel) ingredientTable.getModel();
        DefaultTableModel model2 = (DefaultTableModel) recipesTable.getModel();
        model1.setRowCount(0);
        model2.setRowCount(0);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddRecipeButton() {
        return addRecipeButton;
    }

    public JButton getRemoveRecipeButton() {
        return removeRecipeButton;
    }

    public JButton getEditRecipeButton() {
        return editRecipeButton;
    }

    public JButton getAddIngredientButton() {
        return addIngredientButton;
    }

    public JButton getRemoveIngredientButton() {
        return removeIngredientButton;
    }


    public JTextField getQuantityTextInput() {
        return quantityTextInput;
    }

    public JTextField getRecipeNameTextInput() {
        return recipeNameTextInput;
    }

    public JTextField getRecipePriceTextInput() {
        return recipePriceTextInput;
    }

    public JComboBox getIngredientsComboBox() {
        return IngredientsComboBox;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    public JTable getIngredientTable() {
        return ingredientTable;
    }

    public JTable getRecipesTable() {
        return recipesTable;
    }
}
