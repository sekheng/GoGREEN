package com.sidm.mylab2mgp;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.MotionEvent;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.ImageView;

        import ECS.GraphicsSystem;
        import ECS.GridSystem;
import ECS.MusicSystem;

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

        // This will set up our grids and boxes
        GridSystem.getInstance().setScreenWidthHeight(screenwidth, screenheight);
        // Loading of Graphics
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.outlined_sq), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY, true), "debuggingGrid");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.adventure_background), screenwidth, screenheight, true), "AdventureBackground");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbin), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY, true), "PaperBin");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.small_trash), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY * 2, true), "RottenApple");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.generalbin), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY, true), "GeneralBin");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plasticbin), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY, true), "PlasticBin");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wastepaper), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY, true), "WastePaper");
        GraphicsSystem.getInstance().putImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plasticbottle), (int)GridSystem.getInstance().getAverageBoxSize().scaleX, (int)GridSystem.getInstance().getAverageBoxSize().scaleY, true), "PlasticBottle");

        MusicSystem.getInstance().setCurrentContext(this);
        MusicSystem.getInstance().addBGM(R.raw.adventure_bgm, "Adventure");
        MusicSystem.getInstance().addSoundEffect(R.raw.pick_garbage, "GarbagePicked");
        MusicSystem.getInstance().addSoundEffect(R.raw.remove_trash, "RemoveTrash");

        LevelLoadSystem.setContext(this);
        LevelLoadSystem.getInstance();  // Load the text file first!

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
