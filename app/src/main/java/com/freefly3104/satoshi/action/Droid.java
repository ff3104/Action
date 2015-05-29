package com.freefly3104.satoshi.action;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by satoshi on 2015/05/29.
 */
public class Droid {
    private final Paint paint = new Paint();
    private Bitmap bitmap;
    private final Rect rect;

    public Droid(Bitmap bitmap, int left, int top){
        this.bitmap = bitmap;
        this.rect = new Rect(left, top, bitmap.getWidth(), bitmap.getHeight());
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, rect.left, rect.top, paint);
    }
}
