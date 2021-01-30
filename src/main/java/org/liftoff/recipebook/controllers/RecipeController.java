package org.liftoff.recipebook.controllers;

import org.liftoff.recipebook.models.data.RecipeCategoryRepository;
import org.liftoff.recipebook.models.data.RecipeRepository;
import org.liftoff.recipebook.models.Recipe;
import org.liftoff.recipebook.models.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller

public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @GetMapping("CreateRecipe")
    public String renderCreateRecipeForm(Model model){
        model.addAttribute("recipe",new Recipe());
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        return "CreateRecipe";
    }

    @PostMapping("CreateRecipe")
    public String createRecipe(@RequestParam String name, Recipe recipe, @RequestParam String description,
                               @RequestParam String hiddenIngredients, @RequestParam RecipeCategory category,
                               @RequestParam String imageUrl, HttpSession session){


        //Get the userId from the session
        int currentUserId = (int) session.getAttribute("user");

     System.out.print(currentUserId);

        //save the recipe to th database
        recipe.setUserId(currentUserId);
        recipe.setImageUrl(imageUrl);
        recipe.setName(name);
        recipe.setDescription(description.trim());//added .trim() to get rid of unnecessary white space
        recipe.setIngredients(hiddenIngredients);
        recipe.setCategory(category);
        recipeRepository.save(recipe);
    return "redirect:";
    }

    //this is just to test the url function.
    @GetMapping("testpic")
    public String testPic(Model model){
       model.addAttribute("recipePic",recipeRepository.findById(80));
        System.out.print("something");
        return "testpic";
    }

}
