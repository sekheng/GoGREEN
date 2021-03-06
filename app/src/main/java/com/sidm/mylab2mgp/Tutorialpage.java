package com.sidm.mylab2mgp;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lenov on 24/01/2017.
 * This has to be created because Gamepage doesn't allows me to switch view!
 */

public class Tutorialpage extends Gamepage {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title   // Switching position with super.onCreate() will work!
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        zeCurrentGameView = new TutorialView(this);
        setContentView(zeCurrentGameView);
    }

}
