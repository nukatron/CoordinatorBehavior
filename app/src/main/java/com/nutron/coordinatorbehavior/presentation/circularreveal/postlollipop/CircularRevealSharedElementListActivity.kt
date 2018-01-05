package com.nutron.coordinatorbehavior.presentation.circularreveal.postlollipop

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nutron.coordinatorbehavior.R
import com.nutron.coordinatorbehavior.data.entity.ImageTitleData
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

        val adapter = ItemListAdapter(getListData()) { view, data -> handleOnClick(view, data) }
        recyclerView.adapter = adapter

    }

    private fun handleOnClick(view: View, data: ImageTitleData) {
        val intent = CircularRevealSharedElementActivity.getStartIntent(this, data)
        intent.apply {
            val transition = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@CircularRevealSharedElementListActivity,
                    view, getString(R.string.rounded_color_view_transition_name))
            startActivity(this, transition.toBundle())
        }
    }


    private fun getListData(): List<ImageTitleData> {
        return arrayListOf(
                ImageTitleData(id = R.drawable.header, title = "Item 1"),
                ImageTitleData(id = R.drawable.header_flowers, title = "Item 2"),
                ImageTitleData(id = R.drawable.header_material, title = "Item 3"),
                ImageTitleData(id = R.drawable.header_mountains, title = "Item 4"),
                ImageTitleData(id = R.drawable.header_mountains_re, title = "Item 5"),
                ImageTitleData(id = R.drawable.header_flowers, title = "Item 6"),
                ImageTitleData(id = R.drawable.header, title = "Item 7"),
                ImageTitleData(id = R.drawable.header_mountains_re, title = "Item 8"),
                ImageTitleData(id = R.drawable.header_flowers, title = "Item 9"),
                ImageTitleData(id = R.drawable.header_mountains, title = "Item 10"),
                ImageTitleData(id = R.drawable.header, title = "Item 11"))
    }
}
