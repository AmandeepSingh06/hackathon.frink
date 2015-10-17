package com.frink.hackathon.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amandeepsingh on 17/10/15.
 */
public class FriendsList implements Serializable {
    private ArrayList<String> users;

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setNames(ArrayList<String> users) {
        this.users = users;
    }
}
