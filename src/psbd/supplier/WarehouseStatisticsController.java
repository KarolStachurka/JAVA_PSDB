package psbd.supplier;

import psbd.models.Ingredient;
import psbd.models.Recipe;
import psbd.utils.DatabaseConnector;
import psbd.utils.Messages;

import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarehouseStatisticsController {

    private WarehouseStatisticsView view;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Recipe> dishes;

    public WarehouseStatisticsController(WarehouseStatisticsView view)
    {
        this.view = view;
        ingredients = new ArrayList<>();
        dishes = new ArrayList<>();

        view.getGetIngredientSummaryButton().addActionListener(e->{
            getIngredientsStatistics();
            showIngredientsStatistics();

        });
        view.getOrdersSummaryButton().addActionListener(e->{
            getOrdersStatistics();
            showOrdersStatistics();
        });

    }

    public WarehouseStatisticsView getView() {
        return view;
    }

    private void getOrdersStatistics()
    {
        dishes.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT recipes.name, COUNT(recipes.name) AS total_orders FROM dishes INNER JOIN recipes ON recipe_id = recipes.id " +
                "INNER JOIN orders ON order_id = orders.id " +
                "WHERE orders.order_time BETWEEN ? AND ? GROUP BY recipes.name";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setDate(1, Date.valueOf(view.getStartDayChooser().getText()));
            statement.setDate(2, Date.valueOf(view.getEndDayChooserDayChooser().getText()));
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            return;
        }
        try {
            while (result.next()) {
                dishes.add(new Recipe(result.getString("name"),
                        result.getDouble("total_orders"),
                        null,true));
            }
        }
        catch (SQLException e)
        {
        }

    }

    private void getIngredientsStatistics()
    {
        ingredients.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT ingredient,COUNT(dishes_ingredients.quantity) AS total_amount FROM dishes " +
                "INNER JOIN dishes_ingredients ON dishes_ingredients.dish_id = dishes.id " +
                "INNER JOIN orders ON order_id = orders.id " +
                "WHERE orders.order_time BETWEEN ? AND ? " +
                "GROUP BY dishes_ingredients.ingredient";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setDate(1, Date.valueOf(view.getStartDayChooser().getText()));
            statement.setDate(2, Date.valueOf(view.getEndDayChooserDayChooser().getText()));
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            return;
        }
        try {
            while (result.next()) {
                ingredients.add(new Ingredient(result.getString("ingredient"),
                        null, 0,result.getDouble("total_amount"),false));
            }
        }
        catch (SQLException e)
        {
        }

    }

    private void showOrdersStatistics()
    {
        view.cleanAll();
        DefaultTableModel model = new DefaultTableModel(Messages.SUPPLIER_STATISTICS_ORDERS_HEADERS,0);
        view.getStatisticsTable().setModel(model);
        Object [][] data = new Object[dishes.size()][];
        for (int i = 0; i < dishes.size(); i++) {
            data[i] = dishes.get(i).getRecipeModelToSupplierList().toArray(new Object[0]);
        }
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getStatisticsTable().repaint();
    }

    private void showIngredientsStatistics()
    {
        view.cleanAll();
        DefaultTableModel model = new DefaultTableModel(Messages.SUPPLIER_STATISTICS_INGREDIENTS_HEADERS,0);
        view.getStatisticsTable().setModel(model);
        Object [][] data = new Object[ingredients.size()][];
        for (int i = 0; i < ingredients.size(); i++) {
            data[i] = ingredients.get(i).getIngredientModelSupplierList().toArray(new Object[0]);
        }
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getStatisticsTable().repaint();
    }
}
