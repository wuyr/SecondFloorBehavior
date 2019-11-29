package com.wuyr.secondfloorbehaviortest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_text.view.*

/**
 * @author wuyr
 * @github https://github.com/wuyr/ArrowDrawable
 * @since 2019-06-30 下午8:24
 */
class TextListAdapter(
    context: Context, private var data: MutableList<String>
) : RecyclerView.Adapter<TextListAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private val header = context.decode(R.drawable.ic_15)

    fun setData(vararg dataList: String) {
        data.clear()
        data.addAll(dataList.toList())
        notifyItemRangeChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        layoutInflater.inflate(
            if (viewType == 0) R.layout.item_image
            else R.layout.item_text, parent, false
        )
    )

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.imageView?.setImageBitmap(header)
        } else {
            holder.textView?.text = data[position]
        }
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = itemView.image
        var textView: TextView? = itemView.textView
    }

}