package com.devkor_decuple.PanderaBox.Listeners;

import com.devkor_decuple.PanderaBox.Core.Games.Yootnori;
import com.devkor_decuple.PanderaBox.PanderaBox;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YootnoriListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent ev) {

        User sender = ev.getAuthor();
        String msg = ev.getMessage().getContentRaw();
        TextChannel c = ev.getChannel();
        String[] args = msg.split(" ");

        Message yootInfoMsg = null;

        if (PanderaBox.manager1.yootnoriGameExists(ev.getChannel().getId())) {
            Yootnori yoot = PanderaBox.manager1.findYootnoriGame(c.getId());
            yoot.c = c;

            if (yoot.nowPlaying) {

                boolean isOurTurn = yoot.nowTurn_team ? yoot.blueTeam.contains(sender) : yoot.redTeam.contains(sender);

                if (yoot.getUserList().contains(sender)) {
                    if (eq(args[0], "/ㅈㅈ", "/항복", "/서렌", "/ㅅㄹ", "15ㄱㄱ", "/기권")) {
                        if (args.length == 1) {
                            yoot.surrender(sender);
                        }
                    }

                    if (eq(args[0], "/윷")) {
                        if (args.length > 1) {
                            if (eq(args[1], "던지기")) {

                                int nowTurn = yoot.nowTurn;
                                boolean canThrowYoot = yoot.canCountOfThrowYoot > 0;

                                if (yoot.turnOrder.get(nowTurn).getId().equals(sender.getId())) {
                                    if (!canThrowYoot) {
                                        c.sendMessage("던질 기회가 없습니다. `/윷 판` 명령어를 입력하고 해당 내용을 참고하여 말을 움직여 주세요!").queue();
                                        return;
                                    }

                                    int move = yoot.throwYoot();
                                    c.sendMessage(yoot.getYootEmote(ev.getJDA()).equals("") ? "\u200B" : yoot.getYootEmote(ev.getJDA())).queue();
                                    c.sendMessage("윷을 던졌더니, **" + yoot.getYootName(move) + "**(이)가 나왔습니다!").queue();
                                    c.sendMessage(move != 0 ? yoot.canCountOfThrowYoot + "번 더 던질 수 있습니다." : "**낙**이므로 던질 수 없습니다. " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례가 되었습니다.").queue();
                                } else {
                                    c.sendMessage("당신은 지금 윷을 던질 수 없습니다! `/윷 판` 명령어를 입력하고 해당 내용을 참고하여 말을 움직여 주세요!").queue();
                                }

                            }
                        }

                        if (eq(args[1], "판")) {

                            c.sendMessage(yoot.showMalBoard()).queue();
                            c.sendMessage("'**뒷도**/**도**/**개**/**걸**/**윷**/**모** [말 번호]' 명령어로 말을 움직일 수 있습니다.").queue();

                            StringBuilder sb = new StringBuilder();

                            for (int i = 0; i < yoot.moves.size(); i++) {
                                sb.append(yoot.getYootName(yoot.moves.get(i))).append(i != yoot.moves.size() - 1 ? " - " : "");
                            }

                            c.sendMessage( "\n\n\n" +
                                    "현재 **청편** 업혀있는 말 묶음 : " + yoot.getCarryingTarget(true) + "\n" +
                                    "현재 **홍편** 업혀있는 말 묶음 : " + yoot.getCarryingTarget(false)).queue();
                            c.sendMessage("\n\n윷의 현재 결과 : [**" + sb.toString() + "**]").queue();

                        }
                    }

                    if (eq(args[0], "도")) {

                        if (args.length == 2) {

                            if (isOurTurn) {
                                if (yoot.canChangeOrderOfYoot ? yoot.moves.contains(1) : yoot.moves.get(0) == 1) {
                                    if (yoot.nowTurn_team ? yoot.blueMal[Integer.parseInt(args[1]) - 1] != -2 : yoot.redMal[Integer.parseInt(args[1]) - 1] != -2) {
                                        yoot.move(1, Integer.parseInt(args[1]));
                                        c.sendMessage(yoot.showMalBoard()).queue();

                                        if (yoot.canCountOfThrowYoot <= 0 && yoot.moves.size() <= 0) {
                                            yoot.nextTurn();
                                            c.sendMessage("이제 " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례입니다!").queue();
                                        }
                                    } else {
                                        EmbedBuilder eb = new EmbedBuilder();
                                        eb.setTitle("해당 말은 이미 도착했기 때문에 움직일 수 없습니다!");
                                        eb.setColor(Color.yellow);

                                        c.sendMessage(eb.build()).queue();
                                    }
                                }
                            }

                        }

                    }

                    if (eq(args[0], "개")) {

                        if (args.length == 2) {

                            if (isOurTurn) {
                                if (yoot.canChangeOrderOfYoot ? yoot.moves.contains(2) : yoot.moves.get(0) == 2) {
                                    if (yoot.nowTurn_team ? yoot.blueMal[Integer.parseInt(args[1]) - 1] != -2 : yoot.redMal[Integer.parseInt(args[1]) - 1] != -2) {
                                        yoot.move(2, Integer.parseInt(args[1]));
                                        c.sendMessage(yoot.showMalBoard()).queue();

                                        if (yoot.canCountOfThrowYoot <= 0 && yoot.moves.size() <= 0) {
                                            yoot.nextTurn();
                                            c.sendMessage("이제 " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례입니다!").queue();
                                        }
                                    } else {
                                        EmbedBuilder eb = new EmbedBuilder();
                                        eb.setTitle("해당 말은 이미 도착했기 때문에 움직일 수 없습니다!");
                                        eb.setColor(Color.yellow);

                                        c.sendMessage(eb.build()).queue();
                                    }
                                }
                            }

                        }

                    }

                    if (eq(args[0], "걸")) {

                        if (args.length == 2) {

                            if (isOurTurn) {
                                if (yoot.canChangeOrderOfYoot ? yoot.moves.contains(3) : yoot.moves.get(0) == 3) {
                                    if (yoot.nowTurn_team ? yoot.blueMal[Integer.parseInt(args[1]) - 1] != -2 : yoot.redMal[Integer.parseInt(args[1]) - 1] != -2) {
                                        yoot.move(3, Integer.parseInt(args[1]));
                                        c.sendMessage(yoot.showMalBoard()).queue();

                                        if (yoot.canCountOfThrowYoot <= 0 && yoot.moves.size() <= 0) {
                                            yoot.nextTurn();
                                            c.sendMessage("이제 " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례입니다!").queue();
                                        }
                                    } else {
                                        EmbedBuilder eb = new EmbedBuilder();
                                        eb.setTitle("해당 말은 이미 도착했기 때문에 움직일 수 없습니다!");
                                        eb.setColor(Color.yellow);

                                        c.sendMessage(eb.build()).queue();
                                    }
                                }
                            }

                        }

                    }

                    if (eq(args[0], "윷")) {

                        if (args.length == 2) {

                            if (isOurTurn) {
                                if (yoot.canChangeOrderOfYoot ? yoot.moves.contains(4) : yoot.moves.get(0) == 4) {
                                    if (yoot.nowTurn_team ? yoot.blueMal[Integer.parseInt(args[1]) - 1] != -2 : yoot.redMal[Integer.parseInt(args[1]) - 1] != -2) {
                                        yoot.move(4, Integer.parseInt(args[1]));
                                        c.sendMessage(yoot.showMalBoard()).queue();

                                        if (yoot.canCountOfThrowYoot <= 0 && yoot.moves.size() <= 0) {
                                            yoot.nextTurn();
                                            c.sendMessage("이제 " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례입니다!").queue();
                                        }
                                    } else {
                                        EmbedBuilder eb = new EmbedBuilder();
                                        eb.setTitle("해당 말은 이미 도착했기 때문에 움직일 수 없습니다!");
                                        eb.setColor(Color.yellow);

                                        c.sendMessage(eb.build()).queue();
                                    }
                                }
                            }

                        }

                    }

                    if (eq(args[0], "모")) {

                        if (args.length == 2) {

                            if (isOurTurn) {
                                if (yoot.canChangeOrderOfYoot ? yoot.moves.contains(5) : yoot.moves.get(0) == 5) {
                                    if (yoot.nowTurn_team ? yoot.blueMal[Integer.parseInt(args[1]) - 1] != -2 : yoot.redMal[Integer.parseInt(args[1]) - 1] != -2) {
                                        yoot.move(5, Integer.parseInt(args[1]));
                                        c.sendMessage(yoot.showMalBoard()).queue();

                                        if (yoot.canCountOfThrowYoot <= 0 && yoot.moves.size() <= 0) {
                                            yoot.nextTurn();
                                            c.sendMessage("이제 " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례입니다!").queue();
                                        }
                                    } else {
                                        EmbedBuilder eb = new EmbedBuilder();
                                        eb.setTitle("해당 말은 이미 도착했기 때문에 움직일 수 없습니다!");
                                        eb.setColor(Color.yellow);

                                        c.sendMessage(eb.build()).queue();
                                    }
                                }
                            }

                        }

                    }

                    if (eq(args[0], "뒷도")) {

                        if (args.length == 2) {

                            if (isOurTurn) {
                                if (yoot.canChangeOrderOfYoot ? yoot.moves.contains(-1) : yoot.moves.get(0) == -1) {
                                    if (yoot.nowTurn_team ? yoot.blueMal[Integer.parseInt(args[1]) - 1] != -2 : yoot.redMal[Integer.parseInt(args[1]) - 1] != -2) {
                                        yoot.move(-1, Integer.parseInt(args[1]));
                                        c.sendMessage(yoot.showMalBoard()).queue();

                                        if (yoot.canCountOfThrowYoot <= 0 && yoot.moves.size() <= 0) {
                                            yoot.nextTurn();
                                            c.sendMessage("이제 " + yoot.turnOrder.get(yoot.nowTurn).getAsMention() + "님이 던질 차례입니다!").queue();
                                        }
                                    } else {
                                        EmbedBuilder eb = new EmbedBuilder();
                                        eb.setTitle("해당 말은 이미 도착했기 때문에 움직일 수 없습니다!");
                                        eb.setColor(Color.yellow);

                                        c.sendMessage(eb.build()).queue();
                                    }
                                }
                            }

                        }

                    }

                    return;

                }
            }
        }

        if (eq(args[0], "/이모티콘")) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < ev.getMessage().getEmotes().size(); i++) {
                sb.append(ev.getMessage().getEmotes().get(i).getId()).append(" ");
            }

            c.sendMessage(sb.toString()).queue();
        }

        if (eq(args[0], "/윷놀이", "/윷")) {

            if (args.length == 1) {

                if (PanderaBox.manager1.yootnoriGameExists(ev.getChannel().getId())) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("윷놀이 게임이 이미 있어요.");
                    eb.setDescription("`/윷놀이 참가` 명령어로 윷놀이 게임에 참가해 주세요.");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                Yootnori yoot = PanderaBox.manager1.findYootnoriGame(c.getId());
                yoot.addUser(sender);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("윷놀이 게임을 생성했습니다!");
                eb.addField("게임 설명", "팀의 말을 한 바퀴를 돌아 도착하도록 만드는 게임입니다. 4개 말을 모두 도착시킨 쪽이 승리합니다.", false);
                eb.addField("명령어",
                        ">>> " + /* **윷 순서 변경**\n" +
                                "다시 한번 더 던질 수 있는 '윷'이나 '모'가 나왔을 때, 말을 두는 순서를 변경할 수 있습니다.\n" +
                                "예를 들어, '모-윷-도' 순으로 나왔을 경우 '도-윷-모' 등으로 변경할 수 있습니다.\n\n" +
                                "명령어: `/윷놀이 순서변경 <ON | OFF>`\n" +
                                "예시: `/윷놀이 순서변경 OFF`\n\n\n" +
                                "**함정 난이도**\n" +
                                "함정 칸은 0 ~ 2중에서 고를 수 있습니다. 0이면 함정이 없고, 1이면 함정 칸을 밟았을 때 한 턴을 쉬며, 2면 함정 칸을 밟았을 때 처음으로 돌아갑니다.\n\n" +
                                "명령어: `/윷놀이 함정 <숫자>`\n" +
                                "예시: `/윷놀이 함정 1`\n\n\n" +
                                "**자유걸 허용**\n" +
                                "뒷도 윷을 제외한 나머지 윷이 앞면이면 앞으로 3칸 또는 뒤로 3칸을 선택하여 이동할 수 있습니다.\n\n" +
                                "명령어: `/윷놀이 자유걸 <ON | OFF>`\n" +
                                "예시: `/윷놀이 자유걸 ON`\n\n\n" +
                                "**출발 전 뒷도 허용**\n" +
                                "출발 전 뒷도가 나올 경우 도착점 바로 전 위치로 이동합니다.\n\n" +
                                "명령어: `/윷놀이 시작뒷도 <ON | OFF>`\n" +
                                "예시: `/윷놀이 시작뒷도 OFF`\n\n\n" +
                                "**엄격한 도착**\n" +
                                "도착점을 넘었다면 한 바퀴 더 돌아야 합니다. 정확히 도착점에 말을 둬야 도착으로 인정됩니다.\n\n" +
                                "명령어: `/윷놀이 엄격도착 <ON | OFF>`\n" +
                                "예시: `/윷놀이 엄격도착 ON`\n\n\n" +
                                "**무운 효과**\n" +
                                "말이 연속으로 잡힐 경우 다음 턴에 해당 팀이 던진 윷은 무조건 '모'가 나옵니다. 1에서 4 사이로 설정해야 하며, 5로 설정하면 비활성화됩니다.\n\n" +
                                "명령어: `/윷놀이 무운 <숫자>`\n" +
                                "예시: `/윷놀이 무운 3`\n\n\n" + */
                                "**게임 시작**\n" +
                                "명령어: `/윷놀이 시작`\n\n\n" +
                                "**게임 취소하기**\n" +
                                "명령어: `/윷놀이 취소`\n\n\n"
                        , false);
                eb.setColor(Color.orange);

                yootInfoMsg = c.sendMessage(eb.build()).complete();

            } else if (eq(args[1], "시작")) {

                if (!PanderaBox.manager1.yootnoriGameExists(ev.getChannel().getId())) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("윷놀이 게임이 없네요..");
                    eb.setDescription("`/윷놀이` 명령어로 윷놀이 게임을 생성해 주세요!");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                if (yootInfoMsg != null) yootInfoMsg.delete().queue();

                Yootnori yoot = PanderaBox.manager1.findYootnoriGame(c.getId());
                yoot.gameStart(c);

            } else if (eq(args[1], "참가")) {

                if (!PanderaBox.manager1.yootnoriGameExists(ev.getChannel().getId())) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("윷놀이 게임이 없네요..");
                    eb.setDescription("`/윷놀이` 명령어로 윷놀이 게임을 생성해 주세요!");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                Yootnori yoot = PanderaBox.manager1.findYootnoriGame(c.getId());

                if (yoot.getUserList().contains(sender)) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("이미 게임에 참가하셨네요.");
                    eb.setDescription("게임 참가를 취소하시려면 `/윷놀이 취소` 명령어로 취소해 주세요.");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                yoot.addUser(sender);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("윷놀이 게임에 참가하셨습니다!");
                eb.setColor(Color.green);

                c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

            } else if (eq(args[1], "취소")) {

                if (!PanderaBox.manager1.yootnoriGameExists(ev.getChannel().getId())) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("윷놀이 게임이 없네요..");
                    eb.setDescription("`/윷놀이` 명령어로 윷놀이 게임을 생성해 주세요!");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                Yootnori yoot = PanderaBox.manager1.findYootnoriGame(c.getId());

                if (!yoot.getUserList().contains(sender)) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("게임에 참가하지 않으셨네요.");
                    eb.setDescription("게임에 참가하시려면 `/윷놀이 참가` 명령어로 참가해 주세요!");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                if (yoot.getGameStarter().getId().equals(sender.getId())) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("게임을 종료했습니다.");
                    eb.setDescription("게임을 다시 생성하시려면 `/윷놀이` 명령어를 이용해 주세요!");
                    eb.setColor(Color.yellow);

                    PanderaBox.manager1.removeYootnoriGame(c.getId());

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("게임에서 나왔습니다.");
                eb.setDescription("게임에 다시 참가하시려면 `/윷놀이 참가` 명령어를 이용해 주세요!");
                eb.setColor(Color.yellow);

                c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                yoot.removeUser(sender);

            } else if (eq(args[1], "멤버")) {

                if (!PanderaBox.manager1.yootnoriGameExists(ev.getChannel().getId())) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("윷놀이 게임이 없네요..");
                    eb.setDescription("`/윷놀이` 명령어로 윷놀이 게임을 생성해 주세요!");
                    eb.setColor(Color.red);

                    c.sendMessage(eb.build()).delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();

                    return;
                }

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("현재 윷놀이 게임에 참가한 멤버");

                List<User> playerList = PanderaBox.manager1.findYootnoriGame(c.getId()).getUserList();
                StringBuilder builder = new StringBuilder();

                for (User player : playerList) {
                    builder.append(player.getAsMention()).append("\n");
                }

                eb.setDescription(builder.toString());
                c.sendMessage(eb.build()).delay(30, TimeUnit.SECONDS).flatMap(Message::delete).queue();

            }

        }

    }

    boolean eq(String value, String... others) {
        for (String other : others) {
            if (other.equalsIgnoreCase(value)) return true;
        }

        return false;
    }

}
