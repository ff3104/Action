package com.freefly3104.satoshi.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by satoshi on 2015/05/28.
 */
public class GameView extends SurfaceView implements Droid.Callback, SurfaceHolder.Callback{

    private static final long EPS = 60;
    private static final int GROUND_MOVE_TO_LEFT = 10;
    private static final int MAX_TOUCH_TIME = 500; // m sec
    private static final int START_GROUND_HEIGHT = 50;
    private static final Paint PAINT = new Paint();
    private Ground ground;
    private Droid droid;
    private DrawThread drawThread;
    private long touchDownStartTime;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void drawGame(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawColor(Color.WHITE);

        if(droid == null){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.droid);
            droid = new Droid(bitmap, 0, 0, this);
        }

        if(ground == null){
            ground = new Ground(0, height - START_GROUND_HEIGHT, width, height);
        }

        droid.move();
        droid.draw(canvas);

        ground.move(GROUND_MOVE_TO_LEFT);
        ground.draw(canvas);

        //invalidate(); // 描画の繰り返し
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchDownStartTime = System.currentTimeMillis(); // 発生した時間を保持
                return true;
            case MotionEvent.ACTION_UP:
                jumpDroid();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void jumpDroid(){
        // 指が触れていた時間を計測
        float time = System.currentTimeMillis() - touchDownStartTime;

        // 地面に触れていないなら、ジャンプしない
        if(getDistanceFromGround(droid) != 0){
            return;
        }

        // MAX_TOUCH_TIMEを上限とする MAX_TOUCH_TIMEを超えていたら、MAX_TOUCH_TIMEとする
        if(time > MAX_TOUCH_TIME){
            time = MAX_TOUCH_TIME;
        }

        droid.jump(time / MAX_TOUCH_TIME);

    }

    @Override
    public int getDistanceFromGround(Droid droid) {

        // 落下を続ける droidが地面上にいないなら
        boolean horizontal = !(droid.rect.left >= ground.rect.right || droid.rect.right <= ground.rect.left);
        if(!horizontal){
            return Integer.MAX_VALUE;
        }

        return ground.rect.top - droid.rect.bottom;
    }


    private class DrawThread extends Thread{
        boolean isFinished;

        @Override
        public void run() {
            SurfaceHolder holder = getHolder();

            while(!isFinished){
                Canvas canvas = holder.lockCanvas(); // canvasオブジェクトを取得
                if(canvas != null){
                    drawGame(canvas);
                    holder.unlockCanvasAndPost(canvas); // 実際に画面に反映
                }

                try{
                    sleep(1000 / EPS); // 描画を少しゆっくりにする処理
                }catch (InterruptedException e){
                }
            }
            super.run();
        }
    }

    public void startDrawThread(){
        stopDrawThread();

        drawThread = new DrawThread();
        drawThread.start();
    }

    public boolean stopDrawThread(){
        if(drawThread == null){
            return false;
        }
        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }

}
