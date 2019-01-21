package psbd.courier;

import psbd.models.CurrentSession;
import psbd.models.Order;
import psbd.utils.DatabaseConnector;
import psbd.utils.OrderStatusEnum;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourierMainController {
    private CourierMainView view;
    private ArrayList<Order> orders;

    public CourierMainController(CourierMainView view)
    {
        this.view = view;
        orders = new ArrayList<>();
        view.getUpdateListButton().addActionListener(e->{
            view.cleanAll();
            getOrdersList();
            updateOrderList();

        });

        view.getOrdersTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if(view.getOrdersTable().getSelectedColumn() == 4)
                {
                    orders.get(view.getOrdersTable().getSelectedRow()).setDelivered(true);
                    if(updateOrder()){
                        view.cleanAll();
                        getOrdersList();
                        updateOrderList();
                    }

                }
            }
        });
    }

    public CourierMainView getView() {
        return view;
    }

    public void login(){
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    private boolean updateOrder()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE orders SET delivered = 1, delivery_time = NOW(), order_status = ?  WHERE id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            return false;
        }
        try {
            statement.setString(1, OrderStatusEnum.DELIVERED.toString());
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

    private void getOrdersList()
    {
        orders.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT id, client_addresses.address AS address,delivery_time,price, order_status FROM orders " +
                "INNER JOIN client_addresses ON orders.address_id = client_addresses.address_id " +
                "WHERE order_status = 'IN_DELIVERY' ORDER BY delivery_time DESC";
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

    private void updateOrderList()
    {
        Object [][] data = new Object[orders.size()][];
        for (int i = 0; i < orders.size(); i++) {
            data[i] = orders.get(i).getOrderModelToClientList().toArray(new Object[0]);
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
}
