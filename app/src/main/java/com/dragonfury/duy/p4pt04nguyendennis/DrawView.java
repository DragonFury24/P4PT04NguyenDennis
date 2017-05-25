package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Canvas;
import android.graphics.Color;
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

    }

    private Paint paint = new Paint();
    private SensorActivity sensorActivity = new SensorActivity(getContext());
    private Path path = new Path();
    private float[] orientations = new float[3];
    MainActivity mainActivity = new MainActivity();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        orientations = mainActivity.getOrientation();
        for (float orientation: orientations) {
            System.out.println("test:"+orientation);
        }

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(100 * getWidth() / 1440);

        if (orientations[0] > 0) {

        }else if (orientations[0] < 0) {

        }

        if (orientations[1] > 0) {
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 2 * getHeight() / 3, paint);
        }else if (orientations[1] <= 0) {
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 2560 - (1280 - 7 * orientations [2]), paint);
        }

        if (orientations[2] >= 0) {
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 3, getHeight() / 2, paint);
        }else if (orientations[2] <= 0) {

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
