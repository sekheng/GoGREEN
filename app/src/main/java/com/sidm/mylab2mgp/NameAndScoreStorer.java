package com.sidm.mylab2mgp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by - on 19/1/2017.
 */

public class NameAndScoreStorer extends Gamepage{
    private static NameAndScoreStorer donttouchthis = null;
    private List<NameAndScore> scores;
    private NameAndScore latestNameAnsScore;
    private int numberOfScores;
    //public Context app = ;
    //private Map

    public static NameAndScoreStorer getInstance()//singleton
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

    public void setCurrNameAndScore(NameAndScore nameAndScore)//used to store the name and score of the player right after a game
    {
        latestNameAnsScore = nameAndScore;
    }

    public List<NameAndScore> getList()//lixt of NameAndScore objects
    {
        return  scores;
    }

    public void clearList()
    {
        scores = new ArrayList();
    }

    //public int getNumberOfScores()
    /*{
        //return numberOfScores;
    }*/

    public void sortListByScore()
    {
        Collections.sort(scores, new Comparator<NameAndScore>() {
            @Override
            public int compare(NameAndScore nameAndScore, NameAndScore t1) {// sort the list based on the score and in decending order.
                return t1.score - nameAndScore.score;
            }
        });
    }

    public void setNumberOfScores(int i)
    {
        numberOfScores = i;
    }
}
