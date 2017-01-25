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

    public TutorialDialogs(Context context)
    {
        this.context = context;

        createDialog = true;
        dialog = new Dialog(context);
        dialog.setCancelable(false);//disable the player to back if press back button
        dialog.setContentView(R.layout.dialogfortutorial);
        Drawable drawable = context.getDrawable(R.drawable.sandbackground);
        drawable.setAlpha(50);
        dialog.getWindow().setBackgroundDrawable(drawable);

        btn_next = (Button)dialog.findViewById(R.id.btn_next);
        textview = (TextView)dialog.findViewById(R.id.textView);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog = true;
                inDialog = false;
                dialog.dismiss();

            }
        });
    }

    public void showDialog()
    {
        if(createDialog)
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
}
