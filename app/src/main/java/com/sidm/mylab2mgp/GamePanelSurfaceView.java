package com.sidm.mylab2mgp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanelSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    // Implement this interface to receive information about changes to the surface.

    protected GameThread myThread = null; // Thread to control the rendering

    // 1a) Variables used for background rendering
    protected Bitmap bg  //bg = background
            , scaledbg; // scaledbg = scaled version of background

    // 1b) Define Screen width and Screen height as integer
//    int Screenwidth, Screenheight;

    // 1c) Variables for defining background start and end point
    protected short bgX = 0, bgY = 0;

    // 4a) bitmap array to stores 4 images of the spaceship
    //protected  Bitmap[] ship_friend = new Bitmap[4];

    // 4b) Variable as an index to keep track of the spaceship images
    //protected short shipindex = 0;


    // Variables for FPS
    public float FPS;

    //Activity activityTracker;

    //constructor for this GamePanelSurfaceView class
    public GamePanelSurfaceView (Context context){

        // Context is the current state of the application/object
        super(context);

        // Adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // 1d) Set information to get screen size
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        Screenwidth = metrics.widthPixels;
//        Screenheight = metrics.heightPixels;

        // 1e)load the image when this class is being instantiated
//        bg = BitmapFactory.decodeResource(getResources(), R.drawable.gamescene);
//        scaledbg = Bitmap.createScaledBitmap(bg,Screenwidth,Screenheight,true);

        // 4c) Load the images of the spaceships
//        for (int num = 0; num < 4; ++num)
//        {
//            ship_friend[num] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship2_1 + num), Screenwidth, Screenheight, true);
//        }


        // Create the game loop thread
        myThread = new GameThread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    //must implement inherited abstract methods
    public void surfaceCreated(SurfaceHolder holder){
        // Create the thread
        if (!myThread.isAlive()){
            myThread = new GameThread(getHolder(), this);
            myThread.startRun(true);
            myThread.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        // Destroy the thread
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
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    public void RenderGameplay(Canvas canvas) {
        // 2) Re-draw 2nd image after the 1st image ends
        if (canvas == null)
            return;
        canvas.drawBitmap(scaledbg,bgX,bgY,null);   // 1st background image
        canvas.drawBitmap(scaledbg, bgX , bgY, null);  // 2nd image

        // 4d) Draw the spaceships
//        canvas.drawBitmap(ship_friend[shipindex],mX,mY,null);   // location of the ship based on the touch

        // Bonus) To print FPS on the screen

    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;

//        switch (GameState) {
//            case 0: {
//                // 3) Update the background to allow panning effect
//                bgX -= 500 * dt;    // Temp value to speed the panning
//                if (bgX < 0)
//                {
//                    bgX = 0;
//                }
//
//
//                // 4e) Update the spaceship images / shipIndex so that the animation will occur.
////                shipindex++;
////                shipindex %= 4;
//            }
//            break;
//        }
    }

    // Rendering is done on Canvas
    public void doDraw(Canvas canvas){
//        switch (GameState)
//        {
//            case 0:
//                RenderGameplay(canvas);
//                break;
//        }
        RenderGameplay(canvas);
    }

    //Print text on Screen
    public void RenderTextOnScreen(Canvas canvas, String text, int posX, int posY
            , int textsize)
    {
        Paint paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        paint.setStrokeWidth(100);
        paint.setTextSize(textsize);
        canvas.drawText(text, posX, posY, paint);
    }
    public void RenderTextOnScreen(Canvas canvas, String text, int posX, int posY, Paint zePaint)
    {
        canvas.drawText(text, posX, posY, zePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        // 5) In event of touch on screen, the spaceship will relocate to the point of touch
        short x = (short)event.getX();  // temp value of the screen touch
        short y = (short)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
//            mX = (short)(x - (ship_friend[shipindex].getWidth() / 2));
//            mY = (short)(y - (ship_friend[shipindex].getHeight() / 2));
        }
        return super.onTouchEvent(event);
    }
    public boolean onNotify(String zeEvent)
    {
        return false;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return true;
    }
    protected static final String TAG = null;
    protected Gamepage zeCurrContext = null;
}
