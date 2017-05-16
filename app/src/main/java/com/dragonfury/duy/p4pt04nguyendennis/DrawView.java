package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;

/**
 * Created by 1383504 on 5/11/2017.
 */
public class DrawView extends View {
    public DrawView(Context context) {
        super(context);
        context.getSystemService(Context.SENSOR_SERVICE);
    }

    SensorManager sensorManager = new SensorManager();
    float[] orientation = new float[3];
    float[] accelerometer = new float[3];
    SensorActivity sensorActivity = new SensorActivity();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getContext().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = Sensor.TYPE_ACCELEROMETER;
        orientation = sensorActivity.getOrientation(sensorManager.getRotationMatrix(null, null, Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_MAGNETIC_FIELD), orientation);
    }

}
