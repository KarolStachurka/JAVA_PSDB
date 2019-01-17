package psbd.manager;

import psbd.models.CurrentSession;
import psbd.utils.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ManagerMainController {

    private ManagerMainView view;

    public ManagerMainController(ManagerMainView view)
    {
        this.view = view;
        updateDayBoxValues();
        view.getHoursChangeButton().addActionListener(e->{
            if(changeHours())
            {
                view.cleanAll();
            }

        });

        view.getDayComboBox().addActionListener(e->{
            view.cleanAll();
            showHoursValues();

        });
    }

    public ManagerMainView getView() {
        return view;
    }
    public void login(){
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    private boolean changeHours()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE delivery_time SET open_hour = ? , close_hour = ? WHERE day = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            return false;
        }
        try {
            statement.setTime(1, Time.valueOf(view.getTimeOpenTextInput().getText()));
            statement.setTime(2, Time.valueOf(view.getTimeCloseTextInput().getText()));
            statement.setString(3,view.getDayComboBox().getSelectedItem().toString());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            return false;
        }

        return true;
    }

    private void updateDayBoxValues()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet table = database.getFullTableData("delivery_time");
            view.getDayComboBox().removeAllItems();
            while (table.next())
            {
                view.getDayComboBox().addItem(table.getString("day"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }


    }

    private void showHoursValues()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM delivery_time WHERE day = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if (statement == null) {
            return;
        }
        try {
            statement.setString(1,view.getDayComboBox().getSelectedItem().toString());
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        } catch (SQLException e) {
            return;
        }
        try
        {
            result.next();
            view.getTimeOpenTextInput().setText(result.getTime("open_hour").toString());
            view.getTimeCloseTextInput().setText(result.getTime("close_hour").toString());
        } catch (SQLException e) {
            return;
        }
    }
}
