package com.sidm.mylab2mgp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


/**
 * Created by lenov on 24/11/2016.
 */

public class Gamepage extends Activity implements OnClickListener {
    //private Button btn_random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        //setContentView(new GamePanelSurfaceView(this));
        zeCurrentGameView = new AdventureView(this);
        setContentView(zeCurrentGameView);
    }

    public void onClick(View v)
    {
//        if(v == btn_random) {
//            Intent intent = new Intent();
//
//            startActivity(intent);
//        }
    }
    public void onClick(String zeEvent)
    {
        Intent intent = new Intent();
        if (zeEvent.equalsIgnoreCase("win!"))
        {
            intent.setClass(this, WinScreen.class);
            finishAfterTransition();
            startActivity(intent);
        }
        else if (zeEvent.equalsIgnoreCase("lose!"))
        {
            intent.setClass(this, LoseScreen.class);
            finishAfterTransition();
            startActivity(intent);
        }
        else if(zeEvent.equalsIgnoreCase("quit"))
        {
            intent.setClass(this, Mainmenu.class);
            finishAfterTransition();
            startActivity(intent);
        }

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

    protected void onStart()    // Executes when app? is starting up
    {
        super.onStart();
    }
    protected void onResume()   // Runs after onStart or after onPause.
    {
        super.onResume();
    }


    protected GamePanelSurfaceView zeCurrentGameView;
}
