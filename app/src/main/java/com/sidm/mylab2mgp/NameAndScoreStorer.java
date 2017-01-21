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
    private NameAndScore latestNameAnsScore;
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
        latestNameAnsScore = new NameAndScore();
    }

    public NameAndScore getCurrNameAndSCore()
    {
        return latestNameAnsScore;
    }

    public void setCurrNameAndScore(NameAndScore nameAndScore)
    {
        latestNameAnsScore = nameAndScore;
    }

    public List<NameAndScore> getList()
    {
        return  scores;
    }

    public void clearList()
    {
        scores = new ArrayList();
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
