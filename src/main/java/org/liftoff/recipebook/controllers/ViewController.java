package org.liftoff.recipebook.controllers;

import org.liftoff.recipebook.models.Recipe;
import org.liftoff.recipebook.models.User;
import org.liftoff.recipebook.models.data.RecipeCategoryRepository;
import org.liftoff.recipebook.models.data.RecipeRepository;
import org.liftoff.recipebook.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("view")
public class ViewController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/{recipeId}")
    public String view(Model model, @PathVariable int recipeId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = authenticationController.getUserFromSession(session);
        int userId = sessionUser.getId();
        User user = userRepository.findById(userId).get();

        model.addAttribute("recipe", recipeRepository.findById(recipeId));
        model.addAttribute("profile", userRepository.findById(userId).get());
        model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("categories", recipeCategoryRepository.findAll());
        model.addAttribute("user", user);
//        List<String> ingredientList = Arrays.asList(recipe.ingredients.split("$$"));

        return "view";
    }

}