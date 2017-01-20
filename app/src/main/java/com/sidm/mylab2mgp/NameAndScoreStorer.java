package com.sidm.mylab2mgp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by - on 19/1/2017.
 */

public class NameAndScoreStorer {
    private static NameAndScoreStorer donttouchthis = null;
    private List<NameAndScore> scores;
    private int numberOfScores;
    //private Map

    public static NameAndScoreStorer getInstance()
    {
        if(donttouchthis == null)
        {
            donttouchthis = new NameAndScoreStorer();
        }
        return donttouchthis;
    }

    public NameAndScoreStorer()
    {
        numberOfScores = 0;
        scores = new ArrayList();
    }

    public void UpdateHashMap()
    {

    }

    public List<NameAndScore> getList()
    {
        return  scores;
    }

    public int getNumberOfScores()
    {
        return numberOfScores;
    }

    public void setNumberOfScores(int i)
    {
        numberOfScores = i;
    }
}
