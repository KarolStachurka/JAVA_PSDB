package psbd.supplier;

import psbd.models.Delivery;
import psbd.utils.DatabaseConnector;
import psbd.utils.Messages;


import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CreateDeliveryController {

    private CreateDeliveryView view;
    private Messages messages;

    public CreateDeliveryController(CreateDeliveryView view) throws ParseException {
        this.view = view;
        updateList();

        view.getDeliveriesTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getQuantityTextInput().setText(view.getDeliveriesTable().getValueAt(
                        view.getDeliveriesTable().getSelectedRow(), 3).toString()
                );
                view.getExpirationDateInput().setText(view.getDeliveriesTable().getValueAt(
                        view.getDeliveriesTable().getSelectedRow(), 5).toString()
                );
                view.getOrderDateInput().setText(view.getDeliveriesTable().getValueAt(
                        view.getDeliveriesTable().getSelectedRow(), 4).toString()
                );
                view.getIngredientsComboBox().setSelectedItem(view.getDeliveriesTable().getValueAt( view.getDeliveriesTable().getSelectedRow(),1));
                view.getWarehousesComboBox().setSelectedItem(view.getDeliveriesTable().getValueAt( view.getDeliveriesTable().getSelectedRow(),2));

            }
        });

        view.getAddButton().addActionListener(e->{
            if(addDelivery(createNewDelivery()))
            {
                view.cleanAll();
                setIngredientsList();
                setWarehousesList();
                updateList();
            }
        });
        view.getEditButton().addActionListener(e->{
            if(editDelivery(createExistingDelivery()))
            {
                view.cleanAll();
                setIngredientsList();
                setWarehousesList();
                updateList();
            }
        });


        view.getRemoveButton().addActionListener(e->{
            if(removeDelivery(createExistingDelivery()))
            {
                view.cleanAll();
                setIngredientsList();
                setWarehousesList();
                updateList();
            }
        });
    }

    public CreateDeliveryView getView() {
        setIngredientsList();
        setWarehousesList();
        return view;
    }

    private void updateList()
    {
        String [][] data = getDeliveriesList();
        DefaultTableModel model = (DefaultTableModel) view.getDeliveriesTable().getModel();
        if(data.length > 0)
            for(String[] row:data)
            {
                model.addRow(row);
            }
        view.getDeliveriesTable().repaint();
    }

    private String[][] getDeliveriesList()
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM deliveries";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return errorData;
        }
        try {
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            return errorData;
        }
        try {
            while (result.next()) {
                ArrayList<String> dataRow = new ArrayList<>();
                dataRow.add(result.getString("delivery_id"));
                dataRow.add(result.getString("ingredient"));
                dataRow.add(getSelectedStorageName(Integer.valueOf(result.getString("warehouse_id"))));
                dataRow.add(result.getString("quantity"));
                dataRow.add(result.getString("expiration_date"));
                dataRow.add(result.getString("date_of_order"));
                dataList.add(dataRow);
            }
        }
        catch (SQLException e)
        {
            return errorData;
        }
        String [][] data = new String[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i).toArray(new String[0]);
        }

        return data;
    }
    private void setWarehousesList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet table = database.getFullTableData("warehouses");
            view.getWarehousesComboBox().removeAllItems();
            while (table.next())
            {
                view.getWarehousesComboBox().addItem(table.getString("warehouse_name"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private void setIngredientsList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet table = database.getFullTableData("ingredients");
            view.getIngredientsComboBox().removeAllItems();
            while (table.next())
            {
                view.getIngredientsComboBox().addItem(table.getString("name"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private void setMessage(String error)
    {
        view.getMessagesLabel().setText(error);
    }

    private boolean addDelivery(Delivery delivery)
    {
        if(delivery == null)
        {
            setMessage(messages.INVALID_INPUT);
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO deliveries(`ingredient`, `warehouse_id`, `date_of_order`, `expiration_date`, `quantity`, `available_quantity`) " +
                "VALUES(?,?,NOW(),?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, delivery.getIngredient());
            statement.setInt(2,delivery.getWarehouseId());
            statement.setDate(3,delivery.getExpirationDate());
            statement.setDouble(4,delivery.getQuantity());
            statement.setDouble(5,delivery.getAvailableQuantity());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }

    private boolean editDelivery(Delivery delivery)
    {
        if(delivery == null)
        {
            setMessage(messages.INVALID_INPUT);
        }
        delivery.setQuantity(Double.parseDouble(view.getQuantityTextInput().getText()));
        delivery.setExpirationDate(Date.valueOf(view.getExpirationDateInput().getText()));
        delivery.setDateOfOrder(Date.valueOf(view.getOrderDateInput().getText()));
        delivery.setIngredient(view.getIngredientsComboBox().getSelectedItem().toString());
        delivery.setWarehouseId(getSelectedWarehouseId(view.getWarehousesComboBox().getSelectedItem().toString()));

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE deliveries SET `ingredient` = ?, `warehouse_id` = ?, `date_of_order` = ?, `expiration_date` = ?, `quantity` = ? WHERE delivery_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, delivery.getIngredient());
            statement.setInt(2,delivery.getWarehouseId());
            statement.setDate(3,delivery.getDateOfOrder());
            statement.setDate(4,delivery.getExpirationDate());
            statement.setDouble(5,delivery.getQuantity());
            statement.setString(6,delivery.getId());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }

    private boolean removeDelivery(Delivery delivery)
    {
        if(delivery == null)
        {
            setMessage(messages.INVALID_INPUT);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM deliveries WHERE delivery_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        try {
            statement.setString(1, delivery.getId());
            database.setPreparedStatement(statement);
            database.executeStatement();

        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return !checkIfDeliveryExist(delivery.getIngredient());
    }

    private boolean checkIfDeliveryExist(String id)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            return database.checkIfRecordExists("deliveries", "delivery_id", id);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
    }

    private Delivery createNewDelivery()
    {
        try{
            String name = view.getIngredientsComboBox().getSelectedItem().toString();
            int warehouseId = getSelectedWarehouseId(view.getWarehousesComboBox().getSelectedItem().toString());
            double quantity = Double.parseDouble(view.getQuantityTextInput().getText());
            Date expirationDate = convertToSqlDateFormat(view.getExpirationDateInput().getText());

            return new Delivery(name,warehouseId,quantity,expirationDate);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private Delivery createExistingDelivery()
    {
        try{
            String id = view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 0).toString();
            String name = view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 1).toString();
            int warehouseId = getSelectedWarehouseId(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 2).toString());
            double quantity = Double.parseDouble(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 3).toString());
            Date orderDate = Date.valueOf(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 4).toString());
            Date expirationDate = Date.valueOf(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 5).toString());
            Delivery delivery = new Delivery(name,warehouseId,quantity,quantity,expirationDate,orderDate,null,false);
            delivery.setId(id);
            return delivery;
        }
        catch (Exception e)
        {

            return null;
        }
    }

    private int getSelectedWarehouseId(String name)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM warehouses WHERE warehouse_name = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return 0;
        }
        try {
            statement.setString(1,name);
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
            result.next();
            int id = result.getInt("warehouse_id");
            return id;
        }
        catch(SQLException e)
        {
            return 0;
        }
    }
    private String getSelectedStorageName(int id)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM warehouses WHERE warehouse_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return "";
        }
        try {
            statement.setInt(1,id);
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
            result.next();
            return result.getString("warehouse_name");
        }
        catch(SQLException e)
        {
            return "";
        }
    }

    private Date convertToSqlDateFormat(String dateStr) throws ParseException {
        DateFormat srcDf = new SimpleDateFormat("MM/dd/yy");
        // parse the date string into Date object
        java.util.Date date = srcDf.parse(dateStr);
        DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
        // format the date into another format
        dateStr = destDf.format(date);
        Date expirationDate = Date.valueOf(dateStr);
        return expirationDate;
    }

}
