package com.sidm.mylab2mgp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.LinkedList;
import java.util.Random;

import ECS.*;

/**
 * Created by lenov on 03/12/2016.
 */

public class AdventureView extends GamePanelSurfaceView implements SensorEventListener {
    public AdventureView (Context context){

        // Context is the current state of the application/object
        super(context);

        zeCurrContext = (Gamepage)context;

        MusicSystem.getInstance().playBGM("Adventure");
        MusicSystem.getInstance().loadSoundEffect("GarbagePicked");
        MusicSystem.getInstance().loadSoundEffect("RemoveTrash");

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

//        Short []zeNewSpace = {2,5}; // This means row 5, col 5
//        GarbageBuilder.getInstance().buildSmalleGarbage("small garbage", zeNewSpace, 0);
//        Short []zeNewSpace2 = {5,2}; // This means row 5, col 5
//        GarbageBuilder.getInstance().buildSmalleGarbage("small garbage", zeNewSpace,0);

        LinkedList <Short> anotherSpace = new LinkedList<>();
        anotherSpace.add((short)1);
        anotherSpace.add((short)1);
        GarbageBuilder.getInstance().buildPaperBin("ze paper Bin", anotherSpace.toArray(new Short[anotherSpace.size()]));
        anotherSpace.clear();
        anotherSpace.add((short)4);
        anotherSpace.add((short)1);
        GarbageBuilder.getInstance().buildGeneralBin("ze general Bin", anotherSpace.toArray(new Short[anotherSpace.size()]));
        anotherSpace.clear();
        anotherSpace.add((short)5);
        anotherSpace.add((short)1);
//        GarbageBuilder.getInstance().buildPlasticBin("ze plastic Bin", anotherSpace.toArray(new Short[anotherSpace.size()]));
//        GarbageBuilder.getInstance().buildPlasticBottleGarbage("Plastic bottle", anotherSpace.toArray(new Short[anotherSpace.size()]), 2);
//        GarbageBuilder.getInstance().buildWastePaperGarbage("Waste Paper", anotherSpace.toArray(new Short[anotherSpace.size()]), 5.5f);

        overallTime = timeLeft = 20.f;

        TimeColor = new Paint();
        TimeColor.setARGB(200, 0, 135, 42); // Taking from the Hex Picker Color
        ProgressColor = new Paint();    // The progress of playing should have a color!
        ProgressColor.setARGB(220, 229, 207, 6);
        TotalNumOfGarbage = AmountOfTrashLeft.size();
        CapacityColor = new Paint();
        CapacityColor.setARGB(220, 206, 14, 14);
        PlasticGarbageColor = new Paint();
        PlasticGarbageColor.setARGB(220, 206, 6, 6);
        GeneralGarbageColor = new Paint();
        GeneralGarbageColor.setARGB(220, 109, 224, 8);
        PaperGarbageColor = new Paint();
        PaperGarbageColor.setARGB(220, 8, 181, 224);

        gettingRandomStuff = new Random();

        theToastMessage = new Toastbox();
        theToastMessage.toastmessageShort(zeCurrContext, "Get To the Bin!");
        theToastMessage.setShowMessageOnce(true);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int Screenwidth = metrics.widthPixels;
        int Screenheight = metrics.heightPixels;

        pauseButton = new PauseButton(context, getResources(),(Screenwidth/9) * 8, (Screenheight)/120);

        sharedPreferscore = getContext().getSharedPreferences("UserScore", Context.MODE_PRIVATE);
        editScore = sharedPreferscore.edit();
        Playerscore = 0;
        Playerscore = sharedPreferscore.getInt("UserScore", 0);

        alertCreator = new AlertCreator(context);

        theSensor = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
        theSensor.registerListener(this, theSensor.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
        updateThePreviousValueTimer = 2.0f;
        LevelLoadSystem.getInstance();
    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null)
            return;

        //canvas.drawBitmap(pauseButton.PauseB1.getBitmap(), pauseButton.PauseB1.getX(),pauseButton.PauseB1.getY(), null);

        canvas.drawBitmap(scaledbg, 0, 0, null);
        pauseButton.RenderPauseButton(canvas);
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
        short numberOfGarbageCarried = 0;   // Need to count how many garbage the player carried
        float howMuchSpaceAGrid = (zeOverallBounds.scaleX * 0.5f) / PlayerActiveStuff.MaxCapacity();    // Getting the Average size for a grid GUI
        for (String zeTypeOfGarbage : PlayerActiveStuff.carryGarbageType)   // Deciphering the type of garbage then give the color
        {
            ++numberOfGarbageCarried;
            // We need to get the TYPE from "TYPE|SCORE"
            int firstOR = zeTypeOfGarbage.indexOf('|');
            byte zeType = Byte.parseByte(zeTypeOfGarbage.substring(0, firstOR));
            switch (zeType)
            {
                case 0: // This means it is Paper waste
        canvas.drawRoundRect((zeOverallBounds.scaleX * 0.5f) + ((numberOfGarbageCarried-1) * howMuchSpaceAGrid),  // Starting from the middle of screen width because sharing space with time then need to move the sqaure along
                zeOverallBounds.scaleY + zeOverallBounds.posY, // Because the drawing of rectangle starts by the end of the row
                (zeOverallBounds.scaleX * 0.5f) + (numberOfGarbageCarried * howMuchSpaceAGrid),
                (zeOverallBounds.posY * 2) + zeOverallBounds.scaleY,   // The height of the rect. adding posY*2 and scaleY will become the overall screen height
                1,1,PaperGarbageColor
        );
                    break;
                case 1: // this means general waste
                    canvas.drawRoundRect((zeOverallBounds.scaleX * 0.5f) + ((numberOfGarbageCarried-1) * howMuchSpaceAGrid),  // Starting from the middle of screen width because sharing space with time then need to move the sqaure along
                            zeOverallBounds.scaleY + zeOverallBounds.posY, // Because the drawing of rectangle starts by the end of the row
                            (zeOverallBounds.scaleX * 0.5f) + (numberOfGarbageCarried * howMuchSpaceAGrid),
                            (zeOverallBounds.posY * 2) + zeOverallBounds.scaleY,   // The height of the rect. adding posY*2 and scaleY will become the overall screen height
                            1,1,GeneralGarbageColor
                    );
                    break;
                case 2: // this means plastic waste
                    canvas.drawRoundRect((zeOverallBounds.scaleX * 0.5f) + ((numberOfGarbageCarried-1) * howMuchSpaceAGrid),  // Starting from the middle of screen width because sharing space with time then need to move the sqaure along
                            zeOverallBounds.scaleY + zeOverallBounds.posY, // Because the drawing of rectangle starts by the end of the row
                            (zeOverallBounds.scaleX * 0.5f) + (numberOfGarbageCarried * howMuchSpaceAGrid),
                            (zeOverallBounds.posY * 2) + zeOverallBounds.scaleY,   // The height of the rect. adding posY*2 and scaleY will become the overall screen height
                            1,1,PlasticGarbageColor
                    );
                    break;
            }
        }
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

        // TODO: Remove when not debugging accelerometer
//        for (int num = 0; num < PreviousValues.length; ++num)
//        {
//            RenderTextOnScreen(canvas, "Value"+num+":"+PreviousValues[num], 0, (int)((GridSystem.getInstance().getAverageBoxSize().scaleY * 2) + (num * GridSystem.getInstance().getAverageBoxSize().scaleY)), 50);
//        }
//        for (int num = 0; num < SensorVars.length; ++num)
//        {
//            RenderTextOnScreen(canvas, "Value"+num+":"+SensorVars[num], 0, (int)((GridSystem.getInstance().getAverageBoxSize().scaleY * 5) + (num * GridSystem.getInstance().getAverageBoxSize().scaleY)), 50);
//        }
        // TODO: Remove when not debugging accelerometer
    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;
        if (fps > 25 && !pauseButton.getIsPause()) {
            updateThePreviousValueTimer += dt;
            if (timeLeft <= 0)
            {
                if(!alertCreator.showAlert) {
                    Playerscore = (int) PlayerActiveStuff.getScore_();
                    editScore.putInt("UserScore", Playerscore);
                    editScore.commit();
                    alertCreator.winOrLose = false;
                    alertCreator.RunAlert(1000);
                /*zeCurrContext.onClick("lose!");
                GridSystem.getInstance().Exit();*/
                }
            }
            else if (PlayerActiveStuff.carryGarbageType.isEmpty() && AmountOfTrashLeft.isEmpty())
            {
                if(!alertCreator.showAlert) {
                    Playerscore = (int) PlayerActiveStuff.getScore_();
                    editScore.putInt("UserScore", Playerscore);
                    editScore.commit();
                    alertCreator.winOrLose = true;
                    alertCreator.RunAlert(1000);
                /*zeCurrContext.onClick("win!");
                GridSystem.getInstance().Exit();*/
                }
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
                    if (zeGarbage.turnOnFlag_ == 0) {
                        GarbageComponent zeGarbageComp = (GarbageComponent) (zeGarbage.getComponent("zeGarbage"));
                        if (zeGarbageComp.timeToSpawn <= TransformationComponent.EPSILON)    // If the timer has become less than 0
                        {
                            int randomRow = gettingRandomStuff.nextInt(GridSystem.getInstance().getNumOfBoxesPerRow()); // getting the random row like from 0 to 7
                            int randomCol = gettingRandomStuff.nextInt(GridSystem.getInstance().getNumOfBoxesPerCol()); // Same as the above
                            if (GarbageBuilder.getInstance().CheckingThroughEmptyBoxes(randomRow, randomCol, zeGarbageComp)) // If the spawn position happens to be empty. then spawn it.
                            {
                                // Getting the specific index in the grids
                                zeGarbageComp.setSpaces((short) (randomCol + (randomRow * GridSystem.getInstance().getNumOfBoxesPerCol())));
                                zeGarbage.turnOnFlag_ = 1;  // Turn the gameobject to be active
                                bunchOfEntites.add(zeGarbage);  // add the gameobject to the active list
                                bunchOfInactive.remove(zeGarbage);  // well because they were originally at inactivelist
                            } else {
                                zeGarbageComp.timeToSpawn = 1;
                            }
                        } else
                            zeGarbageComp.timeToSpawn -= dt;
                    }
                }
                // This to check whether is there any garbage left
                if (AmountOfTrashLeft.size() == 0)
                    theToastMessage.showToast();
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
            pauseButton.checkIfPressedPause((int)x,(int)y);
            //pauseButton.PauseButtonUpdate(myThread);
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
        MusicSystem.getInstance().stopCurrentBGM();
        MusicSystem.getInstance().stopAllSoundEffect();
        theSensor.unregisterListener(this);
    }

    public boolean onNotify(String zeEvent) // I used this function mainly to play sound effects
    {
        if (MusicSystem.getInstance().playSoundEffect(zeEvent))
            return true;
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorVars = sensorEvent.values;
        float checkingTheXDifference = SensorVars[0] - PreviousValues[0];
        if (updateThePreviousValueTimer > 0.5f) // Need to check for every interval instead of frame
        {
            PreviousValues = SensorVars.clone();
            updateThePreviousValueTimer = 0;
        }
        else if (Math.abs(checkingTheXDifference) > 7.0f) // We cheat here and just check for x value since it is landscape
        {
            PlayerActiveStuff.onNotify("ShakedTooMuch");
            MusicSystem.getInstance().playSoundEffect("RemoveTrash");
            PreviousValues = SensorVars.clone();
            updateThePreviousValueTimer = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Only use it when u want your sensor to be very accurate
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
    Paint TimeColor, ProgressColor, CapacityColor, PlasticGarbageColor, GeneralGarbageColor, PaperGarbageColor;    // Color of the Timer
    int TotalNumOfGarbage;
    Random gettingRandomStuff;  // this is for randomizing the spawn position of the garbage
    Toastbox theToastMessage;   // This is used for popping a message to tell the player to hurry up
    PauseButton pauseButton;
    // Creating and using accelerometer
    private SensorManager theSensor;
    private float SensorVars[] = new float[3], PreviousValues[] = {0, 0, 0}, updateThePreviousValueTimer;    // Realised that it is being updated too fast, need to limit it
    SharedPreferences sharedPreferscore;
    SharedPreferences.Editor editScore;
    int Playerscore;
    AlertCreator alertCreator;
    private int MaxLevels, CurrentLevel;
}
