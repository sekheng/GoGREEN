package com.sidm.mylab2mgp;

import android.app.Activity;
import android.content.Intent;
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
    private Button btn_back;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.rankings);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);





        EditFileForNameScore editFileForNameScore = new EditFileForNameScore(this);
        editFileForNameScore.UpdateListOfNameAndScore(this);

        StringBuilder displyString;
        int maxRanks = 12;
        int numberOfEntries = NameAndScoreStorer.getInstance().getList().size();
        int index = 0;
        NameAndScoreStorer.getInstance().sortListByScore();
        List<NameAndScore> temp =NameAndScoreStorer.getInstance().getList();


        while(index < numberOfEntries && index < maxRanks)
        {
            textview = (TextView)findViewById((R.id.tv_first + index));
            displyString = new StringBuilder(textview.getText());
            displyString.append(" " + temp.get(index).name + " Score:" + temp.get(index).score);
            textview.setText(displyString);
            index += 1;
        }
    }
    public void onClick(View v)
    {
        if(v == btn_back)
        {
            finish();
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
