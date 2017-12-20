package com.nutron.coordinatorbehavior.presentation.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutron.coordinatorbehavior.R
import kotlinx.android.synthetic.main.item_layout.view.*


class MenuAdapter(val data: List<MenuData>, val listener: (data: MenuData) -> Unit): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener(data[adapterPosition])
                }
            }
        }

        fun bind(item: MenuData) {
            view.itemTitleTextView.text = item.title
        }

    }
}