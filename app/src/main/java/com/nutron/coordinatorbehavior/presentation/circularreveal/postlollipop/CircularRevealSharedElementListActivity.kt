package com.nutron.coordinatorbehavior.presentation.circularreveal.postlollipop

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nutron.coordinatorbehavior.R
import com.nutron.coordinatorbehavior.data.entity.MenuData
import com.nutron.coordinatorbehavior.presentation.circularreveal.ItemListAdapter
import kotlinx.android.synthetic.main.activity_circular_reveal_shared_element_list.*

class CircularRevealSharedElementListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_reveal_shared_element_list)
        initView()

    }

    private fun initView() {
        val layoutManger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManger
        recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManger.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val adapter = ItemListAdapter(getMenuData()) { view, data -> handleOnClick(view, data) }
        recyclerView.adapter = adapter

    }

    private fun handleOnClick(view: View, data: MenuData) {
        Intent(this, CircularRevealSharedElementActivity::class.java).apply {
            val transition = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@CircularRevealSharedElementListActivity,
                    view, getString(R.string.rounded_color_view_transition_name))

            startActivity(this, transition.toBundle())
        }
    }


    private fun getMenuData(): List<MenuData> {
        return arrayListOf(
                MenuData(id = 1, title = "Item 1"),
                MenuData(id = 2, title = "Item 2"),
                MenuData(id = 3, title = "Item 3"),
                MenuData(id = 4, title = "Item 4"),
                MenuData(id = 5, title = "Item 5"),
                MenuData(id = 6, title = "Item 6"),
                MenuData(id = 7, title = "Item 7"),
                MenuData(id = 8, title = "Item 8"),
                MenuData(id = 9, title = "Item 9"),
                MenuData(id = 10, title = "Item 10"),
                MenuData(id = 11, title = "Item 11"))
    }
}
