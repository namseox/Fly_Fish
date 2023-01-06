package com.example.fishgameactivity

import androidx.appcompat.app.AppCompatActivity
import com.example.fishgameactivity.FlyingFishView
import android.os.Bundle
import java.util.Timer
import java.util.TimerTask
import java.lang.Runnable
import com.example.fishgameactivity.MainActivity
import com.example.fishgameactivity.callback.callback.CallBackDirect
import android.annotation.SuppressLint
import android.content.Intent
import com.example.fishgameactivity.GameOverActivity
import android.view.Window
import android.view.WindowManager
import android.content.pm.ActivityInfo
import android.os.Handler
import android.util.Log
import com.example.fishgameactivity.model.Setting
import com.example.fishgameactivity.model.Skin
import com.example.fishgameactivity.utils.SharedPreferenceUtils

class MainActivity : AppCompatActivity() {
    private var gameView: FlyingFishView? = null
    private val handler = Handler()
    private lateinit var skin : Skin
    private var volum : Int = 50
    private var bg_volum : Int = 50
    private lateinit var sharedPreferenceUtils : SharedPreferenceUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        val bundle = intent.extras
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(this)
        skin = sharedPreferenceUtils.getObjModel<Setting>()!!.skin
        volum = sharedPreferenceUtils.getObjModel<Setting>()!!.sound
        bg_volum = sharedPreferenceUtils.getObjModel<Setting>()!!.music
        gameView = FlyingFishView(this, bundle!!.getString("name", ""),skin,volum)
        setContentView(gameView)
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post { gameView!!.invalidate() }
            }
        }, 0, Interval)
        gameView!!.setOnClickListener { score, gameOver, name ->
            @SuppressLint("DrawAllocation") val intent =
                Intent(this@MainActivity, GameOverActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val bundle = Bundle()
            bundle.putString("score", score)
            bundle.putString("timeup", gameOver)
            bundle.putString("name", name)
            Log.d("nnn9",name);
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun setupWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    companion object {
        private const val Interval: Long = 30
    }
}