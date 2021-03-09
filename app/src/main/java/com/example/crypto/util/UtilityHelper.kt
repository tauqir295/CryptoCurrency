package com.example.crypto.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import java.util.*

/**
 * method for loading the image from url in the image view
 * @param - [ImageView] - the view on which image will be loaded
 * @param - [url] - path in the server where image is present
 */
@BindingAdapter("android:src")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
            val imageLoader = ImageLoader.Builder(view.context)
                    .componentRegistry {
                        add(SvgDecoder(view.context))
                    }
                    .build()
            val request = LoadRequest.Builder(view.context)
                    .data(it)
                    .target(view)
                    .build()
            imageLoader.execute(request)
        } else {
            view.load(it)
        }
    }

}