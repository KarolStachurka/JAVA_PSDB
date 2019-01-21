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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditStorageController {

    private EditStorageView view;
    private Messages messages;
    private Delivery delivery;

    public EditStorageController(EditStorageView view)
    {
        this.view = view;
        setWarehousesList();
        updateList();
        delivery = null;

        view.getIngredientsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getNameTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 0).toString()
                );
                view.getIngredientTypeComboBox().setSelectedItem(
                        view.getIngredientsTable().getValueAt( view.getIngredientsTable().getSelectedRow(),1)
                );
                view.getQuantityTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 2).toString()
                );
                view.getDateTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 3).toString()
                );
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    java.util.Date date = format.parse(view.getIngredientsTable().getValueAt(
                            view.getIngredientsTable().getSelectedRow(), 3).toString());
                    Calendar newDat = new GregorianCalendar();
                    newDat.setTime(date);
                    view.getDateTextInput().setSelectedDate(newDat);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                createDelivery();
            }
        });

        view.getEditButton().addActionListener(e->
        {
            createDelivery();
            if(editIngredient())
            {
                view.cleanAll();
                updateList();
            }
        });

        view.getRemoveButton().addActionListener(e->{
            createDelivery();
            if(removeIngredient())
            {
                view.cleanAll();
                updateList();
            }
        });


        view.getStorageListComboBox().addActionListener(e->
        {
            view.cleanAll();
            updateList();
        });
    }

    public EditStorageView getView() {
        return view;
    }

    private void setWarehousesList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet table = database.getFullTableData("warehouses");
            while (table.next())
            {
                view.getStorageListComboBox().addItem(table.getString("warehouse_name"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private boolean editIngredient()
    {
        if(delivery == null)
        {
            setMessage(messages.NOT_EXISTS);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE deliveries SET available_quantity = ?,  expiration_date = ? WHERE delivery_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setDouble(1,delivery.getQuantity());
            statement.setDate(2, delivery.getExpirationDate());
            statement.setString(3,delivery.getId());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }

    private boolean removeIngredient()
    {
        if(delivery == null)
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE deliveries SET available_quantity = 0 WHERE delivery_id = ?";
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
        return true;
    }

    private void setMessage(String error)
    {
        view.getMessagesLabel().setText(error);
    }

    private void updateList()
    {
        String [][] data = getIngredientsList(view.getStorageListComboBox().getSelectedItem().toString());
        DefaultTableModel model = (DefaultTableModel) view.getIngredientsTable().getModel();
        if(data.length > 0) {
            for (String[] row : data) {
                model.addRow(row);
            }
        }
        view.getIngredientsTable().repaint();
    }

    private String[][] getIngredientsList(String warehouse)
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM deliveries INNER JOIN warehouses ON deliveries.warehouse_id = warehouses.warehouse_id " +
                "INNER JOIN ingredients ON deliveries.ingredient = ingredients.name WHERE warehouse_name = ? AND available_quantity > 0 AND received = 1";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return errorData;
        }
        try {
            statement.setString(1,warehouse);
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
                dataRow.add(result.getString("name"));
                dataRow.add(result.getString("type"));
                dataRow.add(result.getString("available_quantity"));
                dataRow.add(result.getString("expiration_date"));
                dataRow.add(result.getString("delivery_id"));
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

    private void createDelivery()
    {
        try{
            String name = view.getNameTextInput().getText();
            double quantity = Double.parseDouble(view.getQuantityTextInput().getText());
            Date expiration_date = Date.valueOf(view.getDateTextInput().getText());
            int warehouse = 0;
            this.delivery = new Delivery(name,warehouse,quantity,expiration_date);
            this.delivery.setId(view.getIngredientsTable().getValueAt(view.getIngredientsTable().getSelectedRow(), 4).toString());
        }
        catch (Exception e)
        {
        }
    }
}
