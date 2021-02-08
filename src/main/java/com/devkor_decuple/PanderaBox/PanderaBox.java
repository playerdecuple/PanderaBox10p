package com.devkor_decuple.PanderaBox;

import com.devkor_decuple.PanderaBox.Core.Games.YootnoriManager;
import com.devkor_decuple.PanderaBox.Core.ReadFile;
import com.devkor_decuple.PanderaBox.Listeners.YootnoriListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class PanderaBox {

    public static YootnoriManager manager1 = new YootnoriManager();

    public static void main(String[] args) throws LoginException, InterruptedException {

        JDABuilder.createDefault(ReadFile.readString(System.getProperty("user.dir") + "/config.txt"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_EMOJIS)
                .setAutoReconnect(true)
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new YootnoriListener())
                .build()
                .awaitReady();

    }

}
