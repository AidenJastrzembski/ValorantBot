package com.bannable.valbot.listeners;

import com.bannable.valbot.Calculations;
import com.bannable.valbot.DataTracker;
import com.bannable.valbot.Match;
import com.bannable.valbot.Serializer;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class EventListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " in the " + channelMention + " channel.";
        event.getChannel().sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) {
            return;
        }
        String message = event.getMessage().getContentStripped();
        Member member = event.getMember();
        String prefix = Dotenv.configure().load().get("PREFIX");
        if(message.substring(0, 1).equals(prefix)) {
            switch(message.substring(1)) {
                case "statistics" -> {
                    Match[] matches = DataTracker.getUser(member.getIdLong()).returnMatches();
                    event.getMessage().delete().queue();
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder
                            .setColor(Color.RED)
                            .setFooter("Valorant Bot", event.getJDA().getSelfUser().getAvatarUrl())
                            .addField("Username", Serializer.in(String.valueOf(event.getAuthor().getIdLong())).getId(), false)
                            .addField("5 Game Average", "KDA " + Calculations.KDA(matches) +
                                    "\n HS % " + String.format("%.2f", Calculations.headshotPercentage(matches)) +
                                    " \n Most Played Agent: " + Calculations.mostPlayedAgent(matches)
                                    , false)
                            .setTitle("Statistics")
                            .setThumbnail(member.getEffectiveAvatarUrl());
                    event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                }
            }
        }
    }
}
