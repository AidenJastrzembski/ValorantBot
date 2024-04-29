package com.bannable.valbot;

import com.bannable.valbot.commands.CommandManager;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValorantMatchData { //DONE
    public static List<Match> matches = new ArrayList<Match>();
    public static int timesRan = 0;
    public static String[] playerNameArray;
    public static String playerPuuid;
    public static boolean newMatchFound;


    public static void output(String valorantId) throws IOException, JSONException, InterruptedException {
        matches = new ArrayList<Match>();
            puuidAPICallClass.playerIDAPICall(valorantId);
            if (matchDataClass.matchDateCall()) {
                //System.out.println("APICaller.APICall()");
                APICaller.APICall();
                //System.out.println("Your KDA for your last matches: ");
                //Calculations.KDA(APICaller.returnMatches(valorantId));
                //System.out.println("Your headshot percentage for your last matches: ");
                //Calculations.headshotPercentage(APICaller.returnMatches(valorantId));
                //System.out.println("The dates that these matches are: ");
                matchDataClass.matchDateCollecter();
                //Player.addMatches();
            }
            //System.out.println("Match array: ");
            for (Match m : matches) {
                //System.out.println(m);

            //Timer.Timer(60);
        }

    }
    }