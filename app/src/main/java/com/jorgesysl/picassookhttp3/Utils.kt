package com.jorgesysl.picassookhttp3

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.lang.NumberFormatException

class Utils private constructor() {


    companion object{
        fun loadImage(
            iv: ImageView?,
            urlImage: String?
        ) {
            if (iv == null || urlImage.isNullOrEmpty()) {
                Log.e(TAG, "loadImage() imageview null or empty url.")
                return
            }else{
                Log.d(TAG, "loadImage() loading url: $urlImage")
            }
            Log.i(TAG, "urlImage: $urlImage")
            val rc = Picasso.get().load(urlImage)
            rc.error(android.R.drawable.presence_offline)
            rc.into(iv)
        }

        fun isNumeric(number: String?): Boolean {
            var result = false
            try {
                if (number != null) {
                    number.toDouble()
                    result = true
                }
            } catch (nfe: NumberFormatException) {
                Log.w(TAG, "NFException value: $number")
            }
            return result
        }

        private const val TAG = "PicassoOkHttp3"

    }

}