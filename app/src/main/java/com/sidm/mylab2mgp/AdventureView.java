package com.sidm.mylab2mgp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

/**
 * Created by lenov on 03/12/2016.
 */

public class AdventureView extends GamePanelSurfaceView {
    public AdventureView (Context context){

        // Context is the current state of the application/object
        super(context);

        // Adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // 1d) Set information to get screen size
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Screenwidth = metrics.widthPixels;
        Screenheight = metrics.heightPixels;

        // 1e)load the image when this class is being instantiated
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.gamescene);
        scaledbg = Bitmap.createScaledBitmap(bg,Screenwidth,Screenheight,true);

        // 4c) Load the images of the spaceships
        for (int num = 0; num < 4; ++num)
        {
            ship_friend[num] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1 + num), Screenwidth, Screenheight, true);
        }


        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

}
