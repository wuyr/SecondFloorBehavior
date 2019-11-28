package com.wuyr.secondfloorbehaviortest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.roundToInt

/**
 * @author wuyr
 * @github https://github.com/wuyr/
 * @since 2019-11-29 上午1:02
 */
private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
    val reqWidth = 360
    val reqHeight = 480
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val heightRatio = (height / reqHeight.toFloat()).roundToInt()
        val widthRatio = (width / reqWidth.toFloat()).roundToInt()
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}

fun Context.decode(resId: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, resId, options)
    options.inSampleSize = calculateInSampleSize(options)
    options.inJustDecodeBounds = false
    return try {
        BitmapFactory.decodeResource(resources, resId, options)
    } catch (e: Exception) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
    }
}