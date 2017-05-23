package com.dragonfury.duy.p4pt04nguyendennis;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by 1383504 on 5/23/2017.
 */
public class SensorActivity extends Activity implements SensorEventListener{



    public SensorActivity(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

    }

    private float[] orientations = new float[3];
    private float[] accelerometer = new float[3];
    private float[] geoField = new float [3];
    private float[] rotateMatrix = new float[9];
    private SensorManager sensorManager;
    Sensor accelero;
    Sensor magField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { //Calculates magnetic field around device and stores it
            geoField = sensorEvent.values;
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometer = sensorEvent.values;
        }

        sensorManager.getRotationMatrix(rotateMatrix, null, accelerometer, geoField); //Calculate RotationMatrix - Device screen is facing sky, top is towards north pole
        sensorManager.getOrientation(rotateMatrix, orientations); //Calculate amount of rotation along x, y, and z axis
        System
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume() {
        super.onResume();
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
//        sensorManager.registerListener(this, geoField, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public float[] getOrientation() {
        return orientations;
    }
}
