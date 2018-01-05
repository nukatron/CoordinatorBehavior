package com.nutron.coordinatorbehavior.extension

import android.net.Uri
import android.support.annotation.DrawableRes
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo


fun View.getCenter() : Pair<Float, Float> {
    val cx = this.x + this.measuredWidth / 2
    val cy = this.y //+ this.measuredHeight / 2
    return Pair(cx, cy)
}

fun SimpleDraweeView.setImageSrc(@DrawableRes id: Int, listener: ControllerListener<ImageInfo>? = null) {

    val uri = Uri.parse("res:///" + id)
    val controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setTapToRetryEnabled(true)
            .setOldController(this.controller)
            .setControllerListener(listener)
            .build()
    this.controller = controller

}

