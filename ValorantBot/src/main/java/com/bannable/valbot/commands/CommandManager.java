package com.bannable.valbot.commands;

import com.bannable.valbot.DataTracker;
import com.bannable.valbot.Match;
import com.bannable.valbot.ValorantMatchData;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals("adduser")) {
            DataTracker track = new DataTracker();
            Member member = event.getMember();
            long memberId = member.getUser().getIdLong();
            String valId = event.getOption("valorantid").getAsString();
            boolean override;
            if(event.getOption("override") == null) {
                override = false;
            }
            else {
                override = event.getOption("override").getAsBoolean();
            }

            try {
                if(track.addUser(event.getMember().getUser().getIdLong(), valId, false)) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder
                            .addField("Your Valorant ID has been added!", "Valorant ID: " +
                                    DataTracker.getUser(memberId).getId(), false)

                            .setTitle("Success!")
                            .setThumbnail(member.getEffectiveAvatarUrl());
                    event.replyEmbeds(embedBuilder.build()).queue();
                }
                else if(override) {
                    try {
                        track.addUser(event.getMember().getUser().getIdLong(), valId, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder
                            .addField("Your Valorant ID has been overwritten!", "Valorant ID: " +
                                    DataTracker.getUser(memberId).getId(), false)

                            .setTitle("Success!")
                            .setThumbnail(member.getEffectiveAvatarUrl());
                    event.replyEmbeds(embedBuilder.build()).queue();
                }
                else {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder
                            .addField("You already have a Valorant ID associated with your username", "If you would like to replace " +
                                    DataTracker.getUser(memberId).getId() + " with " + valId + ", please use the override function in the command", false)

                            .setTitle("Warning!")
                            .setThumbnail(member.getEffectiveAvatarUrl());
                    event.replyEmbeds(embedBuilder.build()).queue();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (command.equals("getuser")) {
            DataTracker track = new DataTracker();
            Member member = event.getMember();
            long memberId = member.getUser().getIdLong();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder
                    .addField("Retrieving your Valorant ID...", "Valorant ID: " +
                            DataTracker.getUser(memberId).getId(), false)

                    .setTitle("Working!")
                    .setThumbnail(member.getEffectiveAvatarUrl());
            event.replyEmbeds(embedBuilder.build()).queue();
        }
        else if(command.equals("serialize")) {
            DataTracker dataTracker = new DataTracker();
            try {
                dataTracker.serialize();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            event.reply("Successfully serialized").queue();
        }
        else if(command.equals("testoutput")) {
            OptionMapping valId = event.getOption("valid");
            try {
                System.out.println(valId.getAsString());
                ValorantMatchData.output(valId.getAsString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(command.equals("outputmatcharray")) {
            DataTracker dataTracker = new DataTracker();
            long memberId = event.getMember().getUser().getIdLong();
            DataTracker.getUser(memberId).getMatches();
            event.reply("Outputting match array").queue();
        }
        else if(command.equals("refreshmatches")) {
            event.reply("Refreshing matches").queue();
            long memberId = event.getMember().getUser().getIdLong();
            try {
                DataTracker.getUser(memberId).addMatches();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(command.equals("currentrank")) {
            long memberId = event.getMember().getUser().getIdLong();
            event.reply("Current rank is " + DataTracker.getUser(memberId).returnMatches()[0].getCurrentTierPatched()).queue();
        }
        else if(command.equals("kda")) {
            long memberId = event.getMember().getUser().getIdLong();
            Match current = DataTracker.getUser(memberId).returnMatches()[0];
            event.reply("Most recent KDA is " + current.getKills() + "/" + current.getDeaths() + "/" + current.getAssists()).queue();
        }
        else if(command.equals("headshot")) {
            long memberId = event.getMember().getUser().getIdLong();
            Match current = DataTracker.getUser(memberId).returnMatches()[0];
            event.reply("Most recent headshot percentage is " + String.format("%.2f", 100 * ((double) current.getHeadshots()) / ((double) current.getBodyshots() + current.getLegshots()))).queue();
        }
        else if(command.equals("agent")) {
            long memberId = event.getMember().getUser().getIdLong();
            Match current = DataTracker.getUser(memberId).returnMatches()[0];
            event.reply("Most recent agent " + current.getCharacter()).queue();
        }

    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "welcome command"));

        // Command: /adduser <valorant id> <override>
        OptionData valId = new OptionData(OptionType.STRING, "valorantid", "Your valorant ID", true);
        OptionData override = new OptionData(OptionType.BOOLEAN, "override", "True or false for override", false);
        commandData.add(Commands.slash("adduser", "Add your username to track").addOptions(valId, override));

        // Command: /getuser
        commandData.add(Commands.slash("getuser", "Retrieve the Valorant ID associated to your discord"));

        // Command: /serialize
        commandData.add(Commands.slash("serialize", "Serialize the current player data"));

        // Command: /refreshmatches
        commandData.add(Commands.slash("refreshmatches", "refreshing matches"));

        // Command: /testoutput
        OptionData valorantId = new OptionData(OptionType.STRING, "valid", "valid", true);
        commandData.add(Commands.slash("testoutput", "testing output").addOptions(valorantId));

        // Command: /outputmatcharray
        commandData.add(Commands.slash("outputmatcharray", "outputting match arraY"));

        // Command: /currentRank
        commandData.add(Commands.slash("currentrank", "Outputting current rank"));

        // Command: /kda
        commandData.add(Commands.slash("kda", "Outputting KDA of most recent match"));

        // Command: /headshot
        commandData.add(Commands.slash("headshot", "Outputting headshot percentage of most recent match"));

        // Command: /agent
        commandData.add(Commands.slash("agent", "Outputting agent for most recent matcH"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
