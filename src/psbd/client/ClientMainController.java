package psbd.client;

import psbd.models.CurrentSession;
import psbd.models.Order;
import psbd.models.Recipe;
import psbd.utils.DatabaseConnector;
import psbd.utils.Messages;
import psbd.utils.OrderStatusEnum;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientMainController {

    private ClientMainView view;
    private ArrayList<Order> orders;
    private ArrayList<Recipe> dishes;
    private double totalSpentInCurrentMonth;

    public ClientMainController(ClientMainView view)
    {
        this.view = view;
        orders = new ArrayList<>();
        dishes = new ArrayList<>();
        view.getClientOrderTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                getCurrentDishList();
                updateDishList();
            }
        });
    }

    public ClientMainView getView() {
        return view;
    }

    public void login()
    {
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        getOrdersList();
        updateOrderList();
        view.getUserDataLabel().setText(text);
    }

    public void logout(){
        CurrentSession session = CurrentSession.getInstance();
        session.setLoggedUser(null);
    }

    private void updateOrderList()
    {
        Object [][] data = new Object[orders.size()][];
        for (int i = 0; i < orders.size(); i++) {
            data[i] = orders.get(i).getOrderModelToClientList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getClientOrderTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getClientOrderTable().repaint();

    }

    private void getOrdersList()
    {
        orders.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT id, client_addresses.address AS address,delivery_time,price, order_status FROM orders " +
        "INNER JOIN client_addresses ON orders.address_id = client_addresses.address_id " +
        "WHERE orders.client_id = (SELECT user_id FROM users WHERE user_login = ?) ORDER BY delivery_time DESC";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setString(1,CurrentSession.getInstance().getLoggedUser().getLogin());
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
                orders.add(new Order(result.getString("address"),
                        result.getDouble("price"),
                        result.getTimestamp("delivery_time"),
                        OrderStatusEnum.valueOf(result.getString("order_status")),
                        result.getInt("id")
                ));
            }
        }
        catch (SQLException e)
        {
        }
    }

    private void getCurrentDishList()
    {
        dishes.clear();
        int id = orders.get(view.getClientOrderTable().getSelectedRow()).getId();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT recipes.name, recipes.price FROM dishes " +
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
                dishes.add(new Recipe(result.getString("name"),
                        result.getDouble("price"),
                        null,true));
            }
        }
        catch (SQLException e)
        {
        }

    }
    private void updateBalance()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT SUM(price) AS total FROM orders " +
                "INNER JOIN client_addresses ON orders.address_id = client_addresses.address_id " +
                "WHERE orders.client_id = 30 AND order_time BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND CURRENT_DATE()";
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
            result.next();
            totalSpentInCurrentMonth = result.getDouble("total");
        }
        catch (SQLException e)
        {
            totalSpentInCurrentMonth = 0;
        }
    }

    private void updateDishList()
    {
        Object [][] data = new Object[dishes.size()][];
        for (int i = 0; i < dishes.size(); i++) {
            data[i] = dishes.get(i).getRecipeModelToClientList().toArray(new Object[0]);
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

}
