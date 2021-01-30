package org.liftoff.recipebook.models;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @Column(nullable = true, length = 64)
    private String profilePicture;

    private String bio;

    public User () {}

    public User (String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public User (String username, String password, String bio, String profilePicture) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
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

}

