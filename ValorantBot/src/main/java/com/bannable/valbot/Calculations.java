package com.bannable.valbot;



public class Calculations extends ValorantMatchData{
    public static String KDA (Match[] matchesCalc) {
        int kill = 0;
        int death = 0;
        int assist = 0;
        int matchCount = 0;
        for(Match m : matchesCalc) {
                if(m != null) {
                    kill += m.getKills();
                    death += m.getDeaths();
                    assist += m.getAssists();
                    matchCount++;
                }

        }
        return (kill / matchCount) + " / " + (death / matchCount) + " / " + (assist / matchCount);

    }
    public static double headshotPercentage(Match[] matches) {
        int i = 0;
        int headshot = 0;
        int bodyshot = 0;
        int legshot = 0;
        double headshotPercentage = 0;
        for(Match match : matches) {
            if(match != null) {
                headshot = match.getHeadshots();
                bodyshot = match.getBodyshots();
                legshot = match.getLegshots();
                headshotPercentage = (double) 100 * headshot / (bodyshot + legshot);

                i++;
            }

        }
        return headshotPercentage / i;
    }
    public static String mostPlayedAgent(Match[] matches) {
        int astra = 0;
        int breach = 0;
        int brimstone = 0;
        int cypher = 0;
        int chamber = 0;
        int fade = 0;
        int gekko = 0;
        int harbor = 0;
        int jett = 0;
        int Kayo = 0;
        int killjoy = 0;
        int omen = 0;
        int neon = 0;
        int phoenix = 0;
        int raze = 0;
        int reyna = 0;
        int sage = 0;
        int sova = 0;
        int skye = 0;
        int viper = 0;
        int yoru = 0;
        for(Match m : matches) {
            if(m != null)  {
                switch (m.getCharacter()) {
                    case "Astra" -> astra++;
                    case "Breach" -> breach++;
                    case "Brimstone" -> brimstone++;
                    case "Cypher" -> cypher++;
                    case "Chamber" -> chamber++;
                    case "Fade" -> fade++;
                    case "Gekko" -> gekko++;
                    case "Harbor" -> harbor++;
                    case "Jett" -> jett++;
                    case "KAYO" -> Kayo++;
                    case "Killjoy" -> killjoy++;
                    case "Omen" -> omen++;
                    case "Neon" -> neon++;
                    case "Phoenix" -> phoenix++;
                    case "Raze" -> raze++;
                    case "Reyna" -> reyna++;
                    case "Sage" -> sage++;
                    case "Sova" -> sova++;
                    case "Skye" -> skye++;
                    case "Viper" -> viper++;
                    case "Yoru" -> yoru++;
                }
            }

        }
        int largest = Integer.MIN_VALUE;
        String largestName = "";

        if (astra > largest) {
            largest = astra;
            largestName = "astra";
        }
        if (breach > largest) {
            largest = breach;
            largestName = "breach";
        }
        if (brimstone > largest) {
            largest = brimstone;
            largestName = "brimstone";
        }
        if (cypher > largest) {
            largest = cypher;
            largestName = "cypher";
        }
        if (chamber > largest) {
            largest = chamber;
            largestName = "chamber";
        }
        if (fade > largest) {
            largest = fade;
            largestName = "fade";
        }
        if (gekko > largest) {
            largest = gekko;
            largestName = "gekko";
        }
        if (harbor > largest) {
            largest = harbor;
            largestName = "harbor";
        }
        if (jett > largest) {
            largest = jett;
            largestName = "jett";
        }
        if (Kayo > largest) {
            largest = Kayo;
            largestName = "kayo";
        }
        if (killjoy > largest) {
            largest = killjoy;
            largestName = "killjoy";
        }
        if (omen > largest) {
            largest = omen;
            largestName = "omen";
        }
        if (neon > largest) {
            largest = neon;
            largestName = "neon";
        }
        if (phoenix > largest) {
            largest = phoenix;
            largestName = "phoenix";
        }
        if (raze > largest) {
            largest = raze;
            largestName = "raze";
        }
        if (reyna > largest) {
            largest = reyna;
            largestName = "reyna";
        }
        if (sage > largest) {
            largest = sage;
            largestName = "sage";
        }
        if (sova > largest) {
            largest = sova;
            largestName = "sova";
        }
        if (skye > largest) {
            largest = skye;
            largestName = "skye";
        }
        if (viper > largest) {
            largest = viper;
            largestName = "viper";
        }
        if (yoru > largest) {
            largest = yoru;
            largestName = "yoru";
        }

        return " " + largestName.substring(0, 1).toUpperCase() + largestName.substring(1) + " (" + largest + " times)" ;
    }


}
