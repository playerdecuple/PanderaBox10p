package com.devkor_decuple.PanderaBox.Core.Games;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Yootnori {

    private String channelId;

    private final List<User> userList = new ArrayList<>();
    private final List<Integer> yoots = new ArrayList<>();

    private int remainingBlueTeamMoveTurn = 0;
    private int remainingRedTeamMoveTurn = 0;

    public Yootnori() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public User getGameStarter() {
        return userList.get(0);
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser(User user) {
        userList.remove(user);
    }

}
