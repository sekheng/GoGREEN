package com.sidm.mylab2mgp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ECS.GridSystem;

/**
 * Created by - on 25/1/2017.
 */

public class TutorialDialogs {
    private boolean createDialog = false, inDialog = false;
    private Context context = null;
    private Button btn_next;
    private TextView textview;
    private Dialog dialog;
    public int tutorialNumber = 0;
    private Gamepage gamepage = null;

    public TutorialDialogs(Context context, final Gamepage gamepage)
    {
        this.context = context;
        this.gamepage = gamepage;
        tutorialNumber = 0;

        createDialog = true;
        inDialog = true;
        //changeText = false;
        dialog = new Dialog(context);
        dialog.setCancelable(false);//disable the player to back if press back button
        dialog.setContentView(R.layout.dialogfortutorial);
        Drawable drawable = context.getDrawable(R.drawable.sandbackground);
        drawable.setAlpha(150);
        dialog.getWindow().setBackgroundDrawable(drawable);

        btn_next = (Button)dialog.findViewById(R.id.btn_next);
        textview = (TextView)dialog.findViewById(R.id.textView);
        textview.setText("In this tutorial you will learn how to play the game!");
        textview.setAlpha(1.0f);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog = true;
                inDialog = false;
                tutorialNumber += 1;
               if(tutorialNumber == 11)
                {
                    gamepage.onClick("quit");
                    GridSystem.getInstance().Exit();
                }
                dialog.dismiss();
            }
        });
    }

    public void updateMessage()
    {
        if(!dialog.isShowing()) {
            if (tutorialNumber == 1) {
                textview.setText("Tap one of the Garbage that is towards the right side of the screen.");
                inDialog = true;
            }
            else if(tutorialNumber == 3)
            {
                textview.setText("Everytime a Garbage is collected(Max2), its representing color will appear at the bottom left section of the screen.");
                inDialog = true;
            }
            else if(tutorialNumber == 4)
            {
                textview.setText("Tap the Recycling bin with the same color shown at the bottom left.");
                inDialog = true;
            }
            else if(tutorialNumber == 6)
            {
                textview.setText("Tilt your phone foward and back to throw the Garbage of the same color in.");
                inDialog = true;
            }
            else if(tutorialNumber == 8)
            {
                textview.setText("This is how you play the game!");
                inDialog = true;
            }
            else if(tutorialNumber == 9)
            {
                textview.setText("In the main game, there will be a top bar to show how much progress you have and a timer at the bottom left to indicate how much time you have to collect Garbage.");
                inDialog = true;
            }
            else if(tutorialNumber == 10)
            {
                textview.setText("Have Fun!");
                inDialog = true;
            }

        }
    }

    public void showDialog()
    {
        updateMessage();
        if(createDialog && inDialog)
        {
            createDialog = false;
            Handler handler = new Handler(Looper.getMainLooper());
            // Returns the application's main looper, which lives in the main thread of the application

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dialog.show();
                }
            }, 100); // 1000  Delay in milliseconds until the runnable is executed
            //dialog.show();
        }
    }
     public void setInDialog(boolean bool)
     {
         inDialog = bool;
     }

    public boolean getIndialog()
    {
        return inDialog;
    }

}
