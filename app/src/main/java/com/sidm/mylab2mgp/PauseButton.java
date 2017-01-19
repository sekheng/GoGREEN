package com.sidm.mylab2mgp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

/**
 * Created by - on 16/1/2017.
 */

public class PauseButton {
    private boolean isPaused = false;
    public Objects PauseB1;
    private Objects PauseB2;
    private int Screenwidth;
    private int Screenheight;

    public PauseButton(Context context, Resources res, int x, int y)
    {
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
            isPaused = false;
        }
        else if(!isPaused && CheckCollision(PauseB2.getX(),PauseB2.getY(), PauseB2.getWidth(), PauseB2.getHeight(),pressX,pressY,0,0))
        {
            isPaused = true;
        }
    }

}
