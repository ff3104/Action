package com.freefly3104.satoshi.action;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements GameView.Callback{

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        gameView.setCallback(this);
        setContentView(gameView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.startDrawThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stopDrawThread();
    }

    @Override
    public void onGameOver() {
        Toast.makeText(this,"Game Over",Toast.LENGTH_LONG).show();
    }
}
