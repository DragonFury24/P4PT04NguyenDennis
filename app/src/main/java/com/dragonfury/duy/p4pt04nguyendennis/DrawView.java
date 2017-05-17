package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

        sensorManager = (sensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] accelerometer = sensorEvent.values;
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    accelerometer[0] = sensorEvent.values[0];
                    accelerometer[1] = sensorEvent.values[1];
                    accelerometer[2] = sensorEvent.values[2];
                    degrees = (float) Math.toDegrees(Math.atan(Math.abs(accelerometer[1]) / Math.abs(accelerometer[2])));


                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private float[] orientation = new float[3];
    private float[] accelerometer = new float[3];
    private Sensor sensor;
    private SensorManager sensorManager;
    private SensorEventListener listener;
    private MainActivity mainActivity;
    private float degrees;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
