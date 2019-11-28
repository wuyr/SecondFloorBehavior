package com.wuyr.secondfloorbehaviortest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_list_view.view.*

/**
 * @author wuyr
 * @github https://github.com/wuyr/ArrowDrawable
 * @since 2019-06-30 下午8:24
 */
class ImageListAdapter(context: Context) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var data = context.run {
        arrayOf(
            decode(R.drawable.ic_0), decode(R.drawable.ic_1),
            decode(R.drawable.ic_2), decode(R.drawable.ic_3),
            decode(R.drawable.ic_4), decode(R.drawable.ic_5),
            decode(R.drawable.ic_6), decode(R.drawable.ic_7),
            decode(R.drawable.ic_8), decode(R.drawable.ic_9),
            decode(R.drawable.ic_10), decode(R.drawable.ic_11),
            decode(R.drawable.ic_12), decode(R.drawable.ic_13),
            decode(R.drawable.ic_14), decode(R.drawable.ic_7)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(layoutInflater.inflate(R.layout.item_image_list_view, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(data[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.image
    }
}