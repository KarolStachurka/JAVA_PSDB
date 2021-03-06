package psbd.client;

import psbd.models.CurrentSession;
import psbd.models.Ingredient;
import psbd.models.Order;
import psbd.models.Recipe;
import psbd.utils.DatabaseConnector;
import psbd.utils.IngredientsEnum;
import psbd.utils.Messages;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class CreateOrderController {

    private CreateOrderView view;
    private ArrayList<Ingredient> currentIngredientList;
    private ArrayList<Recipe> currentRecipeList;
    private double totalSpentInCurrentMonth;


    public CreateOrderController(CreateOrderView view) {
        this.view = view;
        currentIngredientList = new ArrayList<>();
        currentRecipeList = new ArrayList<>();

        view.getRecipesTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getOrderTable().clearSelection();
                getRecipeIngredientsList();
                updateIngredientsTable();
            }
        });
        view.getOrderTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getRecipesTable().clearSelection();
                List<Ingredient> ingredients = currentRecipeList.get(view.getOrderTable().getSelectedRow()).getIngredientsList().stream().map(ingredient -> new Ingredient(ingredient)).collect(Collectors.toList());
                currentIngredientList = (ArrayList<Ingredient>)ingredients;
                updateIngredientsTable();
            }
        });
        view.getIngredientsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                updateIngredient();
            }
        });

        view.getOrderDateInput().addCommitListener(e->
        {
            setAvailableHours();
        });



        view.getCompleteOrderButton().addActionListener(e->{
            if(completeOrder(createOrder()))
            {
                setMessage(Messages.getInstance().ORDER_COMPLETED);
            }
        });

        view.getAddToOrderButton().addActionListener(e->{
            currentRecipeList.add(createRecipe());
            updateOrderTable();
            updatePrice();
        });

        view.getRemoveFromOrderButton().addActionListener(e->{
            currentRecipeList.remove(view.getOrderTable().getSelectedRow());
            updateOrderTable();
            updatePrice();
        });

    }

    public CreateOrderView getView() {

        view.cleanAll();
        updateBalance();
        setAvailableHours();
        updateMenuTable();
        setAddressList();
        return view;
    }

    private boolean completeOrder(Order order) {
        if(order == null || order.getRecipeList().size() < 1)
        {
            setMessage(Messages.INVALID_INPUT);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO orders(`client_id`, `address_id`,`price`, `discount`, `company_discount`, `order_time`, `delivery_time`) " +
                "VALUES((SELECT user_id FROM users WHERE user_login = ?)," +
                "(SELECT address_id FROM client_addresses WHERE address = ? AND client_id = (SELECT user_id FROM users WHERE user_login = ?)),?,?,?,NOW(),?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(Messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1,CurrentSession.getInstance().getLoggedUser().getLogin());
            statement.setString(2,order.getAddress());
            statement.setString(3,CurrentSession.getInstance().getLoggedUser().getLogin());
            statement.setDouble(4,order.getPrice());
            statement.setDouble(5, order.getDiscount());
            statement.setDouble(6,order.getCompanyDiscount());
            statement.setTimestamp(7,order.getDeliveryTime());
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(Messages.DATABASE_ERROR);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(Messages.DATABASE_ERROR);
            return false;
        }
        return addRecipesToOrder(order.getRecipeList());
    }

    private boolean addRecipesToOrder(ArrayList<Recipe> recipesList)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO dishes(`recipe_id`, `order_id`) " +
                "VALUES((SELECT id FROM recipes WHERE name = ?),(SELECT MAX(id) FROM orders))";
        for(Recipe recipe:recipesList)
        {
            PreparedStatement statement = database.getPreparedStatement(sqlQuery);
            if(statement == null)
            {
                setMessage(Messages.DATABASE_ERROR);

                return false;
            }
            try {
                statement.setString(1,recipe.getName());
                database.setPreparedStatement(statement);
            }
            catch (SQLException e)
            {
                setMessage(Messages.DATABASE_ERROR);
                return false;
            }
            if(!database.executeStatement())
            {
                setMessage(Messages.DATABASE_ERROR);
                return false;
            }
            if(!addIngredientsToRecipe(recipe.getIngredientsList()))
            {
                setMessage(Messages.DATABASE_ERROR);
                return false;
            }

        }
        return true;
    }

    private boolean addIngredientsToRecipe(ArrayList<Ingredient> ingredientsList)
    {
        if(currentIngredientList.size() < 1)
        {
        return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO dishes_ingredients(`dish_id`,`quantity`, `ingredient`) " +
                "VALUES((SELECT MAX(id) FROM dishes),?, ?)";
        for(Ingredient ingredient:ingredientsList)
        {
            if(!ingredient.isIncluded())
            {
                continue;
            }
            PreparedStatement statement = database.getPreparedStatement(sqlQuery);
            if(statement == null)
            {
                setMessage(Messages.DATABASE_ERROR);

                return false;
            }
            try {

                statement.setDouble(1,ingredient.getQuantity());
                statement.setString(2,ingredient.getName());
                database.setPreparedStatement(statement);
            }
            catch (SQLException e)
            {
                setMessage(Messages.DATABASE_ERROR);
                return false;
            }
            if(!database.executeStatement())
            {
                setMessage(Messages.DATABASE_ERROR);
                return false;
            }

        }

        return true;
    }

    private Order createOrder()
    {
        try{
            String login = CurrentSession.getInstance().getLoggedUser().getLogin();
            String address = view.getAddressComboBox().getSelectedItem().toString();
            double discount = getDiscount();
            double companyDiscount = getCompanyDiscount();
            Timestamp orderTime = Timestamp.valueOf(view.getOrderDateInput().getText()+" "+view.getTimeOpenComboBox().getSelectedItem().toString() + ":00");

            return new Order(login,address,currentRecipeList,getFullPrice(),discount,companyDiscount,orderTime);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private double getFullPrice()
    {
        double price = 0;
        if(currentRecipeList.size() > 0)
        {
            for(Recipe recipe: currentRecipeList) {
                price += recipe.getPrice();
            }
        }
        double companyDiscount = getCompanyDiscount();
        double discount = getDiscount();
        if(price > companyDiscount + 10)
            price -= companyDiscount;
        if(price > discount + 15)
            price -= discount;


        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    private  double getDiscount()
    {
        double discount = 0;
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "select discount from discounts where threshold <= ? group by discount desc limit 1 ";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);

        try{
            statement.setDouble(1, totalSpentInCurrentMonth);
            database.setPreparedStatement(statement);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                discount = result.getDouble("discount");
            }


        }
        catch (Exception e)
        {
            // Leave list empty
        }
        return discount;
    }

    private double getCompanyDiscount()
    {
        double discount = 0;
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT discount FROM companies WHERE company_name = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);

        try{
            statement.setString(1, CurrentSession.getInstance().getLoggedUser().getCompany());
            database.setPreparedStatement(statement);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                discount = result.getDouble("discount");
            }


        }
        catch (Exception e)
        {
        }

        return discount;
    }

    private void setMessage(String error) {
        view.getMessagesLabel().setText(error);
    }

    private void setAddressList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT address FROM client_addresses WHERE client_id = (SELECT user_id FROM users WHERE user_login = ?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);

        try{
            statement.setString(1, CurrentSession.getInstance().getLoggedUser().getLogin());
            database.setPreparedStatement(statement);
            ResultSet result = statement.executeQuery();
            view.getAddressComboBox().removeAllItems();
            while (result.next())
            {
                view.getAddressComboBox().addItem(result.getString("address"));
            }

        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private void updateMenuTable()
    {
        Object [][] data = getMenuList();
        DefaultTableModel model = (DefaultTableModel) view.getRecipesTable().getModel();
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getRecipesTable().repaint();
    }


    private Object[][] getMenuList() {
        ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
        Object[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM recipes WHERE availability = 1";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if (statement == null) {
            return errorData;
        }
        try {
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
        Object[][] data = new Object[dataList.size()][];
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
        }
    }

    private Recipe createRecipe()
    {
        try{
            String name = view.getRecipesTable().getValueAt(view.getRecipesTable().getSelectedRow(),0).toString();
            double price = Double.parseDouble(view.getRecipesTable().getValueAt(view.getRecipesTable().getSelectedRow(),1).toString());
            List<Ingredient> ingredients = currentIngredientList.stream().map(ingredient -> new Ingredient(ingredient)).collect(Collectors.toList());
            return new Recipe(name,price,(ArrayList<Ingredient>) ingredients,true);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private void updateIngredientsTable()
    {
        Object [][] data = new Object[currentIngredientList.size()][];
        for (int i = 0; i < currentIngredientList.size(); i++) {
            data[i] = currentIngredientList.get(i).getIngredientModelToClientList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getIngredientsTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getIngredientsTable().repaint();
    }

    private void updateOrderTable()
    {
        Object [][] data = new Object[currentRecipeList.size()][];
        for (int i = 0; i < currentRecipeList.size(); i++) {
            data[i] = currentRecipeList.get(i).getRecipeModelToClientList().toArray(new Object[0]);
        }
        DefaultTableModel model = (DefaultTableModel) view.getOrderTable().getModel();
        model.setRowCount(0);
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getOrderTable().repaint();

    }

    private void updateIngredient() {
        int index = view.getIngredientsTable().getSelectedRow();
        currentIngredientList.get(index).setIncluded(
                (boolean) view.getIngredientsTable().getValueAt(
                        index, 3
                ));
        List<Ingredient> ingredients = currentIngredientList.stream().map(ingredient -> new Ingredient(ingredient)).collect(Collectors.toList());
        currentIngredientList = (ArrayList<Ingredient>)ingredients;
        if (view.getRecipesTable().getSelectionModel().isSelectionEmpty()) {
            currentRecipeList.get(view.getOrderTable().getSelectedRow()).setIngredientsList((ArrayList<Ingredient>) ingredients);

        }
    }

    private void updatePrice()
    {
        double price = getFullPrice();
        view.getPriceLabel().setText(String.valueOf(price));
    }

    private void setAvailableHours()
    {
        Calendar calendar = view.getOrderDateInput().getCurrent();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT open_hour, close_hour FROM delivery_time WHERE id = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return;
        }
        try {
            statement.setInt(1,day);
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            return;
        }
        try {
            result.next();
            LocalTime open = result.getTime("open_hour").toLocalTime();
            LocalTime close = result.getTime("close_hour").toLocalTime();
            view.getTimeOpenComboBox().removeAllItems();
            while(open.isBefore(close))
            {
                view.getTimeOpenComboBox().addItem(open);
                open = open.plusMinutes(30);

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
                "WHERE orders.client_id = (SELECT user_id FROM users WHERE user_login = ?) AND order_time BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND CURRENT_DATE()";
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
        catch (Exception e)
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

}


