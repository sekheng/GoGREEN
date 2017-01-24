package com.sidm.mylab2mgp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by lenov on 24/01/2017.
 */

public class TutorialView extends GamePanelSurfaceView implements SensorEventListener {
    public TutorialView(Context context) {
        super(context);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}