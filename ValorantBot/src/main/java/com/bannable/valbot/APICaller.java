package com.bannable.valbot;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class APICaller extends ValorantMatchData {

    public static void APICall() throws IOException, JSONException {
        String apiUrl = "https://api.henrikdev.xyz/valorant/v3/matches/na/" + playerNameArray[0] + "/" +
                playerNameArray[1];


        matchParser();
    }

    public static void playerInfoParser(StringBuffer playerInfo) throws JSONException, IOException{
        String textBlock = playerInfo.toString();
        //System.out.println(textBlock);
        String delimiter = "puuid";
        String[] blocks = textBlock.split(delimiter);
        textBlock = "{\"" + delimiter + blocks[1];
        //System.out.println(textBlock);
        JSONObject puuidJson = new JSONObject(textBlock);
        playerPuuid = puuidJson.getString("puuid");

    }

    public static void matchParser() throws JSONException, IOException {
        String apiUrl = "https://api.henrikdev.xyz/valorant/v3/matches/na/" + playerNameArray[0] + "/" +
                playerNameArray[1];
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        StringBuffer response = new StringBuffer();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            //System.out.println(response.toString());
            //System.out.print("Done");
        } else {
            System.out.println("HTTP error code: " + responseCode);
        }
        String textBlock = response.toString();
        String delimiter = "puuid";
        String[] blocks = textBlock.split(delimiter);
        String jsonText = matchDataClass.apiCallForMatchData();
        String[] dateArray = matchDataClass.matchDateCollecter();

        JSONObject jsonMatch = new JSONObject(jsonText);
        JSONArray data = jsonMatch.getJSONArray("data");
        String[] matchID = new String[20];
        for (int i = 0; i < data.length(); i++) {
            JSONObject match = data.getJSONObject(i);
            String matchId = match.getString("match_id");
            matchID[i] = matchId;
            //System.out.println("Match ID: " + matchId);
        }
        int k = 0;
        for (String block : blocks) {
            if (block.startsWith("\":\"") && block.contains("level")) {
                // this is the block we're interested in, so let's parse it
                block = "{\"" + delimiter + block; // add the delimiter back in
                JSONObject json = new JSONObject(block);
                String puuid = json.getString("puuid");
                String name = json.getString("name");
                String tag = json.getString("tag");
                String team = json.getString("team"); //sorts either blue or red
                int level = json.getInt("level"); //account level
                String character = json.getString("character");
                //it would be easy to throw a picture of their agent into each response
                String currentTierPatched = json.getString("currenttier_patched"); //current rank
                //this is if we wanna throw an image of their player card into the discord response
                UUID playerCard = UUID.fromString(json.getString("player_card"));
                UUID playerTitle = UUID.fromString(json.getString("player_title"));
                UUID partyId = UUID.fromString(json.getString("party_id"));
                int damageMade = json.getInt("damage_made");  //for the damage delta
                int damageReceived = json.getInt("damage_received");

                int kills = json.getJSONObject("stats").getInt("kills");
                int deaths = json.getJSONObject("stats").getInt("deaths");
                int assists = json.getJSONObject("stats").getInt("assists");
                int headshots = json.getJSONObject("stats").getInt("headshots");
                int bodyshots = json.getJSONObject("stats").getInt("bodyshots");
                int legshots = json.getJSONObject("stats").getInt("legshots");

                if (puuid.equals(playerPuuid)) {
                    if (k % 2 == 0) {
                        String date = dateArray[k];
                        String match_id = matchID[k];
                        Match match = new Match(puuid, name, tag, team, level, character
                                , currentTierPatched, playerCard, playerTitle, partyId
                                , damageMade, damageReceived, kills, deaths, assists, headshots, bodyshots, legshots, date, match_id);
                        matches.add(match);
                    }
                    k++;
                }

            }
        }

    }
    public static ArrayList<Match> returnMatches(String username) {
        ValorantMatchData val = new ValorantMatchData();
        ArrayList<Match> matchList = new ArrayList<>(5);
        int i = 0;
        for (Match match : matches) {
            System.out.println("Outputting each match object in matches");
            System.out.println(match);
            String[] usernameArray = username.split("#");
            username = usernameArray[0];

            if (username.equals(match.getName())) {
                matchList.add(match);
                i++;
                System.out.println(matchList);
                if(i == 5) {
                    break;
                }
            }
        }
        System.out.println("Returning the matchList" + matchList);
        return matchList;
    }
    }
