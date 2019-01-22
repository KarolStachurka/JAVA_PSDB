package psbd.client;

import datechooser.beans.DateChooserCombo;
import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public class CreateOrderView extends ViewTemplate {
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
    private DateChooserCombo orderDateInput;
    private JComboBox timeOpenComboBox;

    public CreateOrderView()
    {
        setWindowName(PanelEnum.CREATEORDER);
        DefaultTableModel menuModel = new DefaultTableModel(Messages.MENU_ORDER_TABLE_HEADERS,0);
        recipesTable.setModel(menuModel);
        MyTableModel recipesModel = new MyTableModel(Messages.MENU_INGREDIENTS_TABLE_HEADERS,3);
        ingredientsTable.setModel(recipesModel);
        DefaultTableModel orderModel = new DefaultTableModel(Messages.MENU_ORDER_TABLE_HEADERS,0);
        orderTable.setModel(orderModel);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        orderDateInput.setDateFormat(format);
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

    public DateChooserCombo getOrderDateInput(){return orderDateInput;}

    public JComboBox getTimeOpenComboBox(){ return timeOpenComboBox;}
}
