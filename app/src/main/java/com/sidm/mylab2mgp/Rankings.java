package com.sidm.mylab2mgp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 150592K on 12/1/2016.
 */

public class Rankings extends Activity implements OnClickListener{
    private Button btn_back, btn_reset;
    private TextView textview;

    private EditFileForNameScore editFileForNameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.rankings);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_reset = (Button)findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);



        editFileForNameScore = new EditFileForNameScore(this);
        editFileForNameScore.UpdateListOfNameAndScore(this);

        StringBuilder displyString;
        int maxRanks = 12;
        int numberOfEntries = NameAndScoreStorer.getInstance().getList().size();
        int index = 0;
        NameAndScoreStorer.getInstance().sortListByScore();
        List<NameAndScore> temp =NameAndScoreStorer.getInstance().getList();

        int currRank = 1;
        int prevRank = 1;

        while(index < numberOfEntries && index < maxRanks)
        {
            textview = (TextView)findViewById((R.id.tv_first + index));
            if(index != 0)
            {
                if(temp.get(index).score == temp.get(index - 1).score)//if equal score should have same rank
                {
                    displyString = new StringBuilder("Number " + prevRank);
                    currRank += 1;
                }
                else
                {
                    displyString = new StringBuilder("Number " + currRank);
                    prevRank = currRank;
                    currRank += 1;
                }
            }
            else
            {
                displyString = new StringBuilder("Number " + currRank);
                currRank += 1;
            }
            displyString.append(": " + temp.get(index).name + " Score:" + temp.get(index).score);
            textview.setText(displyString);
            index += 1;
        }
        if(numberOfEntries <= 0) {
            textview = (TextView) findViewById((R.id.tv_first));
            textview.setText("PLAY TO SEE YOUR SCORE!!");
        }
    }
    public void onClick(View v)
    {
        if(v == btn_back)
        {
            finish();
        }
        else if(v == btn_reset)
        {
            resettheRanks();
        }
        //Intent intent = new Intent();
        /*if (v == btn_start)
        {
            intent.setClass(this, Gamepage.class);
            //intent.setClass(this, Splashpage.class);
        }
        else if(v == btn_options)
        {
            //intent.setClass(this, Splashpage.class);
        }
        else if(v == btn_ranking)
        {
            //intent.setClass(this, Splashpage.class);
        }*/
        //startActivity(intent);
    }

    void resettheRanks()
    {
        editFileForNameScore.refreshText(this);
        NameAndScoreStorer.getInstance().clearList();
        int index = 0;
        int maxRanks = 12;
        while(index < maxRanks) {
            textview = (TextView) findViewById((R.id.tv_first + index));
            textview.setText("");
            index += 1;
        }
        textview = (TextView) findViewById((R.id.tv_first));
        textview.setText("PLAY TO SEE YOUR SCORE!!");

    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
