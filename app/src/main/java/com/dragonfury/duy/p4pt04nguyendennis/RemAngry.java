package com.dragonfury.duy.p4pt04nguyendennis;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * Created by Duy on 6/12/2017.
 */

public class RemAngry extends RectF {

    public RemAngry(float left, float top, float right, float bottom, Bitmap bitmap) {
        super(left, top, right, bottom);
        angryRem = bitmap;
    }

    Bitmap angryRem;
}
