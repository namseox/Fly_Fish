package com.example.fishgameactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.example.fishgameactivity.utils.BitmapUtils;
import com.google.gson.Gson;

import com.example.fishgameactivity.callback.callback;
import com.example.fishgameactivity.dialog.ShowDialog;
import com.example.fishgameactivity.model.Skin;
import com.example.fishgameactivity.model.UserHighScore;
import com.example.fishgameactivity.utils.SharedPreferenceUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FlyingFishView extends View {
    private Context context;
    private String name;
    private ShowDialog showDialog;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    Skin skin;
    Float volum;
    private Boolean isLevel1 = false;
    private Boolean isLevel2 = false;
    private Boolean isLevel3 = false;

    private CountDownTimer countDownTimer;

    private Bitmap fish[] = new Bitmap[4]; //SET CÁ, TÙY THUỘC VÀO KÍCH CỠ HÌNH (1)
    private Bitmap fishDishes[] = new Bitmap[5]; //SET CÁ DISH, TÙY THUỘC VÀO KÍCH CỠ HÌNH (1)
    private Bitmap ocean; //SET background, TÙY THUỘC VÀO KÍCH CỠ HÌNH (1)

    private Boolean isShowLevelUp = false;
    private Bitmap levelUp; //SET levelUp, TÙY THUỘC VÀO KÍCH CỠ HÌNH (1)

    private Paint scorePaint = new Paint(); //SET VẼ ĐIỂM (1)
    private Paint timePaint = new Paint(); //SET VẼ TIME (1)

//    private Bitmap life[] = new Bitmap[2]; //SET heart, TÙY THUỘC VÀO KÍCH CỠ HÌNH (1)

    private int fishX = 100;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth, canvasHeight;

    private boolean touch = false;

    private int fishDishesFirstX, fishDishesFirstY, fishDishesFirstSpeed = 16;
    private Paint fishDishesFirstPaint = new Paint();

    private int fishDishesSecondX, fishDishesSecondY, fishDishesSecondSpeed = 20;
    private Paint fishDishesSecondPaint = new Paint();

    private int fishDishesThirdX, fishDishesThirdY, fishDishesThirdSpeed = 12;
    private Paint fishDishesThirdPaint = new Paint();

    private int fishDishesThirdX2, fishDishesThirdY2, fishDishesThirdSpeed2 = 14;
    private Paint fishDishesThirdPaint2 = new Paint();

    private int fishDishesForthX, fishDishesForthY, fishDishesForthSpeed = 9;
    private Paint fishDishesForthPaint = new Paint();

    private int score;
    private int time;

    private int soundEat, soundDie;
    private SoundPool soundPool;
    private boolean loadedsound;

    public callback.CallBackDirect callBackLoadBill;

    public void setOnClickListener(callback.CallBackDirect callBackLoadBill) {
        this.callBackLoadBill = callBackLoadBill;
    }

    public FlyingFishView(Context context, String name, Skin skin, int volum) {
        super(context);
        this.context = context;
        this.name = name;
        Log.d("abc",volum+"");
        this.volum = volum/100f;
        Log.d("abc",this.volum+"xxx");
        showDialog = new ShowDialog(context);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        this.skin = skin;
        BitmapUtils bitmapUtil = new BitmapUtils(context);

        fish[0] = bitmapUtil.loadBitmap(skin.getId(), 64, 64); //SET CÁ SIZE 32, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fishn32); //SET CÁ, TÙY THUỘC VÀO KÍCH CỠ HÌNH (3)
        fish[2] = bitmapUtil.loadBitmap(skin.getId(), 128, 128);  //SET CÁ SIZE 64
        fish[3] = bitmapUtil.loadBitmap(skin.getId(), 192, 192);
        ; //SET CÁ SIZE 128
        fishDishes[0] = bitmapUtil.loadBitmap(R.drawable.fishdishesx48, 64, 64); //SET CÁ DISHES, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
        fishDishes[1] = bitmapUtil.loadBitmap(R.drawable.fishdishesx24, 64, 64); //SET CÁ DISHES, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
        fishDishes[2] = bitmapUtil.loadBitmap(R.drawable.fishdishes32, 128, 128); //SET CÁ DISHES, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
        fishDishes[4] = bitmapUtil.loadBitmap(R.drawable.fishdishesx32, 128, 128); //SET CÁ DISHES, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
        fishDishes[3] = bitmapUtil.loadBitmap(R.drawable.fishdishesx48, 192, 192); //SET CÁ SIZE 64
        ocean = BitmapFactory.decodeResource(getResources(), R.drawable.oceanlanscape); //SET background, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
//        levelUp = BitmapFactory.decodeResource(getResources(), R.drawable.levelup64); //SET levelUp (2)

        fishDishesFirstPaint.setColor(Color.YELLOW);
        fishDishesFirstPaint.setAntiAlias(false);

        fishDishesSecondPaint.setColor(Color.GREEN);
        fishDishesSecondPaint.setAntiAlias(false);

        fishDishesThirdPaint.setColor(Color.BLUE);
        fishDishesThirdPaint.setAntiAlias(false);

        fishDishesForthPaint.setColor(Color.BLACK);
        fishDishesForthPaint.setAntiAlias(false);

        fishDishesThirdPaint2.setColor(Color.BLACK);
        fishDishesThirdPaint2.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE); //SET VẼ ĐIỂM (2)
        scorePaint.setTextSize(50); //SET VẼ ĐIỂM (3)
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD); //SET VẼ ĐIỂM (4)
        scorePaint.setAntiAlias(true); //SET VẼ ĐIỂM (5)

        timePaint.setColor(Color.WHITE); //SET VẼ TIME (2)
        timePaint.setTextSize(50); //SET VẼ TIME (3)
        timePaint.setTypeface(Typeface.DEFAULT_BOLD); //SET VẼ TIME (4)
        timePaint.setAntiAlias(true); //SET VẼ TIME (5)

