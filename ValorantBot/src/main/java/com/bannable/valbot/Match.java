package com.bannable.valbot;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.*;

public class Match extends ValorantMatchData {


    private int kills;
    private int deaths;
    private int assists;
    private int headshots;
    private int bodyshots;
    private int legshots;

    private String puuid;
    private String name;
    private String tag;
    private String team;
    private int level;
    private String character;
    private int currentTier;
    private String currentTierPatched;
    private UUID playerCard;
    private UUID playerTitle;
    private UUID partyId;
    private int damageMade;
    private int damageReceived;
    private final String match_id;

    private final String date;

    public Match(String puuid, String name, String tag, String team, int level, String character,
                 String currentTierPatched, UUID playerCard, UUID playerTitle,
                 UUID partyId, int damageMade, int damageReceived, int kills, int deaths, int assists,
                 int headshots, int bodyshots, int legshots, String date, String match_id) {
        this.puuid = puuid;
        this.name = name;
        this.tag = tag;
        this.team = team;
        this.level = level;
        this.character = character;
        this.currentTier = currentTier;
        this.currentTierPatched = currentTierPatched;
        this.playerCard = playerCard;
        this.playerTitle = playerTitle;
        this.partyId = partyId;
        this.damageMade = damageMade;
        this.damageReceived = damageReceived;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.headshots = headshots;
        this.bodyshots = bodyshots;
        this.legshots = legshots;
        this.date = date;
        this.match_id = match_id;
    }

    // getters and setters

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(int currentTier) {
        this.currentTier = currentTier;
    }

    public String getCurrentTierPatched() {
        return currentTierPatched;
    }

    public void setCurrentTierPatched(String currentTierPatched) {
        this.currentTierPatched = currentTierPatched;
    }

    public UUID getPlayerCard() {
        return playerCard;
    }

    public void setPlayerCard(UUID playerCard) {
        this.playerCard = playerCard;
    }

    public UUID getPlayerTitle() {
        return playerTitle;
    }

    public void setPlayerTitle(UUID playerTitle) {
        this.playerTitle = playerTitle;
    }

    public UUID getPartyId() {
        return partyId;
    }

    public void setPartyId(UUID partyId) {
        this.partyId = partyId;
    }

    public int getDamageMade() {
        return damageMade;
    }

    public void setDamageMade(int damageMade) {
        this.damageMade = damageMade;
    }

    public int getDamageReceived() {
        return damageReceived;
    }

    public void setDamageReceived(int damageReceived) {
        this.damageReceived = damageReceived;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;

    }

    public int getAssists() {
        return assists;
    }

    public void setAssits(int assists) {
        this.assists = assists;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public int getBodyshots() {
        return bodyshots;
    }

    public void setBodyshots(int bodyshots) {
        this.bodyshots = bodyshots;
    }

    public int getLegshots() {
        return legshots;
    }

    public void setLegshots(int legshots) {
        this.legshots = legshots;
    }

    public String getDate() {
        return date;
    }

    public String getMatch_id() {
        return match_id;
    }

    public int getTime() {
        return timesRan; //this should work right? because in Player.java it looks like it just wants an int value
        //for how many times it has passed an hour.
    }

}