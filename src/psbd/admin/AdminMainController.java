package psbd.admin;

import psbd.DatabaseConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminMainController {
    private AdminMainView view;

    public AdminMainController(AdminMainView view)
    {
        this.view = view;
    }

    public AdminMainView getView() {
        return view;
    }

    public void showAllUsers()
    {
        String[] columnNames = {"Type","Login", "Name", "Surname", "Email","Phone Number","Company","PESEL"};
        String [][] data = getUsersList();
        DefaultTableModel model = (DefaultTableModel) view.getUsersTable().getModel();
        model.setColumnIdentifiers(columnNames);
        model.addRow(data[0]);
        view.getUsersTable().repaint();
    }

    private String[][] getUsersList()
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM users";
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
                dataRow.add(result.getString("user_type"));
                dataRow.add(result.getString("user_login"));
                dataRow.add(result.getString("name"));
                dataRow.add(result.getString("surname"));
                dataRow.add(result.getString("email"));
                dataRow.add(result.getString("phone_number"));
                dataRow.add(result.getString("company"));
                dataRow.add(result.getString("pesel"));
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

    public void setCurrentUser()
    {}

}
