package com.dragonfury.duy.p4pt04nguyendennis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private float[] orientations = new float[3];
    private float[] accelerometer = new float[3];
    private float[] geoField = new float [3];
    private float[] rotateMatrix = new float[9];
    private SensorManager sensorManager;
    Sensor accelero;
    Sensor magField;


    public class DrawView extends View {
        public DrawView(Context context) {
            super(context);

        }

        private Paint paint = new Paint();
        private Path path = new Path();
        private float[] orientations = new float[3];

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            orientations = getOrientations();
            System.out.println("ori1: " + orientations[0]);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(100 * getWidth() / 1440);

            if (orientations[0] > 0) {

            } else if (orientations[0] < 0) {

            }

            if (orientations[1] > 0) {
                canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 2 * getHeight() / 3, paint);
            } else if (orientations[1] <= 0) {
                canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 2560 - (1280 - 7 * orientations[2]), paint);
            }

            if (orientations[2] >= 0) {
                canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 3, getHeight() / 2, paint);
            } else if (orientations[2] <= 0) {

            }

            invalidate();

        }

        private void drawTri(int x1, int y1, int x2, int y2, int x3, int y3, Canvas c, Paint p) {
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.close();
            c.drawPath(path, p);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
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

        SensorManager.getRotationMatrix(rotateMatrix, null, accelerometer, geoField); //Calculate RotationMatrix - Start point: Device screen is facing ceiling
        SensorManager.getOrientation(rotateMatrix, orientations); //Calculate amount of rotation along x, y, and z axis
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
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

    public float[] getOrientations() {
        for (float orientation: orientations){
            System.out.println(orientation);
        }
        return orientations;
    }
}
