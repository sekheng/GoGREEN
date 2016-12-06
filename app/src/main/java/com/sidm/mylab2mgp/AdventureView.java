package com.sidm.mylab2mgp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;

import ECS.*;

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

        bunchOfEntites = new LinkedList<Entity>();
        Entity zeEntity = new Entity(new String("zeShip"));
        thePlayer = zeEntity;
        TransformationComponent zeTransfrom = new TransformationComponent((short)50,(short)50,(short)Screenwidth/10,(short)Screenheight/10);
        zeEntity.setComponent(zeTransfrom);
        BitComponent zeImages = new BitComponent();
        for (int num = 0; num < 4; ++num)
        {
            zeImages.setImages(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1 + num), (int)zeTransfrom.scaleX, (int)zeTransfrom.scaleY, true));
        }
        zeEntity.setComponent(zeImages);
        PhysicComponent zePhysics = new PhysicComponent();
        zePhysics.speed = 300;
        zeEntity.setComponent(zePhysics);
        PlayerActiveStuff = new PlayerComponent();
        zeEntity.setComponent(PlayerActiveStuff);
        bunchOfEntites.add(zeEntity);

        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
        zeBackgroundPaint = new Paint();
        zeBackgroundPaint.setARGB(255,255,255,255);

        allTheBoxes = new LinkedList<Entity>();
        float zeNewTotHeight = Screenheight/10;
        zeOverallBounds = new TransformationComponent(0, zeNewTotHeight, Screenwidth, Screenheight - (zeNewTotHeight * 2.f));
        for (long numRow = 0; numRow < numOfBoxesPerRow; ++numRow)
        {
            for (long numCol = 0; numCol < numOfBoxesPerCol; ++numCol)
            {
                Entity boxEntity = new Entity("Box");
                TransformationComponent boxTransform = new TransformationComponent(
                        zeOverallBounds.posX + (numCol * (zeOverallBounds.scaleX / numOfBoxesPerCol)),
                        zeOverallBounds.posY + (numRow * (zeOverallBounds.scaleY / numOfBoxesPerRow)),
                        zeOverallBounds.posX + ((numCol+1) * (zeOverallBounds.scaleX / numOfBoxesPerCol)),
                        (zeOverallBounds.posY) + ((numRow+1) * (zeOverallBounds.scaleY / numOfBoxesPerRow)));
                boxEntity.setComponent(boxTransform);
                boxEntity.setComponent(new BoxComponent());
                allTheBoxes.add(boxEntity);
            }
        }
        averageBoxSizeX = (long)zeOverallBounds.scaleX / numOfBoxesPerCol;
        averageBoxSizeY = (long)zeOverallBounds.scaleY / numOfBoxesPerRow;
        //TODO: Remove when not debugging
        debuggingGrid = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.outlined_sq), (int)(averageBoxSizeX), (int)(averageBoxSizeY), true);
        debuggingRedFilled = new Paint();
        debuggingRedFilled.setARGB(255,255,0,0);
        debuggingBlueFilled = new Paint();
        debuggingBlueFilled.setARGB(255, 0, 0, 255);
        //TODO: Remove when not debugging

        zeEntity = new Entity("small garbage");
        GarbageComponent zeGarbage = new GarbageComponent();
        zeGarbage.zeGrids = allTheBoxes;
        short []zeNewSpace = {(short)(4 + (4*numOfBoxesPerCol)),(short)(5 + (4*numOfBoxesPerCol)),(short)(6 + (4*numOfBoxesPerCol))};
        zeGarbage.setSpaces(zeNewSpace);
        zeGarbage.onNotify(PlayerActiveStuff);
        zeGarbage.onNotify(150.f);
        zeEntity.setComponent(zeGarbage);
        bunchOfEntites.add(zeEntity);

        zeEntity = new Entity("Garbage Bin");
        GarbageCollectorComponent zeCollector = new GarbageCollectorComponent();
        zeCollector.zeGrids = allTheBoxes;
        short []zeNewSpace2 = {(short)(4 + (6*numOfBoxesPerCol)),(short)(5 + (6*numOfBoxesPerCol)),(short)(4 + (7*numOfBoxesPerCol)), (short)(5 + (7*numOfBoxesPerCol))};
        zeCollector.setSpaces(zeNewSpace2);
        zeCollector.onNotify(PlayerActiveStuff);
        zeEntity.setComponent(zeCollector);
        bunchOfEntites.add(zeEntity);
    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null)
            return;
