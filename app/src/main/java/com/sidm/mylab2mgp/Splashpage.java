package com.sidm.mylab2mgp;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.MotionEvent;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.ImageView;

        import ECS.GridSystem;

/**
 * Created by lenov on 17/11/2016.
 */

public class Splashpage extends Activity {
    private ImageView imageView;

    private int waited = 0;
    timer counter = new timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.splashpage);
        imageView = (ImageView)findViewById(R.id.imageView);
        temp = 1;//alpha for the logo

        //get the screen size for resizing the logo
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenheight = displayMetrics.heightPixels;
        int screenwidth = displayMetrics.widthPixels;

        GridSystem.getInstance().setScreenWidthHeight(screenwidth, screenheight);

        ViewGroup.LayoutParams imageView_params = imageView.getLayoutParams();

        imageView_params .height = screenwidth / 5;

        imageView.setLayoutParams(imageView_params);



        Thread splashTread = new Thread() {
            @Override
            public void run() {
                    timer.startTimer(1000,1000);

                    temp = 1;
                    while(_active && (waited < _splashTime && temp > 0)) {
                        if(_active) {
//                            waited += (float)(temp);
                            waited = timer.getTimeSeconds();
                            float timeTotriggerFade = _splashTime/4;
                            if(waited > timeTotriggerFade)
                            {
                                temp -= 0.000008f;
                                if(temp < 0)
                                {
                                    temp = 0;
                                }
                                if(temp >= 0) {
                                    imageView.setAlpha(temp);
                                }
                            }
                        }
                    }
                    finish();
                    //Create new activity based on and intent with CurrentActivity
                    Intent intent = new Intent(Splashpage.this, Mainmenu.class);
                    startActivity(intent);
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
           // _active = false;
            waited = _splashTime/4 + 1;//press to trigger fading
        }
        return true;
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
    protected boolean _active = true;
    protected int _splashTime = 8;   //s
    protected float temp = 0.f;
}
