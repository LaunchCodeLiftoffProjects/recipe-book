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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("create-recipe")
    public String renderCreateRecipeForm(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        int userId = sessionUser.getId();

        model.addAttribute("recipe",new Recipe());
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        model.addAttribute("profile", userRepository.findById(userId).get());
        return "create-recipe";
    }

    @PostMapping("/create-recipe")
    public String createRecipe(@RequestParam String name, Recipe recipe, @RequestParam String description,
                               @RequestParam String hiddenIngredients, @RequestParam RecipeCategory category,
                               @RequestParam String imageUrl, @RequestParam String prepTime,
                               HttpSession session,Model model){

        //Get the userId from the session
        int currentUserId = (int) session.getAttribute("user");

        //save the recipe to the database
        recipe.setPrepTime(prepTime);
        recipe.setUserId(currentUserId);
        recipe.setImageUrl(imageUrl);
        recipe.setName(name);
        recipe.setDescription(description.trim());//added .trim() to get rid of unnecessary white space
        recipe.setIngredients(hiddenIngredients);
        recipe.setCategory(category);
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
    public String displayRemoveRecipe(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        int userId = sessionUser.getId();
        model.addAttribute("profile", userRepository.findById(userId).get());
        model.addAttribute("recipesToDelete",recipeRepository.getAllRecipesByUserId(userId));
        return "delete";
    }
    
    @PostMapping("delete")
    public String removeRecipe(@RequestParam List<Integer> deleteThis, HttpServletRequest request,
                               Model model){
        for (int recipe: deleteThis) {
            recipeRepository.deleteById(recipe);
        }

        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        User user = userRepository.findById(sessionUser.getId()).get();
        int userId = sessionUser.getId();
        model.addAttribute("user", user);
        model.addAttribute("profile", userRepository.findById(userId).get());
        model.addAttribute("userRecipes", recipeRepository.getAllRecipesByUserId(userId));
        return "profile";
    }

    @GetMapping("edit-recipe")
    public String displayChooseARecipeToEdit(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        int userId = sessionUser.getId();
        model.addAttribute("profile", userRepository.findById(userId).get());
        model.addAttribute("recipesToEdit",recipeRepository.getAllRecipesByUserId(userId));
        return "edit-recipe";
    }
    @PostMapping("edit-recipe")
    public String displayEditRecipeForm(@RequestParam int editThis, HttpServletRequest request,
                                        Model model){
        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        User user = userRepository.findById(sessionUser.getId()).get();
        int userId = sessionUser.getId();
        Recipe needToSplit = recipeRepository.findById(editThis);

        //split's the string of ingredients to be sent to the edit form
        String[] currentIngredients = needToSplit.getIngredients().split("\\$\\$");
        model.addAttribute("user", user);
        model.addAttribute("profile", userRepository.findById(userId).get());
        model.addAttribute("editThisRecipe",recipeRepository.findById(editThis));
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        model.addAttribute("currentIngredients",currentIngredients);

        return "edit-recipe-form";
    }
    @PostMapping("saveEditedRecipe")
    public String saveEditedRecipe(@RequestParam String name, @RequestParam String description,
                                   @RequestParam String hiddenIngredients, @RequestParam RecipeCategory category,
                                   @RequestParam String imageUrl, @RequestParam String oldRecipeId,
                                   @RequestParam String prepTime, @RequestParam(defaultValue = "") String originalIngredients, Model model,
                                   HttpServletRequest request) {

        String addedIngredients = hiddenIngredients;
        String oldIngredients = originalIngredients;
        String temporaryIngredients;
        String finalIngredients;

        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        User user = userRepository.findById(sessionUser.getId()).get();
        int userId = sessionUser.getId();
        model.addAttribute("profile", userRepository.findById(userId).get());


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


        String[] myIngredients = recipeBeingEdited.getIngredients().split("\\$\\$");
        model.addAttribute("myIngredients",myIngredients);
        model.addAttribute("recipe",recipeBeingEdited);
        return "view";
    }
}
