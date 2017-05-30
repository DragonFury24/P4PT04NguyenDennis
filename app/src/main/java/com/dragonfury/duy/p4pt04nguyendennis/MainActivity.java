package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private float[] orientations = new float[3];
    private float[] orientations1 = new float[3];
    private float[] accelerometer = new float[3];
    private float[] geoField = new float [3];
    private float[] rotateMatrix = new float[9];
    private float ori0;
    private float ori1;
    private float ori2;
    private SensorManager sensorManager;
    Sensor accelero;
    Sensor magField;
    DrawView drawView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView=new DrawView(this);
        setContentView(drawView);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { //Calculates magnetic field around device and stores it
            geoField = sensorEvent.values;
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometer = sensorEvent.values;
        }

        SensorManager.getRotationMatrix(rotateMatrix, null, accelerometer, geoField); //Calculate RotationMatrix - Device screen is facing sky, top is towards north pole
        orientations=SensorManager.getOrientation(rotateMatrix, orientations); //Calculate amount of rotation along x, y, and z axis
        ori0 = orientations[0];
        ori1 = orientations[1];
        ori2 = orientations[2];
        System.out.println("test act:"+orientations[0]+","+orientations.length);
        int i=0;
        for (float orientation: orientations) {
            orientations1[i++]=orientation;
            System.out.println("test:" + orientation);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelero, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public float[] getOrientations() {
        System.out.println("test get:"+orientations[0]+","+orientations1[0]);
        return orientations;
    }

    public float getOri0() {
        return ori0;
    }
    public float getOri1() {
        return ori1;
    }
    public float getOri2() {
        return ori2;
    }
}
