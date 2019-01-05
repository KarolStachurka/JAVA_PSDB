package psbd.supplier;

import psbd.models.CurrentSession;
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
import java.util.ArrayList;

public class SupplierMainController {

    private SupplierMainView view;
    private Messages messages;

    public SupplierMainController(SupplierMainView view)
    {
        this.view = view;
        view.getDeliveriesTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if(view.getDeliveriesTable().getSelectedColumn() == 4)
                {
                    if(editDeliver(createExistingDelivery()))
                    {
                        view.cleanAll();
                        updateList();
                    }

                }

            }
        });
    }

    public SupplierMainView getView() {
        view.cleanAll();
        updateList();
        return view;
    }

    public void login()
    {
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " +  session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    public void logout(){
        CurrentSession session = CurrentSession.getInstance();
        session.setLoggedUser(null);
    }

    private void updateList()
    {
        Object [][] data = getDeliveriesList();
        DefaultTableModel model = (DefaultTableModel) view.getDeliveriesTable().getModel();
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getDeliveriesTable().repaint();
    }

    private boolean editDeliver(Delivery delivery)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE deliveries SET `received` = ?, `date_of_receiving` = NOW() WHERE delivery_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            return false;
        }
        try {
            statement.setInt(1,delivery.isReceived()? 1 : 0 );
            statement.setString(2,delivery.getId());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            return false;
        }

        return database.executeStatement();
    }
    // todo: clean this mess below
    private Delivery createExistingDelivery()
    {
        try{
            String id = view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 0).toString();
            String name = view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 1).toString();
            double quantity = Double.parseDouble(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 3).toString());
            Date orderDate = Date.valueOf(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 5).toString());
            //Date receiveDate = Date.valueOf(view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 6).toString());
            Boolean isReceived = (boolean)view.getDeliveriesTable().getValueAt(view.getDeliveriesTable().getSelectedRow(), 4);
            Delivery delivery = new Delivery(name,0,quantity,quantity,orderDate,orderDate,null,isReceived);
            delivery.setId(id);
            return delivery;
        }
        catch (Exception e)
        {

            return null;
        }
    }


    private Object[][] getDeliveriesList()
    {
        ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
        Object[][] errorData = {{}};

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
                ArrayList<Object> dataRow = new ArrayList<>();
                dataRow.add(result.getString("delivery_id"));
                dataRow.add(result.getString("ingredient"));
                dataRow.add(getSelectedStorageName(Integer.valueOf(result.getString("warehouse_id"))));
                dataRow.add(result.getString("quantity"));
                dataRow.add(result.getBoolean("received"));
                dataRow.add(result.getString("date_of_order"));
                dataRow.add(result.getString("date_of_receiving"));
                dataList.add(dataRow);
            }
        }
        catch (SQLException e)
        {
            return errorData;
        }
        Object [][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i).toArray(new Object[0]);
        }

        return data;
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
}
