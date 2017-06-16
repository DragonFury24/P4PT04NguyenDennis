package com.dragonfury.duy.p4pt04nguyendennis;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by duyde on 6/16/2017.
 */

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private float[] orientationDegrees = new float[3];
    private float[] accelerometer = new float[3];
    private float[] geoField = new float [3];
    private float[] rotateMatrix = new float[9];
    private float[] orientations = new float[3];
    private SensorManager sensorManager;
    Sensor accelero;
    Sensor magField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { //Calculates magnetic field around device and stores it
            geoField = event.values;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometer = event.values;
        }

        SensorManager.getRotationMatrix(rotateMatrix, null, accelerometer, geoField); //Calculate RotationMatrix - Start point: Device screen is facing ceiling
        SensorManager.getOrientation(rotateMatrix, orientationDegrees); //Calculate amount of rotation along x, y, and z axis
        for (int i = 0; i < orientationDegrees.length; i++) {
            orientations[i] = (float)(orientationDegrees[i] * 180 / Math.PI);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelero, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public float[] getOrientation() {
        return orientationDegrees;
    }
}
