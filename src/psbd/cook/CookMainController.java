package psbd.cook;

import psbd.models.CurrentSession;
import psbd.models.Ingredient;
import psbd.models.Order;
import psbd.models.Recipe;
import psbd.utils.DatabaseConnector;
import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.OrderStatusEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CookMainController {
    private CookMainView view;
    private ArrayList<Order> orders;
    private ArrayList<Recipe> dishes;
    private ArrayList<Ingredient> currentIngredientList;

    public CookMainController(CookMainView view)
    {
        this.view = view;
        orders = new ArrayList<>();
        dishes = new ArrayList<>();
        currentIngredientList = new ArrayList<>();
        view.getOrdersTable().getColumnModel().getColumn(0).setMaxWidth(60);
        view.getUpdateListButton().addActionListener(e->{
            view.cleanAll();
            getOrdersList();
            updateOrderList();
        });
        view.getSetOrderReadyButton().addActionListener(e->{
            if(checkIfReady())
            {
                removeIngredientsFromStorage();
                if(updateOrder())
                {
                    view.cleanAll();
                    getOrdersList();
                    updateOrderList();
                }
            }


        });
        view.getOrdersTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                DefaultTableModel model = (DefaultTableModel) view.getDishRecipeTable().getModel();
                model.setRowCount(0);
                getCurrentDishList();
                updateDishList();
            }
        });

        view.getOrderDetailsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                currentIngredientList = dishes.get(view.getOrderDetailsTable().getSelectedRow()).getIngredientsList();
                updateIngredientsList();
                if(view.getOrderDetailsTable().getSelectedColumn() == 1)
                {
                    dishes.get(view.getOrderDetailsTable().getSelectedRow()).setRealized(true);
                    updateDishList();
                }
            }
        });
    }

    public CookMainView getView() {
        return view;
    }

    public void login(){
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    private void getOrdersList()
    {
        orders.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT id, realized ,DATE_SUB(delivery_time, INTERVAL 1 HOUR) AS realization_time, order_status FROM orders " +
                "WHERE realized = 0 ORDER BY delivery_time DESC";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
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
                orders.add(new Order(
                        result.getTimestamp("realization_time"),
                        false,
                        OrderStatusEnum.valueOf(result.getString("order_status")),
                        result.getInt("id")
                ));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
    }

    private void getCurrentDishList()
    {
        dishes.clear();
        int id = orders.get(view.getOrdersTable().getSelectedRow()).getId();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT dishes.id, recipes.name, recipes.price FROM dishes " +
                "INNER JOIN recipes ON recipe_id = recipes.id " +
                "WHERE order_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setInt(1,id);
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
                Recipe dish = new Recipe(result.getString("name"),
                        result.getDouble("price"),
                        null,true);
                dish.setId(result.getInt("id"));
                getRecipeIngredientsList(dish);
                dish.setIngredientsList(currentIngredientList);
                dishes.add(dish);
            }
        }
        catch (SQLException e)
        {
        }
    }

    private void getRecipeIngredientsList(Recipe recipe) {
        currentIngredientList.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT ingredient, quantity FROM dishes_ingredients WHERE dish_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if (statement == null) {
            return;
        }
        try {
            statement.setInt(1, recipe.getId());
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        } catch (SQLException e) {
            return;
        }
        try {
            while (result.next()) {
                currentIngredientList.add(new Ingredient(
                        result.getString("ingredient"),
                        null,
                        0,
                        result.getDouble("quantity"),
                        false
                ));
            }
        } catch (SQLException e) {
        }
    }

    private boolean updateOrder()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE orders SET realized = 1, realization_time = NOW(), order_status = ?  WHERE id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            return false;
        }
        try {
            statement.setString(1, OrderStatusEnum.IN_DELIVERY.toString());
            statement.setInt(2, orders.get(view.getOrdersTable().getSelectedRow()).getId());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            return false;
        }
        return true;
    }

    private void removeIngredientsFromStorage()
    {

    }

    private void updateDishList()
    {
        Object [][] data = new Object[dishes.size()][];
        for (int i = 0; i < dishes.size(); i++) {
            data[i] = dishes.get(i).getRecipeModelToCookList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getOrderDetailsTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getOrderDetailsTable().repaint();

    }

    private void updateOrderList()
    {
        Object [][] data = new Object[orders.size()][];
        for (int i = 0; i < orders.size(); i++) {
            data[i] = orders.get(i).getOrderModelToCookList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getOrdersTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getOrdersTable().repaint();
    }

    private void updateIngredientsList()
    {
        Object [][] data = new Object[currentIngredientList.size()][];
        for (int i = 0; i < currentIngredientList.size(); i++) {
            data[i] = currentIngredientList.get(i).getIngredientModelToCookList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getDishRecipeTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getDishRecipeTable().repaint();

    }

    private boolean checkIfReady()
    {
        for(Recipe recipe:dishes)
        {
            if(!recipe.isRealized())
            {
                return false;
            }
        }
        return true;
    }
}
