package com.bannable.valbot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class matchDataClass extends ValorantMatchData {

    static String latestMatch;
    static String matchCheck;

    public static String[] matchDateCollecter() throws IOException, JSONException {
        String textBlock = apiCallForMatchData();
        JSONArray dataArray = new JSONObject(textBlock).getJSONArray("data");
        String[] dates = new String[10];
        long[] dateRaws = new long[dataArray.length()];

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject dataObject = dataArray.getJSONObject(i);
            dates[i] = dataObject.getString("date");
            dateRaws[i] = dataObject.getLong("date_raw");
        }
        for(int j = 0; j < dates.length; j++) {
            System.out.println(dates[j] + " (" + dateRaws[j] + ")");
        }
        return dates;
    }

    public static String apiCallForMatchData() throws IOException {
        String apiUrl = "https://api.henrikdev.xyz/valorant/v1/by-puuid/mmr-history/na/" + playerPuuid;

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        StringBuffer rawMatchData = new StringBuffer();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                rawMatchData.append(inputLine);
            }

        }
        String textBlock = rawMatchData.toString();
        return textBlock;
    }

    public static boolean matchDateCall() throws IOException, JSONException {
        String textBlock = apiCallForMatchData();
        System.out.println(textBlock);
        String delimiter = "match_id";
        String[] blocks = textBlock.split(delimiter);
        textBlock = "{\"" + delimiter + blocks[1];
        JSONObject matchJson = new JSONObject(textBlock);
        matchCheck = matchJson.getString("match_id");
        newMatchFound = timesRan == 0 || !matchCheck.equals(latestMatch);
        return newMatchFound;
    }
}
