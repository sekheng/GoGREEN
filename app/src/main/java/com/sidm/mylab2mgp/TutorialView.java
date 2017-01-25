package com.sidm.mylab2mgp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.LinkedList;

import ECS.*;

/**
 * Created by lenov on 24/01/2017.
 */

public class TutorialView extends GamePanelSurfaceView implements SensorEventListener {
    public TutorialView(Context context) {
        super(context);
        getHolder().addCallback(this);
        zeCurrContext = (Gamepage)context;

        //pauseButton = new PauseButton(context, zeCurrContext,getResources(),(GridSystem.getInstance().getScreenWidth()/9) * 8, (GridSystem.getInstance().getScreenHeight())/120);


        MusicSystem.getInstance().playBGM("Adventure");
        MusicSystem.getInstance().loadSoundEffect("GarbagePicked");
        MusicSystem.getInstance().loadSoundEffect("RemoveTrash");

        zeOverallBounds = GridSystem.getInstance().getBoundary();
        //TODO: Remove when not debugging
        debuggingGrid = GraphicsSystem.getInstance().getImage("debuggingGrid");
        debuggingRedFilled = new Paint();
        debuggingRedFilled.setARGB(255,255,0,0);
        debuggingBlueFilled = new Paint();
        debuggingBlueFilled.setARGB(255, 0, 0, 255);
        //TODO: Remove when not debugging
        GridSystem.getInstance().Exit();

        thePlayer = new Entity("Player");   //For now, it ca make my life easier.
        bunchOfEntites = new LinkedList<Entity>();
        bunchOfInactive = new LinkedList<Entity>();
        AmountOfTrashLeft = new LinkedList<Entity>();
        GarbageBuilder.getInstance().setObjectPools(bunchOfEntites, bunchOfInactive, AmountOfTrashLeft, GridSystem.getInstance().allTheBoxes, thePlayer);
        scaledbg = GraphicsSystem.getInstance().getImage("AdventureBackground");

        // Setting up the player stuff
        TransformationComponent zeTransfrom = new TransformationComponent((short)50,(short)50,(short)GridSystem.getInstance().getScreenWidth()/10,(short)GridSystem.getInstance().getScreenHeight()/10);
        thePlayer.setComponent(zeTransfrom);
        playerTransform = zeTransfrom;
        playerBits = new ProtaganistAnimComponent(getResources());
        thePlayer.setComponent(playerBits);
        PhysicComponent zePhysics = new PhysicComponent();
        zePhysics.speed = 300;
        thePlayer.setComponent(zePhysics);
        PlayerActiveStuff = new PlayerComponent();
        thePlayer.setComponent(PlayerActiveStuff);
        PlayerActiveStuff.theCurrentGamePlayerOn = this;

        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);
        // Make the GamePanel focusable so it can handle events
        setFocusable(true);

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
        GarbageBuilder.getInstance().buildPlasticBin("ze plastic Bin", anotherSpace.toArray(new Short[anotherSpace.size()]));
        anotherSpace.clear();
        anotherSpace.add((short)1);
        anotherSpace.add((short)6);
        GarbageBuilder.getInstance().buildPlasticBottleGarbage("le plastic garbage", anotherSpace.toArray(new Short[anotherSpace.size()]), 0.f);
        anotherSpace.clear();
        anotherSpace.add((short)3);
        anotherSpace.add((short)6);
        GarbageBuilder.getInstance().buildSmalleGarbage("le small garbage", anotherSpace.toArray(new Short[anotherSpace.size()]), 0.f);
        anotherSpace.clear();
        anotherSpace.add((short)5);
        anotherSpace.add((short)6);
        GarbageBuilder.getInstance().buildWastePaperGarbage("le paper garbage", anotherSpace.toArray(new Short[anotherSpace.size()]), 0.f);


        TimeColor = new Paint();
        TimeColor.setARGB(200, 0, 135, 42); // Taking from the Hex Picker Color
        ProgressColor = new Paint();    // The progress of playing should have a color!
        ProgressColor.setARGB(220, 229, 207, 6);
        CapacityColor = new Paint();
        CapacityColor.setARGB(220, 206, 14, 14);
        PlasticGarbageColor = new Paint();
        PlasticGarbageColor.setARGB(220, 206, 6, 6);
        GeneralGarbageColor = new Paint();
        GeneralGarbageColor.setARGB(220, 109, 224, 8);
        PaperGarbageColor = new Paint();
        PaperGarbageColor.setARGB(220, 8, 181, 224);

        // Initializing accelerometer
        theSensor = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
        theSensor.registerListener(this, theSensor.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
        updateTheAccelerometerPreviousValueTimer = 2.0f;

        overallTime = timeLeft = 20.0f;
        TotalNumOfGarbage = AmountOfTrashLeft.size();
    }
    // This is where you render your game stuff!
    public void RenderGameplay(Canvas canvas) {
        if (canvas == null)
            return;
        canvas.drawBitmap(scaledbg, 0, 0, null);
        //pauseButton.RenderPauseButton(canvas);
        RenderTextOnScreen(canvas, "FPS: " + FPS, 50,50,50);
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
        playerBits.draw(canvas);
        playerBits.setPosX((int)playerTransform.posX);
        playerBits.setPosY((int)playerTransform.posY);
    }
    // This is where you update your game logic
    public void update(float dt, float fps) {
        FPS = fps;
        if (fps > 30 /*&& !pauseButton.getIsPause()*/) { // only update if it is running above 30 FPS
            thePlayer.Update(dt);
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
            updateTheAccelerometerPreviousValueTimer += dt;
            float checkingTheXDifference = SensorVars[0] - PreviousValues[0];
            if (Math.abs(checkingTheXDifference) > 7.0f) // We cheat here and just check for x value since it is landscape
            {
                PlayerActiveStuff.onNotify("ShakedTooMuch");
                MusicSystem.getInstance().playSoundEffect("RemoveTrash");
                PreviousValues = SensorVars.clone();
                updateTheAccelerometerPreviousValueTimer = 0;
            }
        }
        /*else if(pauseButton.getIsPause())
        {
            pauseButton.createPauseDialog();
        }*/
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
            //pauseButton.checkIfPressedPause((int)x,(int)y);
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
        return MusicSystem.getInstance().playSoundEffect(zeEvent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorVars = sensorEvent.values;
        if (updateTheAccelerometerPreviousValueTimer > 0.5f) // Need to check for every interval instead of frame
        {
            PreviousValues = SensorVars.clone();
            updateTheAccelerometerPreviousValueTimer = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    // For Player Stuff
    LinkedList<Entity> bunchOfEntites, bunchOfInactive, AmountOfTrashLeft;
    Entity thePlayer;
    TransformationComponent playerTransform;
    ProtaganistAnimComponent playerBits;
    PlayerComponent PlayerActiveStuff;
    TransformationComponent zeOverallBounds;
   //PauseButton pauseButton;
    // For Player Stuff
    //TODO: Remove when not debugging
    Bitmap debuggingGrid;
    Paint debuggingRedFilled, debuggingBlueFilled;
    //TODO: Remove when not debugging
    // Creating and using accelerometer
    private SensorManager theSensor;
    private float SensorVars[] = new float[3], PreviousValues[] = {0, 0, 0}, updateTheAccelerometerPreviousValueTimer;    // Realised that it is being updated too fast, need to limit it
    // Color for the UIs
    Paint TimeColor, ProgressColor, CapacityColor, PlasticGarbageColor, GeneralGarbageColor, PaperGarbageColor;    // Color of the Timer
    float timeLeft, overallTime;
    int TotalNumOfGarbage;
}