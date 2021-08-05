package org.liftoff.recipebook.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Recipe extends AbstractEntity{

    private int userId;

    private String name;
    private String description;
    private String ingredients;
    private String prepTime;

    @ManyToOne
    @NotNull
    private RecipeCategory category;

    private String imageUrl;

    public Recipe(){}
    public Recipe(String name,String description,String ingredients,RecipeCategory category,String imageUrl, int userId){
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Recipe(String name){ this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }
}
