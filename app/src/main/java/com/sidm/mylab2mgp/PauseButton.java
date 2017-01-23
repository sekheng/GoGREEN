package com.sidm.mylab2mgp;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import ECS.GridSystem;

/**
 * Created by - on 16/1/2017.
 */

public class PauseButton {
    private boolean isPaused = false;
    private boolean createDialog = false;
    private Context context = null;
    public Objects PauseB1;
    private Objects PauseB2;
    private int Screenwidth;
    private int Screenheight;
    private Button btn_ok;
    private Button btn_cancel;
    private Dialog dialog;
    private Toastbox toastmaker;
    private Vibrator vibrator;


    public PauseButton(Context context, final Gamepage gamepage, Resources res, int x, int y)
    {
        this.context = context;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Screenwidth = metrics.widthPixels;
        Screenheight = metrics.heightPixels;
        /*PauseB1 = new Objects(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(res,R.drawable.pause_notpressed)),
                (int)(Screenwidth)/15, (int)(Screenheight)/10,true), Screenwidth - 200, 30);
        PauseB2 = new Objects(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(res,R.drawable.pause_pressed)),
                (int)(Screenwidth)/15, (int)(Screenheight)/10,true), Screenwidth - 200, 30);*/
        PauseB1 = new Objects(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(res,R.drawable.pause_notpressed)),
                (int)(Screenwidth)/15, (int)(Screenheight)/10,true), x, y);
        PauseB2 = new Objects(Bitmap.createScaledBitmap((BitmapFactory.decodeResource(res,R.drawable.pause_pressed)),
                (int)(Screenwidth)/15, (int)(Screenheight)/10,true), x, y);

        toastmaker = new Toastbox();
        toastmaker.toastmessageShort(context, "Are you sure you want to quit?");
        toastmaker.setShowMessageOnce(true);

        //Looper.prepareMainLooper();
        createDialog = true;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog);
        Drawable drawable = context.getDrawable(R.drawable.sandbackground);
        drawable.setAlpha(50);
        dialog.getWindow().setBackgroundDrawable(drawable);


        btn_ok = (Button)dialog.findViewById(R.id.btn_ok);
        btn_cancel = (Button)dialog.findViewById(R.id.btn_cancel);

        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toastmaker.getToggleMessageShown()) {
                    isPaused = false;
                    createDialog = true;
                    gamepage.onClick("quit");
                    GridSystem.getInstance().Exit();
                    dialog.dismiss();
                }
                else
                {
                    vibrator.vibrate(250);
                    toastmaker.showToast();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPaused = false;
                createDialog = true;
                toastmaker.reset();
                dialog.dismiss();
            }
        });

    }

    public void RenderPauseButton(Canvas canvas)
    {
        if(!isPaused)
        {
            canvas.drawBitmap(PauseB1.getBitmap(), PauseB1.getX(),PauseB1.getY(), null);
        }
        else
        {
            canvas.drawBitmap(PauseB2.getBitmap(), PauseB2.getX(),PauseB2.getY(), null);
        }
    }

    public void PauseButtonUpdate(GameThread thread)
    {
        if(isPaused)
        {
            thread.pause();
        }
        else
        {
            thread.unPause();
        }
    }

    public void createPauseDialog()
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
       /* if(!createDialog && isPaused)//havent create dialog and is in paused state
        {
            //Looper.prepare();
            createDialog = true;
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.customdialog);

            btn_ok = (Button)dialog.findViewById(R.id.btn_ok);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isPaused = false;
                    createDialog = false;
                    dialog.dismiss();
                }
            });


        }
        else if(createDialog)
        {
            dialog.show();
        }*/
    }


    public boolean getIsPause()
    {
        return isPaused;
    }


    boolean CheckCollision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
    {
        if (x2>=x1 && x2<=x1+w1){ // Start to detect collision of the top left corner
            if (y2>=y1 && y2<=y1+h1) // Comparing yellow box to blue box
                return true;
        }
        if (x2+w2>=x1 && x2+w2<=x1+w1){ // Top right corner
            if (y2>=y1 && y2<=y1+h1)
                return true;
        }
        return false;
    }

    void checkIfPressedPause(int pressX, int pressY)
    {
        if(isPaused && CheckCollision(PauseB1.getX(),PauseB1.getY(), PauseB1.getWidth(), PauseB1.getHeight(),pressX,pressY,0,0))
        {
            //isPaused = false;
        }
        else if(!isPaused && CheckCollision(PauseB2.getX(),PauseB2.getY(), PauseB2.getWidth(), PauseB2.getHeight(),pressX,pressY,0,0))
        {
            isPaused = true;
        }
    }



}
