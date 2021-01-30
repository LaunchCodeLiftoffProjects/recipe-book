package org.liftoff.recipebook.controllers;

import org.liftoff.recipebook.models.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.liftoff.recipebook.models.data.RecipeCategoryRepository;





@Controller

public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @GetMapping("createCategory")
    public String displayCreateCategory() {
        return "createCategory";
    }

    @PostMapping("createCategory")
    public String createCreateCategory(@RequestParam String name, Model model, RecipeCategory recipeCategory) {
        recipeCategory.setName(name);
        recipeCategoryRepository.save(recipeCategory);
        return "index";
    }
}
