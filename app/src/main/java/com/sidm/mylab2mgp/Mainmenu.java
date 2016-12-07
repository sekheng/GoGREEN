package com.sidm.mylab2mgp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

public class Mainmenu extends Activity implements OnClickListener {

    private Button btn_start;
    private Button btn_options;
    private Button btn_ranking;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.mainmenu);

        //InputStream is = getClass().getClassLoader().getResourceAsStream("Laser_Shoot5.wav");

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_options = (Button)findViewById(R.id.btn_options);
        btn_options.setOnClickListener(this);

        btn_ranking = (Button)findViewById(R.id.btn_ranking);
        btn_ranking.setOnClickListener(this);

        imageView = (ImageView)findViewById((R.id.imageView));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenheight = displayMetrics.heightPixels;
        int screenwidth = displayMetrics.widthPixels;
//      setting the buttons to fit screen size
        ViewGroup.LayoutParams start_params = btn_start.getLayoutParams();
        ViewGroup.LayoutParams options_params = btn_options.getLayoutParams();
        ViewGroup.LayoutParams ranking_params = btn_ranking.getLayoutParams();
        ViewGroup.LayoutParams imageView_params = imageView.getLayoutParams();

        start_params .width = screenwidth / 4;
        start_params .height = screenheight / 8;

        options_params .width = screenwidth / 4;
        options_params .height = screenheight / 8;

        ranking_params .width = screenwidth / 4;
        ranking_params .height = screenheight / 8;

        imageView_params .height = screenwidth / 5;

        btn_start.setLayoutParams(start_params);
        btn_options.setLayoutParams(options_params);
        btn_ranking.setLayoutParams(ranking_params);
        imageView.setLayoutParams(imageView_params);



    }

    public void onClick(View v)
    {
        Intent intent = new Intent();
        if (v == btn_start)
        {
            intent.setClass(this, LevelSelect.class);
            //intent.setClass(this, Splashpage.class);
        }
        else if(v == btn_options)
        {
            intent.setClass(this, Options.class);
        }
        else if(v == btn_ranking)
        {
            intent.setClass(this, Rankings.class);
        }
        startActivity(intent);
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
