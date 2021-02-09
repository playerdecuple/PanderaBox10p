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

        Message yootInfoMsg;

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
                        ">>> **윷 순서 변경**\n" +
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
                                "예시: `/윷놀이 무운 3`\n\n\n" +
                                "**게임 시작**\n" +
                                "명령어: `/윷놀이 시작`\n\n\n" +
                                "**게임 취소하기**\n" +
                                "명령어: `/윷놀이 취소`\n\n\n"
                        , false);
                eb.setColor(Color.orange);

                yootInfoMsg = c.sendMessage(eb.build()).complete();

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

    boolean eq(String value, String ... others) {
        for (String other : others) {
            if (other.equalsIgnoreCase(value)) return true;
        }

        return false;
    }

}
