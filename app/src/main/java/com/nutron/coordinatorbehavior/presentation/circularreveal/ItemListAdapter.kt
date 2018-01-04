package com.nutron.coordinatorbehavior.presentation.circularreveal

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutron.coordinatorbehavior.R
import com.nutron.coordinatorbehavior.data.entity.MenuData
import kotlinx.android.synthetic.main.item_layout.view.*


class ItemListAdapter(val data: List<MenuData>,
                      val listener: (view: View, data: MenuData) -> Unit)
    : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_image_title_layout, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener(view.findViewById(R.id.roundedImage), data[adapterPosition])
                }
            }
        }

        fun bind(item: MenuData) {
            view.itemTitleTextView.text = item.title
        }

    }
}