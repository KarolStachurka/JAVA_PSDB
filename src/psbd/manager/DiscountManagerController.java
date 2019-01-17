package psbd.manager;

import psbd.utils.Messages;
import psbd.models.Company;
import psbd.utils.DatabaseConnector;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountManagerController {

    private DiscountManagerView view;
    private Messages messages;

    public DiscountManagerController(DiscountManagerView view)
    {
        this.view = view;
        updateDiscountBoxValues();
        view.getCompaniesTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getNipTextInput().setText(view.getCompaniesTable().getValueAt(
                        view.getCompaniesTable().getSelectedRow(), 0).toString()
                );
                view.getCompanyNameTextInput().setText(view.getCompaniesTable().getValueAt(
                        view.getCompaniesTable().getSelectedRow(), 1).toString()
                );
                view.getCompanyDiscountTextInput().setText(view.getCompaniesTable().getValueAt(
                        view.getCompaniesTable().getSelectedRow(), 2).toString()
                );
            }
        });
        view.getAddCompanyButton().addActionListener(e->{
            if(createCompanyAccount(createNewCompany()))
            {
                view.cleanAll();
                setMessage(messages.ACCOUNT_CREATED);
                updateTable();
            }

        });
        view.getEditCompanyButton().addActionListener(e->{
            if(editExistingCompany(createNewCompany()))
            {
                view.cleanAll();
                setMessage(messages.ACCOUNT_EDITED);
                updateTable();
            }
            else
            {
            }
        });
        view.getRemoveCompanyButton().addActionListener(e->{
            if(removeExistingCompany(createNewCompany()))
            {
                view.cleanAll();
                setMessage(messages.ACCOUNT_REMOVED);
                updateTable();
            }
            else
            {
            }
        });
        view.getAcceptDiscountThresholdButton().addActionListener(e->{
            if(changeDiscountThreshold())
            {
                view.cleanAll();
                updateTable();
            }
        });
        view.getDiscountMilestonesComboBox().addActionListener(e->{
            showDiscountValues();
        });

    }

    public DiscountManagerView getView() {
        view.cleanAll();
        updateTable();
        return view;
    }

    private Company createNewCompany()
    {
        try {
            int nip = Integer.valueOf(view.getNipTextInput().getText());
            double discountValue = Double.valueOf(view.getCompanyDiscountTextInput().getText());
            String name = view.getCompanyNameTextInput().getText();
            return new Company(nip,name,discountValue);
        }
        catch (NumberFormatException e)
        {
            setMessage(messages.UNFILLED_NECESSARY_FIELDS);
            return null;
        }
    }

    private boolean createCompanyAccount(Company company)
    {
        if(company == null)
        {
            setMessage(messages.UNFILLED_NECESSARY_FIELDS);
            return false;
        }
        if(checkIfCompanyExist(String.valueOf(company.getNip())))
        {
            setMessage(messages.ALREADY_EXISTS);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO companies(nip, company_name, discount) " +
                "VALUES(?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setInt(1,company.getNip());
            statement.setString(2,company.getName());
            statement.setDouble(3, company.getDiscountValue());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }
    private boolean editExistingCompany(Company company)
    {
        if(!checkIfCompanyExist(String.valueOf(company.getNip())))
        {
            setMessage(messages.NOT_EXISTS);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE companies SET company_name = ? ,discount = ? WHERE nip = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, company.getName());
            statement.setDouble(2, company.getDiscountValue());
            statement.setInt(3, company.getNip());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }
    private boolean removeExistingCompany(Company company)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM companies WHERE nip = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        try {
            statement.setInt(1, company.getNip());
            database.setPreparedStatement(statement);
            database.executeStatement();

        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return !checkIfCompanyExist((String.valueOf(company.getNip())));
    }

    private void setMessage(String error)
    {
        view.getMessagesLabel().setText(error);
    }

    private boolean checkIfCompanyExist(String nip)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            return database.checkIfRecordExists("companies", "nip", nip);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
    }

    public void updateTable()
    {
        String[] columnNames = messages.COMPANY_TABLE_HEADERS;
        String [][] data = getCompaniesList();
        DefaultTableModel model = (DefaultTableModel) view.getCompaniesTable().getModel();
        model.setColumnIdentifiers(columnNames);
        for(String[] row:data)
        {
            model.addRow(row);
        }
        view.getCompaniesTable().repaint();
    }

    private String[][] getCompaniesList()
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            ResultSet result = database.getFullTableData("companies");
            while (result.next()) {
                ArrayList<String> dataRow = new ArrayList<>();
                dataRow.add(result.getString("nip"));
                dataRow.add(result.getString("company_name"));
                dataRow.add(result.getString("discount"));
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

    private boolean changeDiscountThreshold()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE discounts SET threshold = ? ,discount = ? WHERE threshold_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setDouble(1, Double.parseDouble(view.getThresholdTextInput().getText()));
            statement.setDouble(2, Double.parseDouble(view.getDiscountTextInput().getText()));
            statement.setString(3,view.getDiscountMilestonesComboBox().getSelectedItem().toString());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }

        return true;
    }

    private void updateDiscountBoxValues()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet table = database.getFullTableData("discounts");
            view.getDiscountMilestonesComboBox().removeAllItems();
            while (table.next())
            {
                view.getDiscountMilestonesComboBox().addItem(table.getString("threshold_id"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }


    }

    private void showDiscountValues()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM discounts WHERE threshold_id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if (statement == null) {
            return;
        }
        try {
            statement.setString(1,view.getDiscountMilestonesComboBox().getSelectedItem().toString());
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        } catch (SQLException e) {
            return;
        }
        try
        {
            result.next();
            view.getThresholdTextInput().setText(result.getBigDecimal("threshold").toString());
            view.getDiscountTextInput().setText(result.getBigDecimal("discount").toString());

        } catch (SQLException e) {
            return;
        }
    }
}
