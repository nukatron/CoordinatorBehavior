package com.nutron.coordinatorbehavior.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.nutron.coordinatorbehavior.R
import com.nutron.coordinatorbehavior.data.entity.MenuData
import com.nutron.coordinatorbehavior.presentation.circularreveal.postlollipop.CircularRevealSharedElementListActivity
import com.nutron.coordinatorbehavior.presentation.floatingbottom.FloatingBottomActivity
import com.nutron.coordinatorbehavior.presentation.scrolling.ScrollingActivity
import com.nutron.coordinatorbehavior.presentation.scrollingprofile.ScrollingProfileActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val layoutManger = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManger
        recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManger.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val menuAdapter = MenuAdapter(getMenuData()) { data ->
            handleOnClick(data)
        }
        recyclerView.adapter = menuAdapter

    }

    private fun handleOnClick(data: MenuData) {
        when(data.id) {
            1 -> startActivity(Intent(this, ScrollingActivity::class.java))
            2 -> startActivity(Intent(this, ScrollingProfileActivity::class.java))
            3 -> startActivity(Intent(this, FloatingBottomActivity::class.java))
            4 -> startActivity(Intent(this, CircularRevealSharedElementListActivity::class.java))
        }
    }

    private fun getMenuData(): List<MenuData> {
        return arrayListOf(
                MenuData(id = 1, title = "Scrolling"),
                MenuData(id = 2, title = "Scrolling Profile"),
                MenuData(id = 3, title = "Floating Bottom"),
                MenuData(id = 4, title = "Circular Reveal - Post-Lollipop"))
    }
}
