package com.sidm.mylab2mgp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import java.util.LinkedList;

import ECS.BitComponent;
import ECS.Entity;
import ECS.TransformationComponent;

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

//        // 4c) Load the images of the spaceships
//        for (int num = 0; num < 4; ++num)
//        {
//            ship_friend[num] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1 + num), Screenwidth, Screenheight, true);
//        }
        bunchOfEntites = new LinkedList<Entity>();
        Entity zeEntity = new Entity(new String("zeShip"));
        TransformationComponent zeTransfrom = new TransformationComponent((short)50,(short)50,(short)Screenwidth,(short)Screenheight);
        zeEntity.setComponent(zeTransfrom);
        BitComponent zeImages = new BitComponent();
        for (int num = 0; num < 4; ++num)
        {
            zeImages.setImages(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1 + num), Screenwidth, Screenheight, true));
        }
        zeEntity.setComponent(zeImages);
        bunchOfEntites.add(zeEntity);

        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null)
            return;
        canvas.drawBitmap(scaledbg,bgX,bgY,null);   // 1st background image
        canvas.drawBitmap(scaledbg, bgX + Screenwidth, bgY, null);  // 2nd image

        // 4d) Draw the spaceships
        //canvas.drawBitmap(ship_friend[shipindex],mX,mY,null);   // location of the ship based on the touch
        BitComponent zeBit;
        TransformationComponent zeTransform;
        for (Entity zeEntity : bunchOfEntites)
        {
            zeBit = (BitComponent)zeEntity.getComponent("Ze Images");
            zeTransform = (TransformationComponent)zeEntity.getComponent("Transformation Stuff");
            canvas.drawBitmap(zeBit.getCurrImage(), zeTransform.posX, zeTransform.posY, null);
        }

        // Bonus) To print FPS on the screen

    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;
        bgX -= 500 * dt;    // Temp value to speed the panning
        if (bgX < -Screenwidth)
        {
            bgX = 0;
        }
        for (Entity zeEntity : bunchOfEntites)
        {
            zeEntity.Update(dt);
        }
    }

    LinkedList<Entity> bunchOfEntites;
}
