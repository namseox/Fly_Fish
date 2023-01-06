package com.example.fishgameactivity

import androidx.appcompat.app.AppCompatActivity
import com.example.fishgameactivity.dialog.ShowDialog
import android.os.Bundle
import android.view.Gravity
import android.content.Intent
import com.example.fishgameactivity.GameOverActivity
import com.example.fishgameactivity.SettingActivity
import com.example.fishgameactivity.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private var binding: ActivityStartBinding? = null
    private val showDialog = ShowDialog(this@StartActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.rlt.setOnClickListener { showDialog.openDialogName(Gravity.CENTER) }
        binding!!.cv.setOnClickListener {
            val intent = Intent(this@StartActivity, GameOverActivity::class.java)
            val bundle = Bundle()
            bundle.putString("timeup", "no")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding!!.setting.setOnClickListener {
            val intent = Intent(this@StartActivity, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}