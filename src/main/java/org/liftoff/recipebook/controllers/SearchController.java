package org.liftoff.recipebook.controllers;

import org.liftoff.recipebook.models.data.UserRepository;
import org.liftoff.recipebook.models.User;
import org.liftoff.recipebook.models.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("search")
    public String displaySearchResults(Model model, @RequestParam String searchTerm){

        List<User> results = new ArrayList<>();
        results = UserData.findUser(searchTerm, userRepository.findAll());
        model.addAttribute("results", results);
        model.addAttribute("searchTerm", searchTerm);
        return "search";
    }
}
