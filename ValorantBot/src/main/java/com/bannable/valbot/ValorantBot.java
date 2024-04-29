package com.bannable.valbot;


import com.bannable.valbot.commands.CommandManager;
import com.bannable.valbot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class ValorantBot {

    private final Dotenv config;
    private final ShardManager shardManager;

    public ValorantBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        shardManager = builder.build();

        shardManager.addEventListener(new EventListener(), new CommandManager());
    }
    public Dotenv getConfig() {
        return config;
    }
    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataTracker dataTracker = new DataTracker();
        dataTracker.initializeHashMap();
        try {
            ValorantBot bot = new ValorantBot();
        } catch (LoginException e) {
            System.out.println("Bot token is invalid");
        }
    }
}
