package com.dragonfury.duy.p4pt04nguyendennis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private float[] getOrientation = new float[3];
    private float[] accelerometer = new float[3];
    private float[] geoField = new float [3];
    private float[] rotateMatrix = new float[9];
    private float[] orientations = new float[3];
    private SensorManager sensorManager;
    Sensor accelero;
    Sensor magField;



    public class DrawView extends View {
        public DrawView(Context context) {
            super(context);

        }

        private Paint paint = new Paint();
        RectF rectF = new RectF();
        RectF RemHappyRectF = new RectF();
        RectF RemAngryRectF = new RectF();
        Bitmap happyRemBMP = BitmapFactory.decodeResource(getResources(), R.drawable.remhappy);
        Bitmap angryRemBMP = BitmapFactory.decodeResource(getResources(), R.drawable.remangry);
        Boolean RemAngry = false;
        float RemRectFX;
        float RemRectFY;

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(100 * getWidth() / 1440);

            //Draw line from center to top of screen
            if ((orientations[1]) > 0) {
                paint.setColor(Color.BLUE);
                canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 0, paint);
            }

            //Draw line from center to bottom of screen
            if (0 > orientations[1] && orientations[1] > -90) {
                paint.setColor(Color.RED);
                canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight(), paint);
            }

            //
            rectF.offset(0, orientations[1] * -1);

            if (RemHappyRectF.top >= 0) {
                canvas.drawBitmap(happyRemBMP, null, RemHappyRectF, null);
                RemHappyRectF.offset(0, orientations[1] * -1);
            }else {
                RemAngry = true;
                canvas.drawBitmap(angryRemBMP, null, RemAngryRectF, null);
            }
            if (RemAngry) {
                RemAngryRectF.set(0 * getWidth() / 1440, getHeight() * .33f * getHeight() / 2560, 1000 * getWidth() / 1440, getHeight() * .66f * getHeight() / 2560);
                RemAngryRectF.offsetTo(RemRectFX, RemRectFY);

            }
            RemRectFX = RemHappyRectF.left;
            RemRectFY = RemHappyRectF.top;
            System.out.println("top: " + RemHappyRectF.top);
            System.out.println("bottom: " + RemHappyRectF.bottom);
            System.out.println("thistop: " + this.getTop());
            System.out.println("thisbottom: " + this.getBottom());
            invalidate();

        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            rectF.set(480 * getWidth() / 1440, getHeight() * .33f * getHeight() / 2560, getWidth() * .66f * getWidth() / 1440, getHeight() * .66f * getHeight() / 2560);
            RemHappyRectF.set(getWidth() * .33f * getWidth() / 1440, getHeight() * .33f * getHeight() / 2560, getWidth() * .66f * getWidth() / 1440, getHeight() * .66f * getHeight() / 2560);
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
        SensorManager.getOrientation(rotateMatrix, getOrientation); //Calculate amount of rotation along x, y, and z axis
        for (int i = 0; i < getOrientation.length; i++) {
            orientations[i] = (float)(getOrientation[i] * 180 / Math.PI);
        }

        for (int i = 0; i < orientations.length; i++) {
            System.out.println("degrees:" + i + " " + orientations[i]);
        }
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
        return orientations;
    }
}
