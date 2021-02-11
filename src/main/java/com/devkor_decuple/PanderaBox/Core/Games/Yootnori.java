package com.devkor_decuple.PanderaBox.Core.Games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Yootnori {

    private String channelId;
    public TextChannel c;

    private final List<User> userList = new ArrayList<>();
    public List<User> blueTeam = new ArrayList<>();
    public List<User> redTeam = new ArrayList<>();

    public int[] blueMal = new int[4];
    public int[] redMal = new int[4];

    public List<User> turnOrder = new ArrayList<>();

    private List<Integer> yoots = new ArrayList<>();
    public List<Integer> moves = new ArrayList<>();

    public boolean nowTurn_team = true;
    /*
     * nowTurn_team == true means 'now turn is blue team'
     * nowTurn_team == false means 'now turn is red team'
     */
    
    public int nowTurn = 0;

    public boolean canChangeOrderOfYoot = true;
    /*
    public int trapDifficulty = 0;
    public boolean allowFreeGul = false;
    public boolean allowBackDoBeforeStart = true;
    public boolean hardArrive = false;
    public int unluckyBuff = 3;
     */

    public boolean nowPlaying = false;
    public int canCountOfThrowYoot = 1;

    public Yootnori() {
        for (int i = 0; i < 4; i++) {
            blueMal[i] = -1;
            redMal[i] = -1;
        } // Initialize 'Mal'

        initializeBoard();
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

    public User getGameStarter() {
        return userList.get(0);
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser(User user) {
        userList.remove(user);
    }

    // YOOTNORI GAME MAIN //

    /*
     * Yootnori board introduce
     *
     * Q 8  o 7  o 6  o 5  Q 4
     * 
     * o 9  o 21      o 16 o 3
     *         o 22o 17
     * o 10      Q 18      o 2
     *         o 19o 23
     * o 11 o 20      o 24 o 1
     * 
     * Q 12 o 13 o 14 o 15 Q 0
     */

    String[] slots = new String[29];

    public void initializeBoard() {

        for (int i = 0; i < slots.length; i++) {

            if (i == 0 || i == 5 || i == 10 || i == 15 || i == 22) {
                slots[i] = ":orange_circle:";
            } else {
                slots[i] = ":white_circle:";
            }

        }

    }

    public String showMalBoard() {

        return  slots[10] + "                  " + slots[9] + "                 " + slots[8] + "                 " + slots[7] + "                 " + slots[6] + "                 " + slots[5] + "\n" +
                "\n" +
                "\n" +
                "                 " + slots[25] + "                                                                            " + slots[20] + "\n" +
                slots[11] + "                                                                                                               " + slots[4] + "\n" +
                "\n" +
                "                                        " + slots[26] + "                                  " + slots[21] + "\n" +
                "\n" +
                slots[12] + "                                                                                                               " + slots[3] + "\n" +
                "\n" +
                "                                                            " + slots[22] + "\n" +
                "\n" +
                slots[13] + "                                                                                                               " + slots[2] + "\n" +
                "\n" +
                "                                        " + slots[23] + "                                  " + slots[27] + "\n" +
                "\n" +
                slots[14] + "                                                                                                               " + slots[1] + "\n" +
                "                " + slots[24] + "                                                                             " + slots[28] + "\n" +
                "\n" +
                "\n" +
                slots[15] + "                  " + slots[16] + "                   " + slots[17] + "                 " + slots[18] + "                " + slots[19] + "                 " + slots[0];

    }

    public void gameStart(TextChannel c) {

        List<User> users = new ArrayList<>(userList);
        Collections.shuffle(users);

        for (int i = 0; i < users.size(); i++) {
            if (i % 2 == 0) {
                blueTeam.add(users.get(i));
            } else {
                redTeam.add(users.get(i));
            }

            turnOrder.add(users.get(i));
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("파란 팀입니다!");
        StringBuilder sb = new StringBuilder();

        for (User user : blueTeam) {
            sb.append(user.getAsMention()).append("\n");
        }

        eb.setDescription(sb.toString());
        eb.setColor(Color.blue);
        c.sendMessage(eb.build()).queue();


        eb = new EmbedBuilder();
        eb.setTitle("빨간 팀입니다!");
        sb = new StringBuilder();

        for (User user : redTeam) {
            sb.append(user.getAsMention()).append("\n");
        }

        eb.setDescription(sb.toString());
        eb.setColor(Color.red);
        c.sendMessage(eb.build()).queue();


        c.sendMessage("시작은 " + turnOrder.get(nowTurn).getAsMention() + "님입니다! `/윷 던지기` 명령어로 윷을 던지세요!").queue();

        nowPlaying = true;

    }

    public int throwYoot() {

        yoots = new ArrayList<>();
        canCountOfThrowYoot--;

        int move = 0;

        for (int i = 0; i < 4; i++) {
            yoots.add(new Random().nextInt(2));
        }

        for (int i = 0; i < 4; i++) {
            if (yoots.get(i) == 1) {
                move++;
            }
        }

        int out = new Random().nextInt(100);

        if (yoots.get(0) == 1 && yoots.get(1) == 1 && yoots.get(2) == 1 && yoots.get(3) == 1) {
            canCountOfThrowYoot++;
        }

        if (yoots.get(0) == 0 && yoots.get(1) == 0 && yoots.get(2) == 0 && yoots.get(3) == 0) {
            move = 5; // Mo
            canCountOfThrowYoot++;
        }

        if (yoots.get(0) == 0 && yoots.get(1) == 0 && yoots.get(2) == 0 && yoots.get(3) == 1) {
            move = -1; // Back-Do
        }

        if (out > 95) {
            move = 0; // Nack
            nextTurn();
            return move;
        }

        moves.add(move);
        return move;

    }

    public void move(int value, int id) {
        id--;

        if (nowTurn_team) {
            if (blueMal[id] != -1) {

                log("Checkpoint A");

                int i = blueMal[id];
                moves.remove(Integer.valueOf(value));

                List<Integer> carried = new ArrayList<>();

                for (int j = 0; j < blueMal.length; j++) {
                    if (j != id && (blueMal[j] >= 0 && blueMal[j] == blueMal[id])) {
                        carried.add(j);
                    }
                }

                log(carried);

                log("Checkpoint B");

                if (i == 0 || i == 5 || i == 10 || i == 15 || i == 22) {
                    slots[i] = ":orange_circle:";
                } else {
                    slots[i] = ":white_circle:";
                }

                log("Checkpoint C");

                if (i == 5 || i == 10) {
                    blueMal[id] += 14;
                } else if (i == 22) {
                    blueMal[id] += 4;
                }

                log("Checkpoint D");
                
                if (i == 10 && value == 3) {
                    blueMal[id] += 8;
                } else if (i == 25 && value == 2) {
                    blueMal[id] -= 5;
                } else if (i == 26 && value == 1) {
                    blueMal[id] -= 4;
                }

                if ((i == 20 && value == 5) || (i == 21 && value == 4) || (i == 23 && value == 2) || (i == 24 && value == 1)) {
                    blueMal[id] = 15;
                }

                log("Checkpoint E");

                blueMal[id] += value;

                if ((i == 10 || (i >= 25 && i < 27)) && blueMal[id] > 27) {
                    blueMal[id]--;
                }
                
                if (blueMal[id] == 30) {
                    blueMal[id] = 0;
                } else if (blueMal[id] > 30) {
                    blueMal[id] = -2;
                }

                log("Checkpoint G");

                System.out.println("WHERE NOW MOVED MAL IS : " + blueMal[id]);

                if (blueMal[id] >= 0 && !slots[blueMal[id]].equals(":orange_circle:") && !slots[blueMal[id]].equals(":white_circle:")) {
                    boolean catched = false;
                    for (int j = 0; j < blueMal.length; j++) {
                        if (j != id && blueMal[id] == redMal[j]) {
                            redMal[j] = -1;
                            catched = true;
                        }
                    }

                    log("Checkpoint C");

                    if (catched) {
                        canCountOfThrowYoot++;
                        c.sendMessage("상대 편의 말을 잡았습니다! 윷을 한 번 더 던질 수 있습니다!").queue();
                    }
                }

                for (Integer integer : carried) {
                    blueMal[integer] = blueMal[id];
                }

                log("Checkpoint I");

                if (blueMal[id] >= 0) {
                    slots[blueMal[id]] = getMalEmote(id + 4);
                }

                log("Checkpoint J");
            } else {

                log("Checkpoint A");

                blueMal[id] += value + 1;
                moves.remove(Integer.valueOf(value));
                System.out.println("WHERE NOW MOVED MAL IS : " + blueMal[id]);

                log("Checkpoint B");

                if (blueMal[id] >= 0 && !slots[blueMal[id]].equals(":orange_circle:") && !slots[blueMal[id]].equals(":white_circle:")) {
                    boolean catched = false;
                    for (int j = 0; j < blueMal.length; j++) {
                        if (j != id && blueMal[id] == redMal[j]) {
                            redMal[j] = -1;
                            catched = true;
                        }
                    }

                    log("Checkpoint C");

                    if (catched) {
                        canCountOfThrowYoot++;
                        c.sendMessage("상대 편의 말을 잡았습니다! 윷을 한 번 더 던질 수 있습니다!").queue();
                    }
                }

                if (blueMal[id] >= 0) {
                    slots[blueMal[id]] = getMalEmote(id + 4);
                }

                log("Checkpoint D");
            }

        } else {
            if (redMal[id] != -1) {

                log("Checkpoint A");

                int i = redMal[id];
                moves.remove(Integer.valueOf(value));

                List<Integer> carried = new ArrayList<>();

                for (int j = 0; j < redMal.length; j++) {
                    if (j != id && (redMal[j] >= 0 && redMal[j] == redMal[id])) {
                        carried.add(j);
                    }
                }

                log(carried);

                log("Checkpoint B");

                if (i == 0 || i == 5 || i == 10 || i == 15 || i == 22) {
                    slots[i] = ":orange_circle:";
                } else {
                    slots[i] = ":white_circle:";
                }

                if (i == 5 || i == 10) {
                    redMal[id] += 14;
                } else if (i == 22) {
                    redMal[id] += 4;
                }

                log("Checkpoint D");

                if (i == 10 && value == 3) {
                    redMal[id] += 8;
                } else if (i == 25 && value == 2) {
                    redMal[id] -= 5;
                } else if (i == 26 && value == 1) {
                    redMal[id] -= 4;
                }

                log("Checkpoint E");

                redMal[id] += value;

                if ((i == 10 || (i >= 25 && i < 27)) && redMal[id] > 27) {
                    redMal[id]--;
                }

                if (redMal[id] == 30) {
                    redMal[id] = 0;
                } else if (redMal[id] > 30) {
                    redMal[id] = -2;
                }

                log("Checkpoint G");

                System.out.println("WHERE NOW MOVED MAL IS : " + redMal[id]);

                if (redMal[id] >= 0 && !slots[redMal[id]].equals(":orange_circle:") && !slots[redMal[id]].equals(":white_circle:")) {
                    boolean catched = false;
                    for (int j = 0; j < blueMal.length; j++) {
                        if (redMal[id] == blueMal[j]) {
                            blueMal[j] = -1;
                            catched = true;
                        }
                    }

                    if (catched) {
                        canCountOfThrowYoot++;
                        c.sendMessage("상대 편의 말을 잡았습니다! 윷을 한 번 더 던질 수 있습니다!").queue();
                    }

                    log("Checkpoint H");
                }

                for (Integer integer : carried) {
                    redMal[integer] = redMal[id];
                }

                log("Checkpoint I");

                if (redMal[id] >= 0) {
                    slots[redMal[id]] = getMalEmote(id);
                }

                log("Checkpoint J");
            } else {

                log("Checkpoint A");

                redMal[id] += value + 1;
                moves.remove(Integer.valueOf(value));
                System.out.println("WHERE NOW MOVED MAL IS : " + redMal[id]);

                log("Checkpoint B");

                if (redMal[id] >= 0 && !slots[redMal[id]].equals(":orange_circle:") && !slots[redMal[id]].equals(":white_circle:")) {
                    boolean catched = false;
                    for (int j = 0; j < blueMal.length; j++) {
                        if (redMal[id] == blueMal[j]) {
                            blueMal[j] = -1;
                            catched = true;
                        }
                    }

                    if (catched) {
                        canCountOfThrowYoot++;
                        c.sendMessage("상대 편의 말을 잡았습니다! 윷을 한 번 더 던질 수 있습니다!").queue();
                    }
                }

                if (redMal[id] >= 0) {
                    slots[redMal[id]] = getMalEmote(id);
                }

                log("Checkpoint D");
            }
        }

    }

    public void nextTurn() {
        nowTurn_team = !nowTurn_team;
        nowTurn = nowTurn == turnOrder.size() - 1 ? 0 : nowTurn + 1;

        canCountOfThrowYoot++;
        yoots = new ArrayList<>();
        moves = new ArrayList<>();
    }

    public String getMalEmote(int malNumber) {
        switch (malNumber) {
            case 0:
                return "<:Red1:808981126559432716>";
            case 1:
                return "<:Red2:808981126881607700>";
            case 2:
                return "<:Red3:808981126923288576>";
            case 3:
                return "<:Red4:808981126978338826>";
            case 4:
                return "<:Blue1:808981126865616916>";
            case 5:
                return "<:Blue2:808981126936789012>";
            case 6:
                return "<:Blue3:808981126918963251>";
            case 7:
                return "<:Blue4:808981126957236224>";
            default:
                return null;
        }
    }

    public String getYootName(int move) {
        switch (move) {
            case -1:
                return "뒷도";
            case 0:
                return "낙";
            case 1:
                return "도";
            case 2:
                return "개";
            case 3:
                return "걸";
            case 4:
                return "윷";
            case 5:
                return "모";
            default:
                return "ERR";
        }
    }

    public String getYootEmote(JDA jda) {
        Emote yootBack = jda.getEmoteById("808871533770309652");
        Emote yootFront = jda.getEmoteById("808871533765460009");
        Emote yootFrontSpecial = jda.getEmoteById("808871533539360769");

        StringBuilder yootStr = new StringBuilder();

        if (yootBack == null || yootFront == null || yootFrontSpecial == null) return null;

        for (int i = 0; i < yoots.size(); i++) {
            if (i < 3) {
                if (yoots.get(i) == 0) {
                    yootStr.append(yootBack.getAsMention());
                } else if (yoots.get(i) == 1) {
                    yootStr.append(yootFront.getAsMention());
                }
            } else {
                if (yoots.get(i) == 0) {
                    yootStr.append(yootBack.getAsMention());
                } else {
                    yootStr.append(yootFrontSpecial.getAsMention());
                }
            }
        }

        return yootStr.toString();
    }

    private void log(Object o) {
        System.out.println(o);
    }

}
