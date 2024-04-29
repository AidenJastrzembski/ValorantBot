package com.bannable.valbot;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class puuidAPICallClass extends ValorantMatchData {

    public static void playerIDAPICall(String playerNameAndTag) throws IOException, JSONException {
        playerNameArray = playerNameAndTag.split("#");

        String apiUrl = "https://api.henrikdev.xyz/valorant/v1/account/" + playerNameArray[0] + "/" +
                playerNameArray[1];

        APICaller.playerInfoParser(yetAnotherAPICall(apiUrl));
    }

    public static StringBuffer yetAnotherAPICall(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        StringBuffer playerInfo = new StringBuffer();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                playerInfo.append(inputLine);
            }

            //System.out.println(response.toString());
            //System.out.print("Done");
        } else {
            System.out.println("HTTP error code: " + responseCode);
        }
        return playerInfo;
    }

}