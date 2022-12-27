package com.example.fishgameactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.fishgameactivity.callback.callback;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView gameView;
    private Handler handler = new Handler();
    private final static long Interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindow();
        Bundle bundle = getIntent().getExtras();
        gameView = new FlyingFishView(this, bundle.getString("name", ""));
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        }, 0, Interval);

        gameView.setOnClickListener(new callback.CallBackDirect() {
            @Override
            public void OnItemDirect(String score, String gameOver, String name) {
                @SuppressLint("DrawAllocation") Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("score", score);
                bundle.putString("timeup", gameOver);
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    void setupWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}