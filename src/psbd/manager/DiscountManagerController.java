package psbd.manager;

import com.mysql.jdbc.StringUtils;
import psbd.Communicates;
import psbd.Company;
import psbd.DatabaseConnector;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountManagerController {

    private DiscountManagerView view;
    private Communicates communicates;

    public DiscountManagerController(DiscountManagerView view)
    {
        this.view = view;
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
                setCommunicate(communicates.accountCreated);
                updateTable();
            }

        });
        view.getEditCompanyButton().addActionListener(e->{
            if(editExistingCompany(createNewCompany()))
            {
                view.cleanAll();
                setCommunicate(communicates.accountEdited);
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
                setCommunicate(communicates.accountRemoved);
                updateTable();
            }
            else
            {
            }
        });

    }

    public DiscountManagerView getView() {
        return view;
    }

    private Company createNewCompany()
    {
        try {
            int nip = Integer.valueOf(view.getNipTextInput().getText());
            int discountValue = Integer.valueOf(view.getCompanyDiscountTextInput().getText());
            String name = view.getCompanyNameTextInput().getText();
            return new Company(nip,name,discountValue);
        }
        catch (NumberFormatException e)
        {
            setCommunicate(communicates.unfilledNecessaryFields);
            return null;
        }
    }

    private boolean createCompanyAccount(Company company)
    {
        if(company == null)
        {
            setCommunicate(communicates.unfilledNecessaryFields);
            return false;
        }
        if(checkIfCompanyExist(String.valueOf(company.getNip())))
        {
            setCommunicate(communicates.alreadyExists);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO companies(nip, company_name, discount) " +
                "VALUES(?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setCommunicate(communicates.databaseError);
            return false;
        }
        try {
            statement.setInt(1,company.getNip());
            statement.setString(2,company.getName());
            statement.setInt(3, company.getDiscountValue());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setCommunicate(communicates.databaseError);
            return false;
        }
        if(!database.executeStatement())
        {
            setCommunicate(communicates.databaseError);
            return false;
        }
        return true;
    }
    private boolean editExistingCompany(Company company)
    {
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
            return false;
        }
        return !checkIfCompanyExist((String.valueOf(company.getNip())));
    }

    private void setCommunicate(String error)
    {
        view.getCommunicatesLabel().setText(error);
    }

    private boolean checkIfCompanyExist(String nip)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            return database.checkIfRecordExists("companies", "nip", nip);
        }
        catch (SQLException e)
        {
            setCommunicate(communicates.databaseError);
            return false;
        }
    }

    public void updateTable()
    {
        String[] columnNames = {"NIP","Name", "Discount"};
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
        ResultSet result = database.getFullTableData("companies");
        try {
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
}
