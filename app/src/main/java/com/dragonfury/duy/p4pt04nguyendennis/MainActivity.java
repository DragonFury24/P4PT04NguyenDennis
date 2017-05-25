package com.dragonfury.duy.p4pt04nguyendennis;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


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
        setContentView(new DrawView(this));
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
        SensorManager.getOrientation(rotateMatrix, orientations); //Calculate amount of rotation along x, y, and z axis
//        for (float orientation: orientations) {
//            System.out.println("test:" + orientation);
//        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelero, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magField, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public float[] getOrientation() {
        return orientations;
    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(new DrawView(this));
//
//    }
}
