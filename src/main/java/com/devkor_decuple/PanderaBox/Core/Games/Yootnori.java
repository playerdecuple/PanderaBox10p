package com.devkor_decuple.PanderaBox.Core.Games;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Yootnori {

    private final List<User> userList = new ArrayList<>();
    private final List<Integer> yoots = new ArrayList<>();

    private int remainingBlueTeamMoveTurn = 0;
    private int remainingRedTeamMoveTurn = 0;

    public Yootnori() {
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Integer> getYoots() {
        return yoots;
    }

    public int getRemainingBlueTeamMoveTurn() {
        return remainingBlueTeamMoveTurn;
    }

    public int getRemainingRedTeamMoveTurn() {
        return remainingRedTeamMoveTurn;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser(User user) {
        userList.remove(user);
    }

}
