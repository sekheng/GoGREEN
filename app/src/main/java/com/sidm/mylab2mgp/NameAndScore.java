package com.sidm.mylab2mgp;

import java.util.HashMap;

/**
 * Created by - on 19/1/2017.
 */

public class NameAndScore {
    private static NameAndScore donttouchthis = null;
    private HashMap<String, Integer> scores;

    public static NameAndScore getInstance()
    {
        if(donttouchthis == null)
        {
            donttouchthis = new NameAndScore();
        }
        return donttouchthis;
    }

    public NameAndScore()
    {
        scores = new HashMap<String, Integer>();
    }

    public void UpdateHashMap()
    {

    }

    public HashMap<String, Integer> getHashMap()
    {
        return  scores;
    }

}
