package com.sidm.mylab2mgp;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.os.Bundle;
        import android.view.MotionEvent;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.ImageView;

/**
 * Created by lenov on 17/11/2016.
 */

public class Splashpage extends Activity {
    private ImageView imageView;

    timer counter = new timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.splashpage);
        imageView = (ImageView)findViewById(R.id.imageView);
        temp = 1;
        //An error that result in crash is somewhere here.
        //thread for displaying the Splash Screen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    timer.startTimer(1000,1000);
                    int waited = 0;
                    temp = 1;
                    while(_active && (waited < _splashTime && temp > 0)) {

                        /*long time = System.nanoTime();
                        int delta_time = (int)((time - last_time) / 1000000);
                        last_time = time;
                        temp = delta_time;*/
                        sleep(0);
                        if(_active) {
//                            waited += (float)(temp);
                            waited = timer.getTimeSeconds();
                            if((waited > _splashTime/2))
                            {
//                                temp -= waited/1000000;
                                temp -= 0.00595f;
                                if(temp < 0) {
                                    temp = 0;
                                }
                                imageView.setAlpha(temp);
                            }
                        }
                    }
                } catch(InterruptedException e) {
                    //do nothing
                } finally {
                    finish();
                    //Create new activity based on and intent with CurrentActivity
                    Intent intent = new Intent(Splashpage.this, Mainmenu.class);
                    startActivity(intent);

                }
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            _active = false;
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
    protected int _splashTime = 10;   //s
    protected float temp = 0.f;
}
