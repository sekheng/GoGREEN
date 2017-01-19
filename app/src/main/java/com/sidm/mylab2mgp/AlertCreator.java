package com.sidm.mylab2mgp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by - on 19/1/2017.
 */

public class AlertCreator {
    public boolean showAlert = false;
    AlertDialog.Builder alert = null;
    //week13 shared preference
    /*SharedPreferences sharedPrefname;
    SharedPreferences.Editor editName;*/
    String Playername;

    SharedPreferences sharedPreferscore;
    SharedPreferences.Editor editScore;
    float Playerscore;
    //public Alert alert;

    public AlertCreator(Context context) {

        /*sharedPrefname = context.getSharedPreferences("PlayerUSERID",Context.MODE_PRIVATE);
        editName = sharedPrefname.edit();
        Playername = "Player1";
        Playername = sharedPrefname.getString("PlayerUSERID", "DEFAULT");*/

        sharedPreferscore = context.getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        editScore = sharedPreferscore.edit();
        Playerscore = 0;
        Playerscore = sharedPreferscore.getFloat("UserScore", 0);
        alert = new AlertDialog.Builder(context);

        //allow the players to input their name
        final EditText input = new EditText(context);

        //define the input method where ' enter' key is disabled
        input.setInputType(InputType.TYPE_CLASS_TEXT);


        //define max of 20 character to be entered for name field
        int maxlength = 20;
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxlength);
        input.setFilters(FilterArray);

        //setup the alert dialog
        //set up the alert dialog
        alert.setMessage("game over?!");
        alert.setCancelable(false);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Playername = input.getText().toString();
                /*editName.putString("PlayerUSERID", Playername);
                editName.commit();*/

                /*Intent intent = new Intent();
                intent.setClass(getContext(), ScorePage.class);
                activityTracker.startActivity(intent);*/
            }
        });
    }

    public void RunAlert(int milisec) {
        Handler handler = new Handler(Looper.getMainLooper());
        // Returns the application's main looper, which lives in the main thread of the application

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                alert.show();
            }
        }, milisec); // 1000  Delay in milliseconds until the runnable is executed
    }
}
/*public class Alert {
    private GamePanelSurfaceView Game;

    public Alert(GamePanelSurfaceView Game) {

        this.Game = Game;
    }

    public void RunAlert() {
        Handler handler = new Handler(Looper.getMainLooper());
        // Returns the application's main looper, which lives in the main thread of the application

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Game.alert.show();
            }
        }, 1000); // Delay in milliseconds until the runnable is executed
    }
}*/