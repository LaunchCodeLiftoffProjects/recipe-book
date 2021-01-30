package org.liftoff.recipebook.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RecipeCategory extends AbstractEntity{


    private String name;

    @OneToMany(mappedBy = "category")
    private final List<Recipe> recipes = new ArrayList<>();


    public RecipeCategory(String name) {
        this.name = name;
    }

    public RecipeCategory() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
