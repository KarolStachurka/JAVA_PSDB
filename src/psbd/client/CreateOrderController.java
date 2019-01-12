package psbd.client;

import psbd.models.CurrentSession;
import psbd.models.Ingredient;
import psbd.models.Recipe;
import psbd.utils.DatabaseConnector;
import psbd.utils.IngredientsEnum;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateOrderController {

    private CreateOrderView view;
    private ArrayList<Ingredient> currentIngredientList;
    private ArrayList<Recipe> currentRecipeList;



    public CreateOrderController(CreateOrderView view) {
        this.view = view;
        currentIngredientList = new ArrayList<>();
        currentRecipeList = new ArrayList<>();

        view.getMenuTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                getRecipeIngredientsList();
                updateIngredientsTable();
            }
        });

        view.getAddToOrderButton().addActionListener(e->{

        });

        view.getRemoveFromOrderButton().addActionListener(e->{

        });

    }

    public CreateOrderView getView() {

        view.cleanAll();
        updateMenuTable();
        setAddressList();
        return view;
    }

    public void createOrder() {

    }

    private void setMessage(String error) {
        view.getMessagesLabel().setText(error);
    }

    private void setAddressList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM client_addresses WHERE client_id = (SELECT user_id FROM users WHERE user_login = ?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);

        try{
            statement.setString(1, CurrentSession.getInstance().getLoggedUser().getLogin());
            database.setPreparedStatement(statement);
            ResultSet result = statement.executeQuery();
            view.getAddressComboBox().removeAllItems();
            while (result.next())
            {
                view.getAddressComboBox().addItem(result.getString("address"));
            }

        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private void updateMenuTable()
    {
        Object [][] data = getMenuList();
        DefaultTableModel model = (DefaultTableModel) view.getMenuTable().getModel();
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getRecipesTable().repaint();
    }


    private Object[][] getMenuList() {
        ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
        Object[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM recipes WHERE availability = 1";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if (statement == null) {
            return errorData;
        }
        try {
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        } catch (SQLException e) {
            return errorData;
        }
        try {
            while (result.next()) {
                ArrayList<Object> dataRow = new ArrayList<>();
                dataRow.add(result.getString("name"));
                dataRow.add(result.getDouble("price"));
                dataList.add(dataRow);
            }
        } catch (SQLException e) {
            return errorData;
        }
        Object[][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i).toArray(new Object[0]);
        }
        return data;
    }

    private void getRecipeIngredientsList()
    {
        currentIngredientList.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT ingredients.name, ingredients.price, ingredients.type, optionality, quantity FROM recipes_details " +
                "inner join recipes on recipe_id = recipes.id " +
                "inner join ingredients on ingredient_id = ingredients.id " +
                "WHERE recipes.name = ?;";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setString(1,(view.getMenuTable().getValueAt(
                    view.getMenuTable().getSelectedRow(), 0).toString())
            );
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            return;
        }
        try {
            while (result.next()) {
                currentIngredientList.add(new Ingredient(
                        result.getString("ingredients.name"),
                        IngredientsEnum.valueOf(result.getString("ingredients.type")),
                        result.getDouble("ingredients.price"),
                        result.getDouble("quantity"),
                        !result.getBoolean("optionality")
                ));
            }
        }
        catch (SQLException e)
        {
            return;
        }
    }

    private void updateIngredientsTable()
    {
        Object [][] data = new Object[currentIngredientList.size()][];
        for (int i = 0; i < currentIngredientList.size(); i++) {
            data[i] = currentIngredientList.get(i).getIngredientModelToClientList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getRecipesTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getRecipesTable().repaint();

    }

}


