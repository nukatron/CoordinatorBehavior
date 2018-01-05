package com.nutron.coordinatorbehavior.presentation.circularreveal

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.nutron.coordinatorbehavior.R
import com.nutron.coordinatorbehavior.data.entity.ImageTitleData
import com.nutron.coordinatorbehavior.extension.setImageSrc
import kotlinx.android.synthetic.main.item_image_title_layout.view.*


class ItemListAdapter(val data: List<ImageTitleData>,
                      val listener: (view: View, data: ImageTitleData) -> Unit)
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

        fun bind(item: ImageTitleData) {
            Log.d("DEBUGX", "item.id: ${item.id}")
            view.roundedImage.setImageSrc(item.id)
            view.itemTitleTextView.text = item.title
        }

    }
}