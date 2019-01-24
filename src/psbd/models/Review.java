package psbd.models;

public class Review {

    private int id;
    private int grade;
    private int recipeId;
    private int userId;
    private String review;

    public Review(int userId, int recipeId, int grade, String review)
    {
        this.userId = userId;
        this.recipeId = recipeId;
        this.grade = grade;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public String getReview() {
        return review;
    }
}
