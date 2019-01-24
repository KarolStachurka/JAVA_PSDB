package psbd.client;

import psbd.models.CurrentSession;
import psbd.models.Recipe;
import psbd.models.Review;
import psbd.utils.DatabaseConnector;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateReviewController {

    private CreateReviewView view;
    private ArrayList<ArrayList<Object>>  currentRecipeList;
    private int grade;

    public CreateReviewController(CreateReviewView view)
    {
        this.view = view;
        currentRecipeList = new ArrayList<>();
        grade = 5;
        view.getStarButton1().addActionListener(e->{
            grade = 1;
        });
        view.getStarButton2().addActionListener(e->{
            grade = 2;
        });
        view.getStarButton3().addActionListener(e->{
            grade = 3;
        });
        view.getStarButton4().addActionListener(e->{
            grade = 4;
        });
        view.getStarButton5().addActionListener(e->{
            grade = 5;
        });

        view.getConfirmButton().addActionListener(e->{
            if(updateReview(createReview()))
            {
                view.cleanAll();
                updateRecipeTable();
            }

        });

        view.getDishTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);

            }
        });
    }

    public CreateReviewView getView() {
        view.cleanAll();
        try {
            updateRecipeTable();
        }
        catch (NullPointerException e)
        {

        }
        return view;
    }

    private boolean updateReview(Review review)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO reviews(recipe_id, user_id, grade, opinion) VALUES(?,?,?,?) ";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            return false;
        }
        try {
            statement.setInt(1,review.getRecipeId());
            statement.setInt(2,review.getUserId());
            statement.setInt(3,review.getGrade());
            statement.setString(4,review.getReview());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            return false;
        }
        return  database.executeStatement();
    }

    private Review createReview(){
        String review = view.getReviewEditTextInput().getText();

        return new Review(getUserId(),getRecipeId(),grade,review);

    }

    private int getUserId()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            ResultSet result = database.getSingleStringRecord("users", "user_login", CurrentSession.getInstance().getLoggedUser().getLogin());
            return result.getInt("user_id");
        }
        catch (SQLException e)
        {
            return -1;
        }
    }



    private int getRecipeId()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String recipe = view.getDishTable().getValueAt(view.getDishTable().getSelectedRow(),0).toString();
        try {
            ResultSet result = database.getSingleStringRecord("recipes", "name", recipe);
            return result.getInt("id");
        }
        catch (SQLException e)
        {
            return -1;
        }
    }

    private void updateRecipeTable()
    {
        Object [][] data = getMenuList();
        DefaultTableModel model = (DefaultTableModel) view.getDishTable().getModel();
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getDishTable().repaint();
    }


    private Object[][] getMenuList() {
        ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
        Object[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "select recipes.name, recipes.price FROM recipes " +
                "INNER JOIN dishes ON dishes.recipe_id = recipes.id INNER JOIN orders ON dishes.order_id = orders.id " +
                "INNER JOIN users ON orders.client_id = users.user_id " +
                "WHERE availability = 1  AND users.user_login = ? GROUP BY recipes.name";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if (statement == null) {
            return errorData;
        }
        try {
            statement.setString(1, CurrentSession.getInstance().getLoggedUser().getLogin());
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
        currentRecipeList = dataList;
        Object[][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i).toArray(new Object[0]);
        }
        return data;
    }

}
