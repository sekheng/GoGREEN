package com.sidm.mylab2mgp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import ECS.MusicSystem;

import static android.R.attr.checked;

/**
 * Created by 150592K on 12/1/2016.
 */

public class Options extends Activity implements OnClickListener{
    private Switch switch_audio;
    private Button btn_back;

    private boolean isMusicOn;
    private SharedPreferences sharedIsMusicOn;
    private SharedPreferences.Editor sharedIsMusicOnEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // hide title
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //hide top bar
        setContentView(R.layout.options);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back .setOnClickListener(this);

        sharedIsMusicOn = this.getSharedPreferences("isMusicOn", Context.MODE_PRIVATE);
        sharedIsMusicOnEditor = sharedIsMusicOn.edit();
        isMusicOn = sharedIsMusicOn.getBoolean("isMusicOn", true);

        switch_audio = (Switch)findViewById(R.id.switch_audio);
        switch_audio.setChecked(MusicSystem.getInstance().getisMusicOn());//switch default is false
        switch_audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                MusicSystem.getInstance().turnOnOffAllSounds(b);
                sharedIsMusicOnEditor.putBoolean("isMusicOn",b);
                sharedIsMusicOnEditor.commit();

            }
        });
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
