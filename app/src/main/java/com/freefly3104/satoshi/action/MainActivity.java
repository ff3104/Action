package com.freefly3104.satoshi.action;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements GameView.Callback{

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        gameView.setCallback(this);
        setContentView(gameView);

    }

    @Override
    public void onGameOver() {
        Toast.makeText(this,"Game Over",Toast.LENGTH_LONG).show();
    }
}
