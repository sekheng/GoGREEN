package com.sidm.mylab2mgp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import ECS.*;

/**
 * Created by lenov on 03/12/2016.
 */

public class AdventureView extends GamePanelSurfaceView {
    public AdventureView (Context context){

        // Context is the current state of the application/object
        super(context);

        zeCurrContext = (Gamepage)context;

        BGM_ = MediaPlayer.create(zeCurrContext, R.raw.adventure_bgm);
        BGM_.start();
        BGM_.setLooping(true);
        allTheSounds_ = new HashMap<>();
        audioAttributes_ = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        playSounds_ = new SoundPool.Builder().setAudioAttributes(audioAttributes_).setMaxStreams(2).build();
        allTheSounds_.put("GarbagePicked", playSounds_.load(context, R.raw.pick_garbage, 1));

        getHolder().addCallback(this);

        scaledbg = GraphicsSystem.getInstance().getImage("AdventureBackground");

        zeOverallBounds = GridSystem.getInstance().getBoundary();

        TransformationComponent zeTransfrom = new TransformationComponent((short)50,(short)50,(short)GridSystem.getInstance().getScreenWidth()/10,(short)GridSystem.getInstance().getScreenHeight()/10);
        //TODO: Remove when not debugging
        debuggingGrid = GraphicsSystem.getInstance().getImage("debuggingGrid");
        debuggingRedFilled = new Paint();
        debuggingRedFilled.setARGB(255,255,0,0);
        debuggingBlueFilled = new Paint();
        debuggingBlueFilled.setARGB(255, 0, 0, 255);
        //TODO: Remove when not debugging

        thePlayer = new Entity("Player");   //For now, it ca make my life easier.
        bunchOfEntites = new LinkedList<Entity>();
        bunchOfInactive = new LinkedList<Entity>();
        AmountOfTrashLeft = new LinkedList<Entity>();
        GarbageBuilder.getInstance().setObjectPools(bunchOfEntites, bunchOfInactive, AmountOfTrashLeft, GridSystem.getInstance().allTheBoxes, thePlayer);

        thePlayer.setComponent(zeTransfrom);
        playerTransform = zeTransfrom;

        PhysicComponent zePhysics = new PhysicComponent();
        zePhysics.speed = 300;
        thePlayer.setComponent(zePhysics);
        PlayerActiveStuff = new PlayerComponent();
        thePlayer.setComponent(PlayerActiveStuff);
        PlayerActiveStuff.theCurrentGamePlayerOn = this;
        /*Resources res = getResources();
        AnimationComponent zeAnimation = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res,R.drawable.protagonist),
                GridSystem.getInstance().getScreenWidth()/4, GridSystem.getInstance().getScreenHeight()/17,true),448,64,0.5f,8,2,3);*/
        ProtaganistAnimComponent animComponent = new ProtaganistAnimComponent(getResources());
        thePlayer.setComponent(animComponent);
        playerBits = animComponent;
        bunchOfEntites.add(thePlayer);

        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
        zeBackgroundPaint = new Paint();
        zeBackgroundPaint.setARGB(255,255,255,255);

        short []zeNewSpace = {2,5}; // This means row 5, col 5
        GarbageBuilder.getInstance().buildSmalleGarbage("small garbage", zeNewSpace, 0);
        short []zeNewSpace2 = {5,2}; // This means row 5, col 5
        GarbageBuilder.getInstance().buildSmalleGarbage("small garbage", zeNewSpace,0);

        short []anotherSpave = {2,2};
        GarbageBuilder.getInstance().buildGarbageBin("ze Garbage Bin", anotherSpave);
        overallTime = timeLeft = 10.f;

        TimeColor = new Paint();
        TimeColor.setARGB(200, 0, 135, 42); // Taking from the Hex Picker Color
        ProgressColor = new Paint();    // The progress of playing should have a color!
        ProgressColor.setARGB(220, 229, 207, 6);
        TotalNumOfGarbage = AmountOfTrashLeft.size();
        CapacityColor = new Paint();
        CapacityColor.setARGB(220, 206, 14, 14);

        gettingRandomStuff = new Random();
    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null)
            return;
        canvas.drawBitmap(scaledbg, 0, 0, null);
        RenderTextOnScreen(canvas, "FPS: " + FPS, 50,50,50);
        //RenderTextOnScreen(canvas, "PlayerScore:" + PlayerActiveStuff.score_, 50, 100, 50);
        //RenderTextOnScreen(canvas, "AmountOfTrash:" + PlayerActiveStuff.amountOfGarbageCollected, 50, 150, 50);
        //RenderTextOnScreen(canvas, "TimeLeft:" + timeLeft, 50, GridSystem.getInstance().getScreenHeight() - (GridSystem.getInstance().getScreenHeight() / 10), 50);
        canvas.drawRoundRect(0.0f, // 0 because is at the left of the screen and draw it at half the screenWidth because need to make space for the capacity
                zeOverallBounds.scaleY + zeOverallBounds.posY, // Because the drawing of rectangle starts by the end of the row
                zeOverallBounds.scaleX * (timeLeft / overallTime) * 0.5f,  // so that it will scale from right to left
                (zeOverallBounds.posY * 2) + zeOverallBounds.scaleY,   // The height of the rect. adding posY*2 and scaleY will become the overall screen height
                1, 1, TimeColor
                );  // Displaying time in rectangle
        // Drawing the capacity of the garbage
        canvas.drawRoundRect(zeOverallBounds.scaleX * 0.5f,  // Starting from the middle of screen width because sharing space with time
                zeOverallBounds.scaleY + zeOverallBounds.posY, // Because the drawing of rectangle starts by the end of the row
                zeOverallBounds.scaleX * PlayerActiveStuff.gettingThePercentageOfFullCapacity(),
                (zeOverallBounds.posY * 2) + zeOverallBounds.scaleY,   // The height of the rect. adding posY*2 and scaleY will become the overall screen height
                1,1,CapacityColor
        );
        float remainingGarbage = TotalNumOfGarbage - AmountOfTrashLeft.size();
        canvas.drawRoundRect(0.0f,  // because at the very left of the screen
                0.0f,   // because at the very top of the screen
                zeOverallBounds.scaleX * (remainingGarbage/(float)TotalNumOfGarbage),  // Since scaleX is screen width, so we can multiply the right coordinate by RemainingGarbage/TotalGarbageFromBeginning.
                zeOverallBounds.posY,   // Bottom coordinate shall be before the first row of grids
                1, 1, ProgressColor
                );  // Displaying time in rectangle
        BitComponent zeBit;
        TransformationComponent zeTransform;
        //TODO remove when not debugging
        for (Entity zeEntity : GridSystem.getInstance().allTheBoxes)
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
            if (!zeEntity.equals(thePlayer)) // This will make sure that the player object will not be rendered so that the player will be the last to render out
            switch (zeEntity.turnOnFlag_)
            {
                case 1:
                    if(zeEntity.checkActiveComponent("zeAnimations"))
                    {
                        zeTransform = (TransformationComponent) zeEntity.getComponent("Transformation Stuff");
                        ProtaganistAnimComponent zeDraw = (ProtaganistAnimComponent) zeEntity.getComponent("zeProtagAnimations");
                        zeDraw.getAnimComponent().draw(canvas);
                        zeDraw.getAnimComponent().setX((int)zeTransform.posX);
                        zeDraw.getAnimComponent().setY((int)zeTransform.posY);
                    }
                    else if (zeEntity.checkActiveComponent("Ze Images"))
                    {
                        zeBit = (BitComponent) zeEntity.getComponent("Ze Images");
                        zeTransform = (TransformationComponent) zeEntity.getComponent("Transformation Stuff");
                        canvas.drawBitmap(zeBit.getCurrImage(), zeTransform.posX, zeTransform.posY, null);
                    }
                    break;
                default:
            }
        }
        // This might be removed if A* search is done
        playerBits = (ProtaganistAnimComponent) thePlayer.getComponent("zeProtagAnimations");
        playerBits.draw(canvas);
        playerBits.setPosX((int)playerTransform.posX);
        playerBits.setPosY((int)playerTransform.posY);
    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;
        if (fps > 25) {
            if (timeLeft <= 0)
            {
                zeCurrContext.onClick("lose!");
                GridSystem.getInstance().Exit();
            }
            else if (PlayerActiveStuff.amountOfGarbageCollected == 0 && AmountOfTrashLeft.isEmpty())
            {
                zeCurrContext.onClick("win!");
                GridSystem.getInstance().Exit();
            }
            else {
                thePlayer.Update(dt);

                timeLeft = Math.max(timeLeft - dt, 0);
                boolean stopTheLoop = false;
                for (Entity zeEntity : bunchOfEntites) {
                    switch (zeEntity.turnOnFlag_) {
                        case 0:
                            bunchOfEntites.remove(zeEntity);
                            bunchOfInactive.add(zeEntity);
                            if (zeEntity.checkActiveComponent("zeGarbage"))
                                AmountOfTrashLeft.remove(zeEntity);
                            stopTheLoop = true;
                            break;
                        default:
                    }
                    if (stopTheLoop)
                        break;
                }
                // Putting right after bunchOfEntities so that garbage that's been collected are removed
                for (Entity zeGarbage : AmountOfTrashLeft)  // This is to check if there is any inactive garbage waiting to respawn
                {
                    if (zeGarbage.turnOnFlag_ == 0)
                    {
                        GarbageComponent zeGarbageComp = (GarbageComponent)(zeGarbage.getComponent("zeGarbage"));
                        if (zeGarbageComp.timeToSpawn <= TransformationComponent.EPSILON)    // If the timer has become less than 0
                        {
                            int randomRow = gettingRandomStuff.nextInt(GridSystem.getInstance().getNumOfBoxesPerRow()); // getting the random row like from 0 to 7
                            int randomCol = gettingRandomStuff.nextInt(GridSystem.getInstance().getNumOfBoxesPerCol()); // Same as the above
                            if (GarbageBuilder.getInstance().CheckingThroughEmptyBoxes(randomRow, randomCol, zeGarbageComp)) // If the spawn position happens to be empty. then spawn it.
                            {
                                // Getting the specific index in the grids
                                zeGarbageComp.setSpaces((short)(randomCol + (randomRow * GridSystem.getInstance().getNumOfBoxesPerCol())));
                                zeGarbage.turnOnFlag_ = 1;  // Turn the gameobject to be active
                                bunchOfEntites.add(zeGarbage);  // add the gameobject to the active list
                                bunchOfInactive.remove(zeGarbage);  // well because they were originally at inactivelist
                            }
                            else
                            {
                                zeGarbageComp.timeToSpawn = 1;
                            }
                        }
                        else
                            zeGarbageComp.timeToSpawn -= dt;
                    }
                }
            }
        }
    }
    public boolean onTouchEvent(MotionEvent event){

        // 5) In event of touch on screen, the spaceship will relocate to the point of touch
        float x = event.getX();  // temp value of the screen touch
        float y = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (x >= zeOverallBounds.posX && x <= zeOverallBounds.scaleX
                    && y >= zeOverallBounds.posY && y <= (zeOverallBounds.scaleY + GridSystem.getInstance().getAverageBoxSize().scaleY))
            {
                long boxX = 0, boxY = 0;
                while (x > ((boxX + 1) * GridSystem.getInstance().getAverageBoxSize().scaleX) + GridSystem.getInstance().getOffSetFromScreenWidth())    // (boxX+1) helps to offset the grids, and adding the OffsetFromScreenWidth will help rescale it
                    ++boxX;
                while (y > ((boxY+1) * GridSystem.getInstance().getAverageBoxSize().scaleY) + GridSystem.getInstance().getOffSetFromScreenHeight())      // (boxY+2) helps to offset the grid, and adding the OffsetFromScreenWidth will help rescale it
                    ++boxY;
                long totalNum = boxX + (boxY * GridSystem.getInstance().getNumOfBoxesPerCol());
                if (totalNum < GridSystem.getInstance().allTheBoxes.size()) {
                    Entity theExactBox = GridSystem.getInstance().allTheBoxes.get((int) (boxX + (boxY * GridSystem.getInstance().getNumOfBoxesPerCol())));
                    TransformationComponent zeBoxTransform = (TransformationComponent) (theExactBox.getComponent("Transformation Stuff"));
                    PhysicComponent zePhysics = (PhysicComponent) (thePlayer.getComponent("zePhysic"));
                    zePhysics.setNextPosToGo(zeBoxTransform.posX + (GridSystem.getInstance().getAverageBoxSize().scaleX * 0.25f), zeBoxTransform.posY + (GridSystem.getInstance().getAverageBoxSize().scaleY * 0.25f));
                    thePlayer.getComponent("zePlayer").onNotify(zeBoxTransform);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if (myThread.isAlive()){
            myThread.startRun(false);
        }
        boolean retry = true;
        while (retry) {
            try {
                myThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                e.getCause();
            }
        }
        BGM_.stop();    // Stopping the BGM
        BGM_.release(); // Releasing the BGM for more memories
        for (int zeIDofSound : allTheSounds_.values())  // iterating through the hashmap and unload all sound effects
        {
            playSounds_.unload(zeIDofSound);
        }
        playSounds_.release();    // release the sound effects for memory
        MusicSystem.getInstance().stopCurrentBGM();
    }

    public boolean onNotify(String zeEvent) // I used this function mainly to play sound effects
    {
        if (allTheSounds_.containsKey(zeEvent))
        {
            playSounds_.play(allTheSounds_.get(zeEvent), 1.0f, 1.0f, 0, 0, 1.0f);
            return true;
        }
        return false;
    }

    LinkedList<Entity> bunchOfEntites, bunchOfInactive, AmountOfTrashLeft;
    Entity thePlayer;
    TransformationComponent playerTransform;
    ProtaganistAnimComponent playerBits;
    PlayerComponent PlayerActiveStuff;
    Paint zeBackgroundPaint;
    TransformationComponent zeOverallBounds;
    float timeLeft, overallTime;

    //TODO: Remove when not debugging
    Bitmap debuggingGrid;
    Paint debuggingRedFilled, debuggingBlueFilled;
    //TODO: Remove when not debugging
    SoundPool playSounds_;
    AudioAttributes audioAttributes_;
    HashMap<String, Integer> allTheSounds_;
    MediaPlayer BGM_;
    Paint TimeColor, ProgressColor, CapacityColor;    // Color of the Timer
    int TotalNumOfGarbage;
    Random gettingRandomStuff;  // this is for randomizing the spawn position of the garbage
    //static boolean initializedThingsOnce = true;
}
