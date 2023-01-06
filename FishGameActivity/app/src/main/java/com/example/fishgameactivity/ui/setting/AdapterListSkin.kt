package com.example.fishgameactivity.ui.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fishgameactivity.databinding.ItemSkinBinding
import com.example.fishgameactivity.model.Skin
import com.example.fishgameactivity.utils.ListSkin

class AdapterListSkin() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<Skin>()
    lateinit var interfaceClickSkin: IclickSkin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        data = ListSkin.listSkin
        var binding = ItemSkinBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,interfaceClickSkin)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bind(position)
        }
    }

    override fun getItemCount(): Int = ListSkin.listSkin.size

    inner class ViewHolder(private val binding: ItemSkinBinding, val IonClick:IclickSkin): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.imvSkin.setImageResource(data[position].id)
            binding.imvSkin.setOnClickListener{
                IonClick.onClickSkin(position)
            }
        }
    }
    interface IclickSkin{
        fun onClickSkin(position: Int)
    }
}
