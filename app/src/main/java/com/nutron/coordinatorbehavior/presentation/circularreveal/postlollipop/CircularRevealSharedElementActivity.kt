package com.nutron.coordinatorbehavior.presentation.circularreveal.postlollipop

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.nutron.coordinatorbehavior.R
import kotlinx.android.synthetic.main.activity_circular_reveal_shared_element.*

class CircularRevealSharedElementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_reveal_shared_element)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}
