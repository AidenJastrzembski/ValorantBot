package com.bannable.valbot;



import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class Player extends APICaller implements Serializable {
    private static final long serialVersionUID = -5723817549691497196l;
    private final String valorantId;
    private double kda;

    public static void setMatches(Match[] matches) {
        Player.matches = matches;
    }

    private static Match[] matches = new Match[100];
    private static int i = 0;
    public Player(String v) {
        valorantId = v;
    }

    public String getId() {
        return valorantId;
    }

    public double getKda() {
        return kda;
    }


    public String calculateKda(int hours) {
        String kda = "";
        int kills = 0;
        int deaths = 0;
        int assists = 0;
        int matchCount = 0;
        for(Match m : matches) {
            if(m.getTime() < ((System.currentTimeMillis() / 1000 / 60) - (hours*60))) {
                kills += m.getKills();
                deaths += m.getDeaths();
                assists += m.getAssists();
                matchCount++;
            }
        }
        return (kills / matchCount) + " / " + (deaths / matchCount) + " / " + (assists / matchCount);
    }

    public static void addMatch(Match match) {
        System.out.println("Player.addMatch called with parameter match ID " + match.getMatch_id());
        if(!check(match)) {
            matches[i] = match;
            System.out.println("Player.addMatch for loop added match with ID " + match.getMatch_id());
            i++;
        }

    }
    public static void addMatches() throws JSONException, IOException, InterruptedException {
        System.out.println("Player.addMatches called");
        ValorantMatchData.output("Jai#Diff");
        ArrayList<Match> matchesList = returnMatches("Jai#Diff");
        System.out.println("Player.addMatches matchlist received: " + matchesList);
        for(Match a : matchesList) {
            if(a==null) {
                return;
            }

            System.out.println("Player.addMatches for loop added match with match id " + a.getMatch_id());
            addMatch(a);
        }
    }
    /*public static void getMatch(Match matthew) {
        System.out.println("ITS WORKING LETS GO" + matthew);
    } */
    public static Match[] returnMatches() {
        return matches;
    }
    public static void getMatches() {
        System.out.println("Player.getMatches called");
        for(Match m: matches) {
            if(m==null) {
                return;
            }
            System.out.println("Player.getMatches outputting match ID " + m.getMatch_id());
        }
    }

    public static boolean check(Match match) {
        for (Match m : matches) {
            if(m != null) {
                if (m.getMatch_id().equals(match.getMatch_id())) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return getId() + "," + getKda();
    }
}
