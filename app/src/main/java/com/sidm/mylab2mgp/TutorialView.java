package com.sidm.mylab2mgp;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import ECS.*;

/**
 * Created by lenov on 24/01/2017.
 */

public class TutorialView extends GamePanelSurfaceView implements SensorEventListener {
    public TutorialView(Context context) {
        super(context);
        getHolder().addCallback(this);
        zeCurrContext = (Gamepage)context;
        MusicSystem.getInstance().playBGM("Adventure");

        scaledbg = GraphicsSystem.getInstance().getImage("AdventureBackground");

    }
    // This is where you render your game stuff!
    public void RenderGameplay(Canvas canvas) {
        if (canvas == null)
            return;
        canvas.drawBitmap(scaledbg, 0, 0, null);

    }
    // This is where you update your game logic
    public void update(float dt, float fps) {

    }
        @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}