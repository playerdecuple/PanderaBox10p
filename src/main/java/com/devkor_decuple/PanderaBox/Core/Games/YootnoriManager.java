package com.devkor_decuple.PanderaBox.Core.Games;

import java.util.ArrayList;
import java.util.List;

public class YootnoriManager {

    public List<Yootnori> games = new ArrayList<>();

    public Yootnori createYootnoriGame(String channelId) {

        Yootnori y = new Yootnori();
        y.setChannelId(channelId);
        games.add(y);

        return y;

    }

    public void removeYootnoriGame(String channelId) {

        Yootnori y = findYootnoriGame(channelId);

        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getChannelId().equals(y.getChannelId())) {
                games.remove(i);
                break;
            }
        }

    }

    public Yootnori findYootnoriGame(String channelId) {

        for (Yootnori game : games) {
            if (game.getChannelId().equals(channelId)) {
                return game;
            }
        }

        return createYootnoriGame(channelId);

    }

    public boolean yootnoriGameExists(String channelId) {

        for (Yootnori game : games) {
            if (game.getChannelId().equals(channelId)) {
                return true;
            }
        }

        return false;

    }

}