//        canvas.drawBitmap(scaledbg,bgX,bgY,null);   // 1st background image
//        canvas.drawBitmap(scaledbg, bgX + Screenwidth, bgY, null);  // 2nd image
        canvas.drawRoundRect(0,0,Screenwidth,Screenheight,0,0,zeBackgroundPaint);
        RenderTextOnScreen(canvas, "FPS: " + FPS, 50,50,50);
        RenderTextOnScreen(canvas, "PlayerScore:" + PlayerActiveStuff.score_, 50, 100, 50);
        RenderTextOnScreen(canvas, "AmountOfTrash:" + PlayerActiveStuff.amountOfGarbageCollected, 50, 150, 50);
        // 4d) Draw the spaceships
        //canvas.drawBitmap(ship_friend[shipindex],mX,mY,null);   // location of the ship based on the touch
        BitComponent zeBit;
        TransformationComponent zeTransform;
        //TODO remove when not debugging
        for (Entity zeEntity : allTheBoxes)
        {
            zeTransform = (TransformationComponent)zeEntity.getComponent("Transformation Stuff");
            canvas.drawBitmap(debuggingGrid, zeTransform.posX, zeTransform.posY, null);
            BoxComponent zeBoxType = (BoxComponent)(zeEntity.getComponent("zeBox"));
            switch (zeBoxType.whatBox)
            {
                case FILL:
                    canvas.drawRoundRect(zeTransform.posX, zeTransform.posY, zeTransform.scaleX, zeTransform.scaleY,0,0,debuggingRedFilled);
                    break;
                case BIN:
                    canvas.drawRoundRect(zeTransform.posX, zeTransform.posY, zeTransform.scaleX, zeTransform.scaleY,0,0,debuggingBlueFilled);
                    break;
                default:
            }
        }
        //TODO remove when not debugging

        for (Entity zeEntity : bunchOfEntites)
        {
            switch (zeEntity.turnOnFlag_)
            {
                case 1:
                    if (zeEntity.checkActiveComponent("Ze Images"))
                    {
                        zeBit = (BitComponent) zeEntity.getComponent("Ze Images");
                        zeTransform = (TransformationComponent) zeEntity.getComponent("Transformation Stuff");
                        canvas.drawBitmap(zeBit.getCurrImage(), zeTransform.posX, zeTransform.posY, null);
                    }
                    break;
                default:
            }
        }


    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;
       for (Entity zeEntity : bunchOfEntites)
        {
            switch (zeEntity.turnOnFlag_)
            {
                case 1:
                    zeEntity.Update(dt);
                    break;
                default:
            }
        }
    }
    public boolean onTouchEvent(MotionEvent event){

        // 5) In event of touch on screen, the spaceship will relocate to the point of touch
        short x = (short)event.getX();  // temp value of the screen touch
        short y = (short)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (x >= zeOverallBounds.posX && x <= zeOverallBounds.scaleX
                    && y >= zeOverallBounds.posY && y <= (zeOverallBounds.scaleY + averageBoxSizeY))
            {
                long boxX = 0, boxY = 0;
                while (x > (boxX + 1) * averageBoxSizeX)
                    ++boxX;
                while (y > (boxY + 1) * averageBoxSizeY * 1.25f)
                    ++boxY;
                long totalNum = boxX + (boxY * numOfBoxesPerCol);
                if (totalNum < allTheBoxes.size()) {
                    Entity theExactBox = allTheBoxes.get((int) (boxX + (boxY * numOfBoxesPerCol)));
                    TransformationComponent zeBoxTransform = (TransformationComponent) (theExactBox.getComponent("Transformation Stuff"));
                    //TransformationComponent zeTransform = (TransformationComponent) (thePlayer.getComponent("Transformation Stuff"));
                    PhysicComponent zePhysics = (PhysicComponent) (thePlayer.getComponent("zePhysic"));
                    zePhysics.setNextPosToGo(zeBoxTransform.posX + (averageBoxSizeX * 0.25f), zeBoxTransform.posY + (averageBoxSizeY * 0.25f));
                    thePlayer.getComponent("zePlayer").onNotify(zeBoxTransform);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    LinkedList<Entity> bunchOfEntites;
    LinkedList<Entity> allTheBoxes;
    Entity thePlayer;
    PlayerComponent PlayerActiveStuff;
    Paint zeBackgroundPaint;
    TransformationComponent zeOverallBounds;
    long numOfBoxesPerRow = 8, numOfBoxesPerCol = 8, averageBoxSizeX, averageBoxSizeY;

    //TODO: Remove when not debugging
    Bitmap debuggingGrid;
    Paint debuggingRedFilled, debuggingBlueFilled;
    //TODO: Remove when not debugging
}
