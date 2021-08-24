package org.liftoff.recipebook.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RecipeCategory{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "category")
    private final List<Recipe> recipes = new ArrayList<>();

    public RecipeCategory() {}


    public int getId() {
        return id;
    }

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
