package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.content.Context;

/**
 * Created by 1383504 on 5/11/2017.
 */
public class DrawView extends View {
    public DrawView(Context context) {
        super(context);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                geoField = sensorEvent.values;

                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { //Calculates magnetic field around device and stores it
                    geoField[0] = sensorEvent.values[0];
                    geoField[1] = sensorEvent.values[1];
                    geoField[2] = sensorEvent.values[2];
                }

                sensorManager.getRotationMatrix(rotateMatrix, null, accelerometer, geoField); //Calculate RotationMatrix - Device screen is facing sky, top is towards north pole
                sensorManager.getOrientation(rotateMatrix, orientations); //Calculate amount of rotation along x, y, and z axis
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

        };

    }

    private float[] orientations = new float[3];
    private float[] accelerometer = new float[3];
    private float[] geoField = new float [3];
    private float[] rotateMatrix = new float[9];
    private SensorManager sensorManager;
    private SensorEventListener listener; //Detects when sensor is being used/changed
    private Paint paint = new Paint();
    private float degrees;
    Path path = new Path();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        for (float radOrientation: orientations) { //Convert orientations values to degrees
//            radOrientation = (float) Math.toDegrees(radOrientation);
//        }

        for (float orientation: orientations) {
            System.out.println("test:"+orientation);
        }

        if (orientations[0] > 0) {

        }else if (orientations[0] < 0) {

        }

        if (orientations[1] > 0) {

        }else if (orientations[1] < 0) {

        }

        if (orientations[2] > 0) {
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 3 * getHeight() / 2, paint);
        }else if (orientations[2] < 0) {
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 2560 - (1280 - 7 * orientations [2]), paint);
        }



    }

    private void drawTri(int x1, int y1, int x2, int y2, int x3, int y3, Canvas c, Paint p) {
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();
        c.drawPath(path, p);
    }




}
