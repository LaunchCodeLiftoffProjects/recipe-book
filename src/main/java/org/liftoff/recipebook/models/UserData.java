package org.liftoff.recipebook.models;

import java.util.ArrayList;

public class UserData {

    public static ArrayList<User> findUser(String usernameCheck, Iterable<User> allUsers) {

        ArrayList<User> currentUser = new ArrayList<>();

        String username = usernameCheck.toLowerCase();

        for (User user : allUsers) {

            if (user.getUsername().toLowerCase().contains(username)) {
                currentUser.add(user);

            }
        }
        return currentUser;
    }
}