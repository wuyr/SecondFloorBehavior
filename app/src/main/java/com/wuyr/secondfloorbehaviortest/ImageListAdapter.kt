package com.wuyr.secondfloorbehaviortest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_list_view.view.*
import kotlin.math.roundToInt

/**
 * @author wuyr
 * @github https://github.com/wuyr/ArrowDrawable
 * @since 2019-06-30 下午8:24
 */
class ImageListAdapter(
    private var context: Context
) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var data = arrayOf(
        decode(R.drawable.ic_0), decode(R.drawable.ic_1),
        decode(R.drawable.ic_2), decode(R.drawable.ic_3),
        decode(R.drawable.ic_4), decode(R.drawable.ic_5),
        decode(R.drawable.ic_6), decode(R.drawable.ic_7),
        decode(R.drawable.ic_8), decode(R.drawable.ic_9),
        decode(R.drawable.ic_10), decode(R.drawable.ic_11),
        decode(R.drawable.ic_12), decode(R.drawable.ic_13),
        decode(R.drawable.ic_14), decode(R.drawable.ic_1)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(layoutInflater.inflate(R.layout.item_image_list_view, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(data[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.image
    }

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

    private fun decode(resId: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, resId, options)
        options.inSampleSize = calculateInSampleSize(options)
        options.inJustDecodeBounds = false
        return try {
            BitmapFactory.decodeResource(context.resources, resId, options)
        } catch (e: Exception) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
        }
    }
}