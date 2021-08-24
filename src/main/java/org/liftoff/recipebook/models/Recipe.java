package org.liftoff.recipebook.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Recipe{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @NotBlank(message = "Name required")
    @Size(max = 200, message = "200 characters or less")
    private String name;
    private String ingredients;
    private String description;
    private String prepTime;

    @ManyToOne
    @NotNull
    private RecipeCategory category;
    private String imageUrl;

    public Recipe(){}

//    public Recipe(int userId, String name, String description, List<String> ingredients, RecipeCategory category, String imageUrl){
//        this.userId = userId;
//        this.name = name;
//        this.description = description;
//        this.ingredients = ingredients;
//        this.category = category;
//        this.imageUrl = imageUrl;
//    }

//    public Recipe(String name){ this.name = name;}


    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrepTime() {
        return prepTime;
    }
    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public RecipeCategory getCategory() {
        return category;
    }
    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
