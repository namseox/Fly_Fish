package com.example.fishgameactivity.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class BitmapUtils(var context: Context) {
    fun loadBitmap(id: Int, width: Float, heigh: Float): Bitmap? {
        var bm = BitmapFactory.decodeResource(context.resources, id)
        bm = Bitmap.createScaledBitmap(bm!!, width.toInt(), heigh.toInt(), true)
        if (bm == null) {
            println("load bitmap failed")
        }
        return bm
    }

    fun cropBitmap(bitmap: Bitmap?, x: Int, y: Int, width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(bitmap!!, x, y, width, height)
    }

    fun scaleBitmap(bitmap: Bitmap?, newWidth: Int, newHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap!!, newWidth, newHeight, false)
    }
}