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

import ECS.GridSystem;

/**
 * Created by - on 19/1/2017.
 */

public class AlertCreator {
    public boolean showAlert = false;
    public boolean winOrLose = false;

    AlertDialog.Builder alert = null;
    //week13 shared preference
    /*SharedPreferences sharedPrefname;
    SharedPreferences.Editor editName;*/
    String Playername;

    SharedPreferences sharedPreferscore;
    int Playerscore;
    Gamepage zeCurrContext = null;

    EditFileForNameScore editFileForNameScore;
    //public Alert alert;

    public AlertCreator(final Context context) {

        /*sharedPrefname = context.getSharedPreferences("PlayerUSERID",Context.MODE_PRIVATE);
        editName = sharedPrefname.edit();
        Playername = "Player1";
        Playername = sharedPrefname.getString("PlayerUSERID", "DEFAULT");*/

        showAlert = false;
        zeCurrContext = (Gamepage)context;

        sharedPreferscore = context.getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        Playerscore = 0;
        Playerscore = sharedPreferscore.getInt("UserScore", 0);

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
        alert.setTitle("game over?!");
        alert.setIcon(R.drawable.gogreenlogo);
        alert.setMessage("Enter your Name");
        alert.setCancelable(false);
        alert.setView(input);

        editFileForNameScore = new EditFileForNameScore(context);
        //editFileForNameScore.UpdateListOfNameAndScore(context);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Playername = input.getText().toString();
                Playerscore = sharedPreferscore.getInt("UserScore", 0);
                /*editName.putString("PlayerUSERID", Playername);
                editName.commit();*/

                editFileForNameScore.UpdateTextFile(Playername,Playerscore,context);
                if(!winOrLose)
                {
                    zeCurrContext.onClick("lose!");
                }
                else
                {
                    zeCurrContext.onClick("win!");
                }
                GridSystem.getInstance().Exit();
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
        showAlert = true;
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