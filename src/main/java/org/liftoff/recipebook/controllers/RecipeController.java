package org.liftoff.recipebook.controllers;

import org.liftoff.recipebook.models.User;
import org.liftoff.recipebook.models.data.RecipeCategoryRepository;
import org.liftoff.recipebook.models.data.RecipeRepository;
import org.liftoff.recipebook.models.Recipe;
import org.liftoff.recipebook.models.RecipeCategory;
import org.liftoff.recipebook.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    public int setUser (HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        int userId = sessionUser.getId();
        return userId;
    }

    @GetMapping("create")
    public String displayCreateRecipeForm(Model model, HttpServletRequest request){
        int currentUserId = setUser(request);

        model.addAttribute("recipe",new Recipe());
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        return "recipes/create";
    }

    @PostMapping("/create")
    /*@RequestParam String name, @RequestParam String description,
                               @RequestParam RecipeCategory category,
                               @RequestParam String imageUrl, @RequestParam String prepTime,*/
    public String processCreateRecipeForm( @ModelAttribute Recipe recipe, @RequestParam String hiddenIngredients, HttpServletRequest request, Model model){

        //Get the userId from the session
        int currentUserId = setUser(request);
        User current = userRepository.findById(currentUserId).get();

        //save the recipe to the database
        recipe.setUser(current);
        /*recipe.setName(name);
        recipe.setImageUrl(imageUrl);
        recipe.setDescription(description.trim());//added .trim() to get rid of unnecessary white space
        recipe.setPrepTime(prepTime);
        */recipe.setIngredients(hiddenIngredients);/*
        recipe.setCategory(category);*/
        recipeRepository.save(recipe);
        User user = userRepository.findById(currentUserId).get();
        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        model.addAttribute("user", user);

        //split's the string of ingredients to be sent to the view page
        String[] myIngredients = recipe.getIngredients().split("\\$\\$");
        model.addAttribute("myIngredients",myIngredients);

        return "view";
    }


    @GetMapping("delete")
    public String displayDeleteRecipeForm(Model model, HttpServletRequest request) {
        int currentUserId = setUser(request);

        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        model.addAttribute("recipesToDelete",recipeRepository.getAllRecipesByUserId(currentUserId));
        return "recipes/delete";
    }
    
    @PostMapping("delete")
    public String processDeleteRecipeForm(@RequestParam List<Integer> deleteThis, HttpServletRequest request,
                               Model model){
        for (int recipe: deleteThis) {
            recipeRepository.deleteById(recipe);
        }

        int currentUserId = setUser(request);

        model.addAttribute("user", userRepository.findById(currentUserId).get());
        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        model.addAttribute("userRecipes", recipeRepository.getAllRecipesByUserId(currentUserId));
        return "profile";
    }

    @GetMapping("edit")
    public String selectRecipeToEdit(Model model, HttpServletRequest request) {
        int currentUserId = setUser(request);

        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        model.addAttribute("recipesToEdit",recipeRepository.getAllRecipesByUserId(currentUserId));
        return "recipes/edit";
    }

    @PostMapping("edit")
    public String displayEditRecipeForm(@RequestParam int editThis, HttpServletRequest request,
                                        Model model){
        int currentUserId = setUser(request);

        Recipe needToSplit = recipeRepository.findById(editThis);

        //split's the string of ingredients to be sent to the edit form
        String[] currentIngredients = needToSplit.getIngredients().split("\\$\\$");
        model.addAttribute("user", userRepository.findById(currentUserId).get());
        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        model.addAttribute("editThisRecipe",recipeRepository.findById(editThis));
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        model.addAttribute("currentIngredients",currentIngredients);

        return "edit-recipe-form";
    }

    @PostMapping("saveEditedRecipe")
    public String processEditRecipeForm(@RequestParam String name, @RequestParam String description,
                                   @RequestParam String hiddenIngredients, @RequestParam RecipeCategory category,
                                   @RequestParam String imageUrl, @RequestParam String oldRecipeId,
                                   @RequestParam String prepTime, @RequestParam(defaultValue = "") String originalIngredients, Model model,
                                   HttpServletRequest request) {

        String addedIngredients = hiddenIngredients;
        String oldIngredients = originalIngredients;
        String temporaryIngredients;
        String finalIngredients;

        int currentUserId = setUser(request);

        model.addAttribute("profile", userRepository.findById(currentUserId).get());


        int i = Integer.parseInt(oldRecipeId);
        Recipe recipeBeingEdited = recipeRepository.findById(i);


        if(!oldIngredients.equals("")){temporaryIngredients = oldIngredients.replace(",","$$");
            finalIngredients = temporaryIngredients.concat("$$"+addedIngredients);
            recipeBeingEdited.setIngredients(finalIngredients.trim());
        }

        if(oldIngredients.equals("")){
            recipeBeingEdited.setIngredients(addedIngredients.trim());
        }


        if(!imageUrl.trim().equals("")){
            recipeBeingEdited.setImageUrl(imageUrl);
        }

        recipeBeingEdited.setPrepTime(prepTime);
        recipeBeingEdited.setName(name);
        recipeBeingEdited.setDescription(description.trim());//added .trim() to get rid of unnecessary white space
        recipeBeingEdited.setCategory(category);
        recipeRepository.save(recipeBeingEdited);


        String[] myIngredients = recipeBeingEdited.getIngredients().split(", ");
        model.addAttribute("myIngredients",myIngredients);
        model.addAttribute("recipe",recipeBeingEdited);
        return "view";
    }

    @GetMapping("categories")
    public String displayCreateCategoryForm(Model model, HttpServletRequest request) {

        int currentUserId = setUser(request);

        model.addAttribute("profile", userRepository.findById(currentUserId).get());
        model.addAttribute("category", new RecipeCategory());
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        return "/recipes/categories";
    }

    @PostMapping("/categories")
    public String processCreateCategoryForm(@RequestParam String name, Model model, @ModelAttribute @Valid RecipeCategory newRecipeCategory,
                                       @RequestParam ArrayList<RecipeCategory> deleteCategories) {

        for (RecipeCategory category: deleteCategories) {
            recipeCategoryRepository.deleteById(category.getId());
        }

        newRecipeCategory.setName(name);
        recipeCategoryRepository.save(newRecipeCategory);
        return "redirect:/recipes/create";
    }
}
