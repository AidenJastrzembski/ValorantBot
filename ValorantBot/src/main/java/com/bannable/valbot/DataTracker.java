package com.bannable.valbot;


import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class DataTracker implements Serializable {
    static HashMap<Long, Player> players = new HashMap<>();
    public void serialize() throws IOException, ClassNotFoundException {
        for (HashMap.Entry<Long, Player> me : players.entrySet()) {
            Serializer.out(me.getValue(), String.valueOf(me.getKey()));
        }
        //Serializer.out(players.get(discordId), discordId);
        //players.put(discordId, Serializer.in(discordId));
        //System.out.println(players);
    }

    public void deserialize(Long discordId) {
        //System.out.println(Serializer.in(discordId));
        //players.put(discordId, Serializer.in(discordId));
    }

    public void setPlayers(HashMap<Long, Player> p) throws IOException, ClassNotFoundException {
        players = p;
        //Serializer.out(players);
    }



    public boolean addUser(Long discordId, String valorantId, boolean override) throws IOException {
        if(players == null) {
            players = new HashMap<>();
        }
        if(!players.containsKey(discordId) || override || players.get(discordId) == null) {
            Player player = new Player(valorantId);
            players.put(discordId, player);
            File f = new File("usernames.txt");
            Scanner in = new Scanner(f);
            while(in.hasNextLine()) {
                String next = in.nextLine();
                if(next.contains(discordId.toString())) {
                    return true;
                }
            }
            in.close();
            FileWriter fw = new FileWriter(f, true);
            System.out.println("Writing to file... " + discordId.toString());
            fw.write(discordId + "\n");
            fw.flush();
            fw.close();
            return true;
        }
        else
            return false;

    }

    public static Player getUser(long discordId) {
        if(players == null) {
            players = new HashMap<>();
        }
        //Create if statement to say there is no associated valorant ID if null
        return players.get(discordId);
    }

    /* public void testerOut(String data) {
        Serializer.out(data, "testing");
    } */

    public void testerIn() {
        System.out.println(Serializer.in("testing"));
    }

    public void initializeHashMap() throws FileNotFoundException {
        File f = new File("usernames.txt");
        Scanner in = new Scanner(f);
        while(in.hasNextLine()) {
            String discordId = in.nextLine();
            Player player = Serializer.in(discordId);
            players.put(Long.valueOf(discordId), player);
        }
        in.close();
    }

}
