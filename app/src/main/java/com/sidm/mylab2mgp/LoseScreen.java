package com.sidm.mylab2mgp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by 150592K on 12/1/2016.
 */

public class LoseScreen extends Activity implements OnClickListener{
    private Button btn_mainmenu,btn_tryagain,btn_post;
    private TextView tv_score;
    private Vibrator vibrator;
    private PostToFacebookDialog fbDialog;
    int Playerscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.losescreen);

        btn_mainmenu = (Button)findViewById(R.id.btn_mainmenu);
        btn_mainmenu.setOnClickListener(this);

        btn_tryagain = (Button)findViewById((R.id.btn_tryagain));
        btn_tryagain.setOnClickListener(this);

        Playerscore = NameAndScoreStorer.getInstance().getCurrNameAndSCore().score;

        StringBuilder temp = new StringBuilder("Score: ");
        temp.append(Integer.toString(Playerscore));
        tv_score = (TextView)findViewById(R.id.tv_score);
        tv_score.setText(temp.toString());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenheight = displayMetrics.heightPixels;
        int screenwidth = displayMetrics.widthPixels;

        ViewGroup.LayoutParams mainmenu_params = btn_mainmenu.getLayoutParams();
        ViewGroup.LayoutParams tryagain_params = btn_tryagain.getLayoutParams();

        mainmenu_params .width = screenwidth / 4;
        mainmenu_params .height = screenheight / 8;

        tryagain_params  .width = screenwidth / 4;
        tryagain_params  .height = screenheight / 8;

        btn_mainmenu.setLayoutParams(mainmenu_params);
        btn_tryagain.setLayoutParams(tryagain_params);

        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        Activity tempActivity = (LoseScreen)this;
        fbDialog = new PostToFacebookDialog(this,tempActivity);
        btn_post = (Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);

    }
    public void onClick(View v)
    {
        if(v == btn_mainmenu)
        {
            //go back mainmenu
            Intent intent = new Intent();
            intent.setClass(this, Mainmenu.class);
            startActivity(intent);
            finish();
        }
        else if(v == btn_tryagain)
        {
            //load level again
            vibrator.vibrate(250);
            Intent intent = new Intent();
            intent.setClass(this, Gamepage.class);
            startActivity(intent);
            finish();
        }
        else if(v == btn_post)
        {
            fbDialog.setInDialog(true);

            fbDialog.showDialog();
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
        fbDialog.stopTracking();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        fbDialog.setCallbackManagerOnactivityResult(requestCode,resultCode,data);
    }
}
