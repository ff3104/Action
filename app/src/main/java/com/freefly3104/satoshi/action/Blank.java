package com.freefly3104.satoshi.action;

import android.graphics.Canvas;

/**
 * Created by satoshi on 2015/06/05.
 */
public class Blank extends Ground {

    public Blank(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
