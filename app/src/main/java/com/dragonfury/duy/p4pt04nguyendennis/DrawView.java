package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Canvas;
import android.hardware.SensorManager;
import android.view.View;
import android.content.Context;

/**
 * Created by 1383504 on 5/11/2017.
 */
public class DrawView extends View {
    public DrawView(Context context) {
        super(context);
    }

    SensorManager sensorManager = new SensorManager() {
        @Override
        public int getSensors() {
            return super.getSensors();
        }
    }
    float[] orientation = new float[3];

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       orientation = sensorManager.getOrientation()
    }
}
