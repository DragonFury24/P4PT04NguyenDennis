package com.dragonfury.duy.p4pt04nguyendennis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by duyde on 6/16/2017.
 */

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
    float orientations[] = new float[3];
    SensorActivity sensorActivity = new SensorActivity();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        orientations = sensorActivity.getOrientation();
        for (float orientation : orientations) {
            System.out.println("ori " + orientation);
        }
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
