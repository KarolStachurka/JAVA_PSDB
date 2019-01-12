package psbd.manager;

import com.mysql.jdbc.StringUtils;
import psbd.models.Ingredient;
import psbd.models.Recipe;
import psbd.utils.DatabaseConnector;
import psbd.utils.IngredientsEnum;
import psbd.utils.Messages;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeManagerController {

    private RecipeManagerView view;
    private Messages messages;
    private ArrayList<Ingredient> currentIngredientList;

    public RecipeManagerController(RecipeManagerView view)
    {
        this.view = view;
        currentIngredientList = new ArrayList<>();
        updateRecipesTable();

        view.getRecipesTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getRecipeNameTextInput().setText(view.getRecipesTable().getValueAt(
                        view.getRecipesTable().getSelectedRow(), 0).toString()
                );
                view.getRecipePriceTextInput().setText(view.getRecipesTable().getValueAt(
                        view.getRecipesTable().getSelectedRow(),1).toString()
                );
                getRecipeIngredientsList();
                updateIngredientsTable();
            }
        });
        view.getIngredientTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getIngredientsComboBox().setSelectedItem(view.getIngredientTable().getValueAt(
                        view.getIngredientTable().getSelectedRow(), 0));
                view.getQuantityTextInput().setText(view.getIngredientTable().getValueAt(
                        view.getIngredientTable().getSelectedRow(), 3).toString()
                );
                editIngredientFromList(view.getIngredientTable().getSelectedRow());
                //updateIngredientsTable();
            }
        });

        view.getAddRecipeButton().addActionListener(e->{
            if(addRecipe(createRecipe()))
            {
                if(addRecipeDetails(createRecipe()))
                {
                    view.cleanAll();
                    setIngredientsList();
                    setIngredientsList();
                    updateRecipesTable();
                    setMessage(messages.RECORD_CREATED);
                }

            }
        });

        view.getEditRecipeButton().addActionListener(e->{
            if(editRecipe(createRecipe()))
            {
                view.cleanAll();
                setIngredientsList();
                setIngredientsList();
                updateRecipesTable();
                setMessage(messages.RECORD_EDITED);
            }
        });

        view.getRemoveRecipeButton().addActionListener(e->{
            if(removeRecipe(createRecipe()))
            {
                view.cleanAll();
                setIngredientsList();
                setIngredientsList();
                updateRecipesTable();
                setMessage(messages.RECORD_REMOVED);
                currentIngredientList.clear();
            }
        });

        view.getAddIngredientButton().addActionListener(e->{
            if(addIngredient(createIngredient()))
            {
                view.getQuantityTextInput().setText("");
                updateIngredientsTable();
            }
        });

        view.getRemoveIngredientButton().addActionListener(e->{
            if(removeIngredient())
            {
                view.getQuantityTextInput().setText("");
                updateIngredientsTable();
            }

        });

    }

    public RecipeManagerView getView() {
        view.cleanAll();
        updateRecipesTable();
        setIngredientsList();
        return view;
    }

    private void updateRecipesTable()
    {
        Object [][] data = getRecipesList();
        DefaultTableModel model = (DefaultTableModel) view.getRecipesTable().getModel();
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getRecipesTable().repaint();
    }

    private void updateIngredientsTable()
    {
        Object [][] data = new Object[currentIngredientList.size()][];
        for (int i = 0; i < currentIngredientList.size(); i++) {
            data[i] = currentIngredientList.get(i).getIngredientModelToList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getIngredientTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getIngredientTable().repaint();

    }

    private boolean addRecipe(Recipe recipe)
    {
        if(recipe == null)
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO recipes(`name`, `price`, `availability`) " +
                "VALUES(?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1,recipe.getName());
            statement.setDouble(2,recipe.getPrice());
            statement.setInt(3, 1 );
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
    private boolean addRecipeDetails(Recipe recipe)
    {
        if(currentIngredientList.size() < 1)
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO recipes_details(`recipe_id`, `ingredient_id`, `quantity`, `optionality`) " +
                "VALUES((SELECT id FROM recipes WHERE name = ?),(SELECT id FROM ingredients WHERE name = ?),?,?)";
        for(Ingredient ingredient:currentIngredientList)
        {
            PreparedStatement statement = database.getPreparedStatement(sqlQuery);
            if(statement == null)
            {
                setMessage(messages.DATABASE_ERROR);

                return false;
            }
            try {
                statement.setString(1,recipe.getName());
                statement.setString(2,ingredient.getName());
                statement.setDouble(3,ingredient.getQuantity());
                statement.setInt(4,ingredient.isOptional() ? 1 : 0);
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

        }
        return true;
    }

    private boolean removeRecipeDetails(Recipe recipe)
    {
        if(currentIngredientList.size() < 1)
        {
            return true;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM  recipes_details WHERE `recipe_id` = (SELECT id FROM recipes WHERE name = ?) ";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try
        {
            statement.setString(1,recipe.getName());
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

    private boolean editRecipe(Recipe recipe)
    {
        if(!editRecipeDetails(recipe))
        {
           return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE recipes SET `price` = ?, `availability` = ? WHERE `name` = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setDouble(1, recipe.getPrice());
            statement.setInt(2,recipe.isAvailable()? 1 : 0);
            statement.setString(3, recipe.getName());
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

    private boolean editRecipeDetails(Recipe recipe)
    {
        removeRecipeDetails(recipe);
        addRecipeDetails(recipe);
        return true;
    }

    private boolean removeRecipe(Recipe recipe)
    {
        if(!removeRecipeDetails(recipe))
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM  recipes WHERE `name` = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try
        {
            statement.setString(1,recipe.getName());
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

    private boolean addIngredient(Ingredient ingredient)
    {
        if(ingredient == null)
        {
            setMessage(messages.INVALID_INPUT);
            return false;
        }
        currentIngredientList.add(ingredient);

        return true;
    }

    private boolean removeIngredient()
    {

        currentIngredientList.remove(view.getIngredientTable().getSelectedRow());
        return true;
    }

    private Object[][] getRecipesList()
    {
        ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
        Object[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM recipes";
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
                dataRow.add(result.getString("name"));
                dataRow.add(result.getDouble("price"));
                dataRow.add(result.getBoolean("availability"));
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

    private void getRecipeIngredientsList()
    {
        currentIngredientList.clear();
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT ingredients.name, ingredients.price, ingredients.type, optionality, quantity FROM recipes_details " +
                "inner join recipes on recipe_id = recipes.id " +
                "inner join ingredients on ingredient_id = ingredients.id " +
                "WHERE recipes.name = ?;";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setString(1,(view.getRecipesTable().getValueAt(
                    view.getRecipesTable().getSelectedRow(), 0).toString())
            );
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            return;
        }
        try {
            while (result.next()) {
                currentIngredientList.add(new Ingredient(
                        result.getString("ingredients.name"),
                        IngredientsEnum.valueOf(result.getString("ingredients.type")),
                        result.getDouble("ingredients.price"),
                        result.getDouble("quantity"),
                        result.getBoolean("optionality")
                ));
            }
        }
        catch (SQLException e)
        {
            return;
        }
    }

    private Recipe createRecipe()
    {
        try{
            String name = view.getRecipeNameTextInput().getText();
            double price = Double.parseDouble(view.getRecipePriceTextInput().getText());
            ArrayList<Ingredient> ingredients = this.currentIngredientList;
            boolean available;
            try
            {
                available = (boolean)view.getRecipesTable().getValueAt(view.getRecipesTable().getSelectedRow(),2);
            }
            catch(NullPointerException e)
            {
                available = true;
            }
            return new Recipe(name,price,ingredients,available);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private Ingredient createIngredient()
    {
        String name = view.getIngredientsComboBox().getSelectedItem().toString();
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet result =  database.getSingleStringRecord("ingredients","name", name);
            String type = result.getString("type");
            double price = result.getDouble("price");
            double quantity = Double.parseDouble(view.getQuantityTextInput().getText());
            boolean optional;
            try {
                optional = (boolean) view.getIngredientTable().getValueAt(
                        view.getIngredientTable().getSelectedRow(), 4);

            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                optional = false;
            }
            return new Ingredient(name, IngredientsEnum.valueOf(type),price,quantity,optional);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    private void setIngredientsList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try{
            ResultSet table = database.getFullTableData("ingredients");
            view.getIngredientsComboBox().removeAllItems();
            while (table.next())
            {
                view.getIngredientsComboBox().addItem(table.getString("name"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private void editIngredientFromList(int index)
    {
        Ingredient ingredient = createIngredient();
        currentIngredientList.set(index,ingredient);
    }

    private void setMessage(String error)
    {
        view.getMessagesLabel().setText(error);
    }
}
