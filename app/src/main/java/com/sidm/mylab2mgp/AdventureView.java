package com.sidm.mylab2mgp;

import android.content.Context;
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
//        if (initializedThingsOnce)
//        {
//            //MusicSystem.getInstance().addBGM(MediaPlayer.create(context, R.raw.adventure_bgm), "Adventure");
//            initializedThingsOnce = false;
//        }
//        MusicSystem.getInstance().playBGM("Adventure");

        // Adding the callback (this) to the surface holder to intercept events
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
        AnimationComponent zeAnimation = new AnimationComponent(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.protagonist),
                GridSystem.getInstance().getScreenWidth()/4, GridSystem.getInstance().getScreenHeight()/17,true),448,64,0.5f,8,2,3);
        thePlayer.setComponent(zeAnimation);
        playerBits = zeAnimation;
        bunchOfEntites.add(thePlayer);

        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
        zeBackgroundPaint = new Paint();
        zeBackgroundPaint.setARGB(255,255,255,255);

        short []zeNewSpace = {5,5}; // This means row 5, col 5
        GarbageBuilder.getInstance().buildSmalleGarbage("small garbage", zeNewSpace);

        short []anotherSpave = {2,2};
        GarbageBuilder.getInstance().buildGarbageBin("ze Garbage Bin", anotherSpave);
        timeLeft = 10.f;
    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null)
            return;
        canvas.drawBitmap(scaledbg, 0, 0, null);
        RenderTextOnScreen(canvas, "FPS: " + FPS, 50,50,50);
        RenderTextOnScreen(canvas, "PlayerScore:" + PlayerActiveStuff.score_, 50, 100, 50);
        RenderTextOnScreen(canvas, "AmountOfTrash:" + PlayerActiveStuff.amountOfGarbageCollected, 50, 150, 50);
        RenderTextOnScreen(canvas, "TimeLeft:" + timeLeft, 50, GridSystem.getInstance().getScreenHeight() - (GridSystem.getInstance().getScreenHeight() / 10), 50);
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
                        AnimationComponent zeDraw = (AnimationComponent) zeEntity.getComponent("zeAnimations");
                        zeDraw.draw(canvas);
                        zeDraw.setX((int)zeTransform.posX);
                        zeDraw.setY((int)zeTransform.posY);
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
        playerBits.draw(canvas);
        playerBits.setX((int)playerTransform.posX);
        playerBits.setY((int)playerTransform.posY);
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
                    && y >= zeOverallBounds.posY && y <= (zeOverallBounds.scaleY + GridSystem.getInstance().getAverageBoxSize().scaleY))
            {
                long boxX = 0, boxY = 0;
                while (x > (boxX + 1) * GridSystem.getInstance().getAverageBoxSize().scaleX)
                    ++boxX;
                while (y > (boxY + 1) * GridSystem.getInstance().getAverageBoxSize().scaleY * 1.25f)
                    ++boxY;
                long totalNum = boxX + (boxY * GridSystem.getInstance().getNumOfBoxesPerCol());
                if (totalNum < GridSystem.getInstance().allTheBoxes.size()) {
                    Entity theExactBox = GridSystem.getInstance().allTheBoxes.get((int) (boxX + (boxY * GridSystem.getInstance().getNumOfBoxesPerCol())));
                    TransformationComponent zeBoxTransform = (TransformationComponent) (theExactBox.getComponent("Transformation Stuff"));
                    //TransformationComponent zeTransform = (TransformationComponent) (thePlayer.getComponent("Transformation Stuff"));
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
    AnimationComponent playerBits;
    PlayerComponent PlayerActiveStuff;
    Paint zeBackgroundPaint;
    TransformationComponent zeOverallBounds;
    float timeLeft = 10.f;

    //TODO: Remove when not debugging
    Bitmap debuggingGrid;
    Paint debuggingRedFilled, debuggingBlueFilled;
    //TODO: Remove when not debugging
    SoundPool playSounds_;
    AudioAttributes audioAttributes_;
    HashMap<String, Integer> allTheSounds_;
    MediaPlayer BGM_;
    //static boolean initializedThingsOnce = true;
}
