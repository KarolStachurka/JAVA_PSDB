package psbd.supplier;

import psbd.models.Ingredient;
import psbd.utils.DatabaseConnector;
import psbd.utils.IngredientsEnum;
import psbd.utils.Messages;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditStorageController {

    private EditStorageView view;
    private Messages messages;

    public EditStorageController(EditStorageView view)
    {
        this.view = view;
        setWarehousesList();
        updateList();

        view.getIngredientsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getNameTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 0).toString()
                );
                view.getPriceTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 2).toString()
                );
                view.getIngredientTypeComboBox().setSelectedItem(view.getIngredientsTable().getValueAt( view.getIngredientsTable().getSelectedRow(),1));
                view.getDateTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 3).toString()
                );
                view.getQuantityTextInput().setText(view.getIngredientsTable().getValueAt(
                        view.getIngredientsTable().getSelectedRow(), 4).toString()
                );
            }
        });

        view.getAddButton().addActionListener(e->{
            if(addIngredient(createIngredient()))
            {
                view.cleanAll();
                updateList();
            }
        });

        view.getEditButton().addActionListener(e->
        {
            if(editIngredient(createIngredient()))
            {
                view.cleanAll();
                updateList();
            }
        });

        view.getRemoveButton().addActionListener(e->{
            if(removeIngredient(createIngredient()))
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
        ResultSet table = database.getFullTableData("warehouses");
        try{
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

    private boolean addIngredient(Ingredient ingredient)
    {
        if(ingredient == null)
        {
            setMessage(messages.notExists);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO ingredients(`name`, `price`, `expiration_date`, `amount`, `warehouse`, `type`) " +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.databaseError);
            return false;
        }
        try {
            statement.setString(1, ingredient.getName());
            statement.setDouble(2,ingredient.getPrice());
            statement.setDate(3, ingredient.getExpiration_time());
            statement.setDouble(4,ingredient.getAmount());
            statement.setInt(5,getSelectedStorageId());
            statement.setString(6, ingredient.getType().toString());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(messages.databaseError);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.databaseError);
            return false;
        }
        return true;
    }

    private boolean editIngredient(Ingredient ingredient)
    {
        if(!checkIfIngredientExist(ingredient.getName()))
        {
            setMessage(messages.notExists);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE ingredients SET price = ?, amount = ?, warehouse = ?, expiration_date = ? WHERE name = ? AND warehouse = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.databaseError);
            return false;
        }
        try {
            statement.setDouble(1,ingredient.getPrice());
            statement.setDouble(2,ingredient.getAmount());
            statement.setInt(3,getSelectedStorageId());
            statement.setDate(4, ingredient.getExpiration_time());
            statement.setString(5,ingredient.getName());
            statement.setInt(6,ingredient.getWarehouse());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            setMessage(messages.databaseError);
            return false;
        }
        return true;
    }

    private boolean removeIngredient(Ingredient ingredient)
    {
        if(ingredient == null)
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM ingredients WHERE name = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        try {
            statement.setString(1, ingredient.getName());
            database.setPreparedStatement(statement);
            database.executeStatement();

        }
        catch (SQLException e)
        {
            setMessage(messages.databaseError);
            return false;
        }
        return !checkIfIngredientExist(ingredient.getName());
    }

    private boolean checkIfIngredientExist(String name)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            return database.checkIfRecordExists("ingredients", "name", name);
        }
        catch (SQLException e)
        {
            setMessage(messages.databaseError);
            return false;
        }
    }

    private void setMessage(String error)
    {
        view.getMessagesLabel().setText(error);
    }

    private void updateList()
    {
        String[] columnNames = messages.ingredientTableHeaders;
        String [][] data = getIngredientsList(view.getStorageListComboBox().getSelectedItem().toString());
        DefaultTableModel model = (DefaultTableModel) view.getIngredientsTable().getModel();
        model.setColumnIdentifiers(columnNames);
        if(data.length > 0)
            for(String[] row:data)
            {
                model.addRow(row);
            }
        view.getIngredientsTable().repaint();
    }

    private String[][] getIngredientsList(String warehouse)
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM ingredients INNER JOIN warehouses ON ingredients.warehouse = warehouses.warehouse_id WHERE warehouse_name = ?";
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
                dataRow.add(result.getString("price"));
                dataRow.add(result.getString("expiration_date"));
                dataRow.add(result.getString("amount"));
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

    private int getSelectedStorageId()
    {
        String name = view.getStorageListComboBox().getSelectedItem().toString();
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

    private Ingredient createIngredient()
    {
        try{
        String name = view.getNameTextInput().getText();
        IngredientsEnum type = IngredientsEnum.valueOf(view.getIngredientTypeComboBox().getSelectedItem().toString());
        double price = Double.parseDouble(view.getPriceTextInput().getText());
        double quantity = Double.parseDouble(view.getQuantityTextInput().getText());
        Date expiration_time = Date.valueOf(view.getDateTextInput().getText());
        int warehouse = getSelectedStorageId();
        return new Ingredient(name,type,price,quantity,expiration_time,warehouse);
        }
        catch (Exception e)
        {
            return null;
        }
    }


}
