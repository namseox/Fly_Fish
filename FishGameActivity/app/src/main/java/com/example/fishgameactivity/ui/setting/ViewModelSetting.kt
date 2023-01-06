package com.example.fishgameactivity.ui.setting

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.fishgameactivity.R
import com.example.fishgameactivity.model.Setting
import com.example.fishgameactivity.model.Skin
import com.example.fishgameactivity.utils.SharedPreferenceUtils

class ViewModelSetting(val app : AppCompatActivity) : ViewModel() {
    private val sharedPreferenceUtils = SharedPreferenceUtils.getInstance(app)
    lateinit var setting : Setting

    fun getData(): Setting{
        if (sharedPreferenceUtils.getObjModel<Setting>() == null){
            setting = Setting(50,50, Skin(R.drawable.fish24))
        }else{
        setting = sharedPreferenceUtils.getObjModel<Setting>()!!
        }
        return setting
    }

    fun saveData(setting: Setting){
        sharedPreferenceUtils.setObjModel(setting)
    }

}