//        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart24); //SET heart, TÙY THUỘC VÀO KÍCH CỠ HÌNH (2)
//        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_gray24); //SET heart, TÙY THUỘC VÀO KÍCH CỠ HÌNH (3)

        fishY = 250;
        score = 0;
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedsound = true;
            }

        });


//        soundPool.setVolume(soundEat,0,0);
//        soundPool.setVolume(soundDie,0,0);
        soundEat = this.soundPool.load(context, R.raw.eat, 1);
        soundDie = this.soundPool.load(context, R.raw.die, 1);


        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = (int) (millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                @SuppressLint("DrawAllocation") Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("score", String.valueOf(score));
                bundle.putString("timeup", "GAME OVER");
                bundle.putString("name", name);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
                ref.child(name).setValue(new UserHighScore(name, String.valueOf(score)));
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(ocean, 0, 0, null); //SET background, TÙY THUỘC VÀO KÍCH CỠ HÌNH (3)

        canvasWidth = getWidth();
        canvasHeight = getHeight();
        //set con thứ 1
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        //set con thứ 2
        int minFishY2 = fish[1].getHeight();
        int maxFishY2 = canvasHeight - fish[1].getHeight() * 3;
        //set con thứ 3
        int minFishY3 = fish[2].getHeight();
        int maxFishY3 = canvasHeight - fish[2].getHeight() * 3;
        //set con thứ 4
        int minFishY4 = fish[3].getHeight();
        int maxFishY4 = canvasHeight - fish[3].getHeight() * 3;
        fishY = fishY + fishSpeed;
        if (fishY < minFishY) { //SET CÁ KHÔNG VƯỢT QUÁ VERTICAL MÀN HÌNH BÊN DƯỚI (ĐÁY)
            fishY = minFishY;
        }
        if (fishY > maxFishY) { //SET CÁ KHÔNG VƯỢT QUÁ VERTICAL MÀN HÌNH BÊN TRÊN
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;
        if (touch && score < 50) {
            canvas.drawBitmap(fish[0], fishX, fishY, null); //NẾU CHẠM VÀO MÀN HÌNH VÀ SỐ ĐIỂM <= 100 SẼ SET CÁ fish[1]
            touch = false;
            isLevel1 = true;
            isLevel2 = false;
            isLevel3 = false;
        } else if (!touch && score < 50) {
            canvas.drawBitmap(fish[0], fishX, fishY, null); //KHÔNG CHẠM VÀO MÀN HÌNH VÀ SỐ ĐIỂM <= 100 SẼ SET CÁ fish[0]
        } else if (touch && score >= 50 && score < 250) { // && score <= 200
            canvas.drawBitmap(fish[2], fishX, fishY, null); //NẾU CHẠM VÀO MÀN HÌNH VÀ 50 <= SỐ ĐIỂM <= 250 SẼ SET CÁ fish[2]
            touch = false;
            isLevel1 = false;
            isLevel2 = true;
            isLevel3 = false;
        } else if (!touch && score >= 50 && score < 250) { // && score <= 200
            canvas.drawBitmap(fish[2], fishX, fishY, null); //NẾU CHẠM VÀO MÀN HÌNH VÀ 50 <= SỐ ĐIỂM <= 250 SẼ SET CÁ fish[2]
        } else if (touch && score >= 250) { // && score <= 200
            canvas.drawBitmap(fish[3], fishX, fishY, null); //NẾU CHẠM VÀO MÀN HÌNH VÀ SỐ ĐIỂM >= 250 SẼ SET CÁ fish[2]
            touch = false;
            isLevel1 = false;
            isLevel2 = false;
            isLevel3 = true;
        } else if (!touch && score >= 250) { // && score <= 200
            canvas.drawBitmap(fish[3], fishX, fishY, null); //NẾU CHẠM VÀO MÀN HÌNH VÀ SỐ ĐIỂM >= 250 SẼ SET CÁ fish[2]
        }

        //SET CÁ DISHES CON THỨ 3
        fishDishesThirdX = fishDishesThirdX - fishDishesThirdSpeed;

        if (hitBallChecker(fishDishesThirdX, fishDishesThirdY)) {
            fishDishesThirdX = -100;
            if (score >= 50) {
                int streamId = this.soundPool.play(this.soundEat, (float) volum, (float) volum, 1, 0, 1f);
                score = score + 20;
            } else {
                int streamId = this.soundPool.play(this.soundDie, (float) volum, (float) volum, 1, 0, 1f);
                countDownTimer.cancel();
                callBackLoadBill.OnItemDirect(String.valueOf(score), "GAME OVER", name);
                ref.child(name).setValue(new UserHighScore(name, String.valueOf(score)));
            }
        }
        if (fishDishesThirdX < 0) {
            fishDishesThirdX = canvasWidth + 21;
            fishDishesThirdY = (int) Math.floor(Math.random() * (maxFishY3 - minFishY3)) + minFishY3;
        }
        canvas.drawBitmap(fishDishes[2], fishDishesThirdX, fishDishesThirdY, fishDishesThirdPaint); //SET CÁ DISHES (3)

        //SET CÁ DISHES CON THỨ 3.2
        fishDishesThirdX2 = fishDishesThirdX2 - fishDishesThirdSpeed2;
        if (hitBallChecker(fishDishesThirdX2, fishDishesThirdY2)) {
            fishDishesThirdX2 = -100;
            if (score >= 50) {
                int streamId = this.soundPool.play(this.soundEat, (float) volum, (float) volum, 1, 0, 1f);
                score = score + 20;
            } else {
                int streamId = this.soundPool.play(this.soundDie, (float) volum, (float) volum, 1, 0, 1f);
                countDownTimer.cancel();
                callBackLoadBill.OnItemDirect(String.valueOf(score), "GAME OVER", name);
                ref.child(name).setValue(new UserHighScore(name, String.valueOf(score)));

            }
        }
        if (fishDishesThirdX2 < 0) {
            fishDishesThirdX2 = canvasWidth + 21;
            fishDishesThirdY2 = (int) Math.floor(Math.random() * (maxFishY3 - minFishY3)) + minFishY3;
        }
        canvas.drawBitmap(fishDishes[4], fishDishesThirdX2, fishDishesThirdY2, fishDishesThirdPaint2); //SET CÁ DISHES (3)

        if (score >= 50) {
            //SET CÁ DISHES CON THỨ 4
            fishDishesForthX = fishDishesForthX - fishDishesForthSpeed;
            if (hitBallChecker(fishDishesForthX, fishDishesForthY)) {
                fishDishesForthX = -100;
                if (score >= 250) {
                    int streamId = this.soundPool.play(this.soundEat, (float) volum, (float) volum, 1, 0, 1f);
                    score = score + 50;
                } else {
                    int streamId = this.soundPool.play(this.soundDie, (float) volum, (float) volum, 1, 0, 1f);
                    countDownTimer.cancel();
                    @SuppressLint("DrawAllocation") Intent intent = new Intent(getContext(), GameOverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("score", String.valueOf(score));
                    bundle.putString("timeup", "GAME OVER");
                    bundle.putString("name", name);
                    Log.d("nnn999", name);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                    ref.child(name).setValue(new UserHighScore(name, String.valueOf(score)));

                }
            }
            if (fishDishesForthX < 0) {
                fishDishesForthX = canvasWidth + 21;
                fishDishesForthY = (int) Math.floor(Math.random() * (maxFishY4 - minFishY4)) + minFishY4;
            }
            canvas.drawBitmap(fishDishes[3], fishDishesForthX, fishDishesForthY, fishDishesForthPaint); //SET CÁ DISHES (3)
        }

        //SET CÁ DISHES CON THỨ 1
        fishDishesFirstX = fishDishesFirstX - fishDishesFirstSpeed;
        if (hitBallChecker(fishDishesFirstX, fishDishesFirstY)) {
            int streamId = this.soundPool.play(this.soundEat, (float) volum, (float) volum, 1, 0, 1f);
            score = score + 10;
            fishDishesFirstX = -100;
        }
        if (fishDishesFirstX < 0) {
            fishDishesFirstX = canvasWidth + 21;
            fishDishesFirstY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawBitmap(fishDishes[0], fishDishesFirstX, fishDishesFirstY, fishDishesFirstPaint); //SET CÁ DISHES (3)

        //SET CÁ DISHES CON THỨ 2
        fishDishesSecondX = fishDishesSecondX - fishDishesSecondSpeed;
        if (hitBallChecker(fishDishesSecondX, fishDishesSecondY)) {
            int streamId = this.soundPool.play(this.soundEat, (float) volum, (float) volum, 1, 0, 1f);
            score = score + 10;
            fishDishesSecondX = -100;
        }
        if (fishDishesSecondX < 0) {
            fishDishesSecondX = canvasWidth + 21;
            fishDishesSecondY = (int) Math.floor(Math.random() * (maxFishY2 - minFishY2)) + minFishY2;
        }
        canvas.drawBitmap(fishDishes[1], fishDishesSecondX, fishDishesSecondY, fishDishesFirstPaint); //SET CÁ DISHES (3)

        canvas.drawText("Score: " + score, 20, 60, scorePaint); //SET VẼ ĐIỂM (6)

        canvas.drawText("Time: " + time + "s", canvasWidth - 300, 60, timePaint); //SET VẼ TIME (6)
    }

    public boolean hitBallChecker(int x, int y) {
        if (isLevel1) {
            if (((fishX < x && x < (fishX + fish[0].getWidth())) || (fishX > x && fishX < x + fish[0].getWidth())) && fishY < y && y < (fishY + fish[0].getHeight())) {
                return true;
            }
        } else if (isLevel2) {
            if (((fishX < x && x < (fishX + fish[2].getWidth())) || (fishX > x && fishX < x + fish[2].getWidth())) && fishY < y && y < (fishY + fish[2].getHeight())) {
                return true;
            }
        } else if (isLevel3) {
            if (((fishX < x && x < (fishX + fish[3].getWidth())) || (fishX > x && fishX < x + fish[3].getWidth())) && fishY < y && y < (fishY + fish[3].getHeight())) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }

}
