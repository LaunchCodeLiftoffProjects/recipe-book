package org.liftoff.recipebook.models;

import java.util.ArrayList;

public class UserData {

    public static ArrayList<User> findUser(String value, Iterable<User> allUsers) {

        ArrayList<User> results = new ArrayList<>();

        String lower_val = value.toLowerCase();

        for (User user : allUsers) {

            if (user.getUsername().toLowerCase().contains(lower_val)) {
                results.add(user);

            }
        }
        return results;
    }
}