package org.liftoff.recipebook.models;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    private String profilePicture;

    private String bio;

    @OneToMany(mappedBy = "user")
    private final List<Recipe> recipes = new ArrayList<>();

    
    public User () {}

    public User (String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

/*    public User (String username, String password, String bio, String profilePicture, List<Recipe> recipes) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.recipes = recipes;
    }*/

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}

