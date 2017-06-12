package com.dragonfury.duy.p4pt04nguyendennis;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by Duy on 6/12/2017.
 */

public class RemHappy extends RectF {

    public RemHappy(float left, float top, float right, float bottom, Bitmap bitmap) {
        super(left, top, right, bottom);
        happyRem = bitmap;

    }

    Bitmap happyRem;
}
