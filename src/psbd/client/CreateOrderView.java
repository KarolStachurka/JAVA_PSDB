package psbd.client;

import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CreateOrderView extends ViewTemplate {
    private Messages messages;
    private JButton completeOrderButton;
    private JButton backButton;
    private JPanel WindowPanel;
    private JComboBox addressComboBox;
    private JButton addToOrderButton;
    private JButton removeFromOrderButton;
    private JTable recipesTable;
    private JTable ingredientsTable;
    private JTable orderTable;
    private JLabel priceLabel;
    private JLabel messagesLabel;
    private JLabel pictureOfDishLabel;
    private JTextField dateTextInput;
    private JTextField hourTextInput;

    public CreateOrderView()
    {
        setWindowName(PanelEnum.CREATEORDER);
        DefaultTableModel menuModel = new DefaultTableModel(messages.MENU_ORDER_TABLE_HEADERS,0);
        recipesTable.setModel(menuModel);
        MyTableModel recipesModel = new MyTableModel(messages.MENU_INGREDIENTS_TABLE_HEADERS,3);
        ingredientsTable.setModel(recipesModel);
        DefaultTableModel orderModel = new DefaultTableModel(messages.ORDER_DETAILS_TABLE_HEADERS,0);
        orderTable.setModel(orderModel);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel menuModel = (DefaultTableModel) recipesTable.getModel();
        menuModel.setRowCount(0);
        DefaultTableModel recipeModel = (DefaultTableModel) ingredientsTable.getModel();
        recipeModel.setRowCount(0);
        DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
        orderModel.setRowCount(0);

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddToOrderButton() {
        return addToOrderButton;
    }

    public JButton getCompleteOrderButton() {
        return completeOrderButton;
    }

    public JButton getRemoveFromOrderButton() {
        return removeFromOrderButton;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    public JLabel getPriceLabel() {
        return priceLabel;
    }

    public JLabel getPictureOfDishLabel() {
        return pictureOfDishLabel;
    }

    public JTextField getDateTextInput() {
        return dateTextInput;
    }

    public JTextField getHourTextInput() {
        return hourTextInput;
    }

    public JTable getRecipesTable() {
        return recipesTable;
    }

    public JTable getOrderTable() {
        return orderTable;
    }

    public JTable getIngredientsTable() {
        return ingredientsTable;
    }

    public JComboBox getAddressComboBox() {
        return addressComboBox;
    }
}
