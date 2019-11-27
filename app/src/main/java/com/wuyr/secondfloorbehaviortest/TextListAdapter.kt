package com.wuyr.secondfloorbehaviortest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_text_list_view.view.*

/**
 * @author wuyr
 * @github https://github.com/wuyr/ArrowDrawable
 * @since 2019-06-30 下午8:24
 */
class TextListAdapter(
    context: Context, private var data: MutableList<String>
) : RecyclerView.Adapter<TextListAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    fun setData(vararg dataList: String) {
        data.clear()
        data.addAll(dataList.toList())
        notifyItemRangeChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(layoutInflater.inflate(R.layout.item_text_list_view, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.textView
    }

}