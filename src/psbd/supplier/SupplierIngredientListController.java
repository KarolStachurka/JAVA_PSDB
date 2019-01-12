package psbd.supplier;

import psbd.models.Ingredient;
import psbd.utils.IngredientsEnum;
import psbd.utils.Messages;
import psbd.utils.DatabaseConnector;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierIngredientListController {

    private SupplierIngredientListView view;
    private Messages messages;

    public SupplierIngredientListController(SupplierIngredientListView view)
    {
        this.view = view;
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
    }

    public SupplierIngredientListView getView() {
        return view;
    }

    private boolean addIngredient(Ingredient ingredient)
    {
        if(ingredient == null)
        {
            setMessage(messages.invalidInput);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO ingredients(`name`, `price`, `type`) " +
                "VALUES(?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.databaseError);
            return false;
        }
        try {
            statement.setString(1, ingredient.getName());
            statement.setDouble(2,ingredient.getPrice());
            statement.setString(3, ingredient.getType().toString());
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
        String sqlQuery = "UPDATE ingredients SET price = ?, type = ? WHERE name = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.databaseError);
            return false;
        }
        try {
            statement.setDouble(1,ingredient.getPrice());
            statement.setString(2, ingredient.getType().toString());
            statement.setString(3,ingredient.getName());
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
        String [][] data = getIngredientsList();
        DefaultTableModel model = (DefaultTableModel) view.getIngredientsTable().getModel();

        model.setColumnIdentifiers(columnNames);
        if(data.length > 0)
            for(String[] row:data)
            {
                model.addRow(row);
            }
        view.getIngredientsTable().repaint();
    }

    private String[][] getIngredientsList()
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM ingredients";
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
                dataRow.add(result.getString("name"));
                dataRow.add(result.getString("type"));
                dataRow.add(result.getString("price"));
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

    private Ingredient createIngredient()
    {
        try{
            String name = view.getNameTextInput().getText();
            IngredientsEnum type = IngredientsEnum.valueOf(view.getIngredientTypeComboBox().getSelectedItem().toString());
            double price = Double.parseDouble(view.getPriceTextInput().getText());
            return new Ingredient(name,type,price);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
