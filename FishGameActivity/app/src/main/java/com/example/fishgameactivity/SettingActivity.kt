package com.example.fishgameactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnScrollChangeListener
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fishgameactivity.databinding.ActivitySettingBinding
import com.example.fishgameactivity.model.Setting
import com.example.fishgameactivity.ui.setting.AdapterListSkin
import com.example.fishgameactivity.ui.setting.ViewModelSetting
import com.example.fishgameactivity.utils.ListSkin

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    lateinit var adapter: AdapterListSkin
    lateinit var setting: Setting
    lateinit var viewModelSetting: ViewModelSetting
    var position :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set seebar
        binding.sbMusic.min = 0
        binding.sbSound.min = 0
        binding.sbMusic.max = 100
        binding.sbSound.max = 100


        //khoi tao gia tri dau
        viewModelSetting = ViewModelSetting(this)
        setting = viewModelSetting.getData()
        adapter = AdapterListSkin()
        val manager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        Log.d("nnn1",setting.skin.id.toString() )
        binding.sbSound.progress = setting.sound
        binding.sbMusic.progress = setting.music

        //recycview
        binding.rcvSkin.adapter = adapter
        binding.rcvSkin.layoutManager = manager
        for (s in ListSkin.listSkin){
            if (s==setting.skin){
                break
            }
            position += 1
        }
        binding.rcvSkin.scrollToPosition(position)


        adapter.interfaceClickSkin = object : AdapterListSkin.IclickSkin{
            override fun onClickSkin(position: Int) {
                setting.skin = ListSkin.listSkin.get(position)
            }
        }
        binding.sbMusic.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setting.music = binding.sbMusic.progress
//                setting.sound = binding.sbSound.progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                setting.music = binding.sbMusic.progress
//                setting.sound = binding.sbSound.progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                setting.music = binding.sbMusic.progress
//                setting.sound = binding.sbSound.progress
            }


        })
        binding.sbSound.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                setting.music = binding.sbMusic.progress
                setting.sound = binding.sbSound.progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                setting.music = binding.sbMusic.progress
                setting.sound = binding.sbSound.progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                setting.music = binding.sbMusic.progress
                setting.sound = binding.sbSound.progress
            }

        })

        binding.imvExit.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                var intent = Intent(this@SettingActivity, StartActivity::class.java)
                viewModelSetting.saveData(setting)
                startActivity(intent)
            }

        })
    }


}