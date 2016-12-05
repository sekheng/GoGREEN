package com.sidm.mylab2mgp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * Created by 150592K on 12/1/2016.
 */

public class LevelSelect extends Activity implements OnClickListener {
    private Button btn_start,btn_next, btn_previous, btn_back;

    private ImageView imageView;

    private ImageSwitcher imageSwitcher;

    private Vibrator vibrator;

    Integer[] images = {R.drawable.mode_adventure, R.drawable.mode_unlimited, R.drawable.mode_quicksort};

    private int imageNumber = 0;

    private Animation in,out,in2,out2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.levelselect);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenheight = displayMetrics.heightPixels;
        int screenwidth = displayMetrics.widthPixels;

        ViewGroup.LayoutParams imageSwitcher_params = imageSwitcher.getLayoutParams();

        imageSwitcher_params.width = screenwidth / 3;
        imageSwitcher_params.height = screenheight /2;

        imageSwitcher.setLayoutParams(imageSwitcher_params);

        in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in);
        out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.out);

        in2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.in2);
        out2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.out2);



        imageSwitcher.setImageResource(images[0]);

        btn_next = (Button)findViewById(R.id.btn_next);
        btn_previous = (Button)findViewById(R.id.btn_previous);

        btn_back = (Button)findViewById((R.id.btn_back));
        btn_back.setOnClickListener(this);

        imageView = (ImageView)findViewById(R.id.title_gamemode);

        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);


//      setting the buttons to fit screen size
        ViewGroup.LayoutParams start_params = btn_start.getLayoutParams();
        ViewGroup.LayoutParams next_params = btn_next.getLayoutParams();
        ViewGroup.LayoutParams previous_params = btn_previous.getLayoutParams();
        ViewGroup.LayoutParams back_params = btn_back.getLayoutParams();
        ViewGroup.LayoutParams imageView_params = imageView.getLayoutParams();

        start_params .width = screenwidth / 4;
        start_params .height = screenheight / 8;

        next_params .width = screenheight / 8;
        next_params .height = screenheight / 8;

        previous_params .width = screenheight / 8;
        previous_params .height = screenheight / 8;

        back_params.width = screenheight / 8;
        back_params.height = screenheight / 8;

        imageView_params .height = screenwidth / 5;

        btn_start.setLayoutParams(start_params);
        btn_next.setLayoutParams(next_params);
        btn_previous.setLayoutParams(previous_params);
        btn_back.setLayoutParams(back_params);
        imageView.setLayoutParams(imageView_params);

        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void onClick(View v)
    {
        Intent intent = new Intent();
        if (v == btn_start)
        {
            vibrator.vibrate(500);//ms
            intent.setClass(this, Gamepage.class);
            startActivity(intent);
            //intent.setClass(this, Splashpage.class);
        }
        if(v == btn_back)
        {
            finish();
        }
        if(v == btn_next)
        {
            imageSwitcher.setInAnimation(in2);
            imageSwitcher.setOutAnimation(out2);
            if(imageNumber < images.length - 1)
            {
                imageNumber++;
                imageSwitcher.setImageResource(images[imageNumber]);
            }
        }
        else if(v == btn_previous)
        {
            imageSwitcher.setInAnimation(in);
            imageSwitcher.setOutAnimation(out);
            if(imageNumber > 0)
            {
                imageNumber--;
                imageSwitcher.setImageResource(images[imageNumber]);
            }
        }
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
