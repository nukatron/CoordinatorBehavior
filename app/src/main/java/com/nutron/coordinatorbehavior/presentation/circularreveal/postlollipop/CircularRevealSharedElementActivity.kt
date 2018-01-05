package com.nutron.coordinatorbehavior.presentation.circularreveal.postlollipop

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.View
import android.view.ViewAnimationUtils
import com.nutron.coordinatorbehavior.R
import com.nutron.coordinatorbehavior.extension.getCenter
import kotlinx.android.synthetic.main.activity_circular_reveal_shared_element.*
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.animation.AccelerateDecelerateInterpolator
import com.nutron.coordinatorbehavior.data.entity.ImageTitleData
import com.nutron.coordinatorbehavior.extension.setImageSrc
import android.widget.RelativeLayout




class CircularRevealSharedElementActivity : AppCompatActivity() {

    private var transitionListener: TransitionCallBack = TransitionCallBack()

    companion object {
        val EXTRA_DATA = "ITEM_DATA"
        fun getStartIntent(context: Context, data: ImageTitleData): Intent {
            val intent = Intent(context, CircularRevealSharedElementActivity::class.java)
            intent.putExtra(EXTRA_DATA, data)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_reveal_shared_element)

        initToolbar()
        initView()
        initTransition()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        // Set the padding to match the Status Bar height
        val layoutParams = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
        layoutParams.height = layoutParams.height + getStatusBarHeight()
        toolbar.layoutParams = layoutParams
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)

        val data = intent?.getParcelableExtra<ImageTitleData>(EXTRA_DATA)
        data?.let { collapsingToolbarLayout.title = data.title }
    }

    // A method to find height of the status bar
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun initView() {
        val data = intent?.getParcelableExtra<ImageTitleData>(EXTRA_DATA)
        data?.let {
            roundedImage.setImageSrc(it.id)
            collapsingToolbarBackground.setBackgroundResource(it.id)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            val inSet = TransitionSet()
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.arc)

            // Add the inflated transition to the set and set duration and interpolator
            inSet.apply {
                addTransition(transition)
                duration = 380
                // The shared element should not move in linear motion
                interpolator = AccelerateDecelerateInterpolator()
                // Set a listener to create a circular reveal when the transition has ended
                setListener { onTransitionEnd { reveal() } }
            }

            val outSet = TransitionSet()
                    .apply {
                        addTransition(transition)
                        duration = 380
                        interpolator = AccelerateDecelerateInterpolator()
                        startDelay = 200
                    }


            window.sharedElementEnterTransition = inSet
            window.sharedElementExitTransition = outSet
            window.sharedElementReturnTransition = outSet

        } else {
            roundedImage.visibility = View.INVISIBLE
            collapsingToolbarBackground.visibility = View.VISIBLE
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun reveal() {
        val center = roundedImage.getCenter()
        val anim = ViewAnimationUtils.createCircularReveal(
                collapsingToolbarBackground,
                center.first.toInt(),
                center.second.toInt(),
                0f,
                collapsingToolbarBackground.measuredWidth.toFloat())
                .apply { duration = 400 }

        val animAlpha = ObjectAnimator.ofFloat(roundedImage, View.ALPHA, 0f)
                .apply { duration = 100 }

        collapsingToolbarBackground.visibility = View.VISIBLE
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(anim, animAlpha)
        animatorSet.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun hideView() {
        val center = roundedImage.getCenter()
        // create the animator for this view (the end radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(collapsingToolbarBackground,
                center.first.toInt(),
                center.second.toInt(),
                collapsingToolbarBackground.measuredWidth.toFloat(),
                0f)
                .apply {
                    duration = 200
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            collapsingToolbarBackground.visibility = View.INVISIBLE
                        }
                    })
                }


        val animAlpha = ObjectAnimator.ofFloat(roundedImage, View.ALPHA, 1f)
                .apply { duration = 380 }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(anim, animAlpha)
        animatorSet.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            hideView()
        }
    }

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.sharedElementEnterTransition.setListener(null)
        }
        super.onDestroy()
    }


    /********************* INNER CLASS & EXTENSION FUNCTION **********************/

    private fun Transition.setListener(func: (TransitionCallBack.() -> Unit)?) {
        if (func == null) {
            this.removeListener(transitionListener)
        } else {
            transitionListener = TransitionCallBack()
            transitionListener.func()
            this.addListener(transitionListener)
        }
    }

//    private fun Animator.setListener(func: (AnimatorCallBack.() -> Unit)?) : Animator {
//        if (func == null) {
//            this.removeAllListeners()
//        } else {
//            val listener = AnimatorCallBack()
//            listener.func()
//            this.addListener(listener)
//        }
//        return this
//    }

    inner class TransitionCallBack : android.transition.Transition.TransitionListener {

        private var _onTransitionEnd: ((t: android.transition.Transition?) -> Unit)? = null
        private var _onTransitionResume: ((t: android.transition.Transition?) -> Unit)? = null
        private var _onTransitionPause: ((t: android.transition.Transition?) -> Unit)? = null
        private var _onTransitionCancel: ((t: android.transition.Transition?) -> Unit)? = null
        private var _onTransitionStart: ((t: android.transition.Transition?) -> Unit)? = null


        fun onTransitionEnd(func: (t: android.transition.Transition?) -> Unit) {
            _onTransitionEnd = func
        }

        fun onTransitionResume(func: (t: android.transition.Transition?) -> Unit) {
            _onTransitionResume = func
        }

        fun onTransitionPause(func: (t: android.transition.Transition?) -> Unit) {
            _onTransitionPause = func
        }

        fun onTransitionCancel(func: (t: android.transition.Transition?) -> Unit) {
            _onTransitionCancel = func
        }

        fun onTransitionStart(func: (t: android.transition.Transition?) -> Unit) {
            _onTransitionStart = func
        }


        override fun onTransitionEnd(p0: android.transition.Transition?) {
            _onTransitionEnd?.invoke(p0)
        }

        override fun onTransitionResume(p0: android.transition.Transition?) {
            _onTransitionResume?.invoke(p0)
        }

        override fun onTransitionPause(p0: android.transition.Transition?) {
            _onTransitionPause?.invoke(p0)
        }

        override fun onTransitionCancel(p0: android.transition.Transition?) {
            _onTransitionCancel?.invoke(p0)
        }

        override fun onTransitionStart(p0: android.transition.Transition?) {
            _onTransitionStart?.invoke(p0)
        }

    }

//    inner class AnimatorCallBack : Animator.AnimatorListener {
//
//        private var _onAnimationRepeat: ((a: Animator?) -> Unit)? = null
//        private var _onAnimationEnd: ((a: Animator?) -> Unit)? = null
//        private var _onAnimationCancel: ((a: Animator?) -> Unit)? = null
//        private var _onAnimationStart: ((a: Animator?) -> Unit)? = null
//
//        fun onAnimationRepeat(func: (a: Animator?) -> Unit) {
//            _onAnimationRepeat = func
//        }
//
//        fun onAnimationEnd(func: (a: Animator?) -> Unit) {
//            _onAnimationEnd = func
//        }
//
//        fun onAnimationCancel(func: (a: Animator?) -> Unit) {
//            _onAnimationCancel = func
//        }
//
//        fun onAnimationStart(func: (a: Animator?) -> Unit) {
//            _onAnimationStart = func
//        }
//
//        override fun onAnimationRepeat(p0: Animator?) {
//            _onAnimationRepeat?.invoke(p0)
//        }
//
//        override fun onAnimationEnd(p0: Animator?) {
//            _onAnimationEnd?.invoke(p0)
//        }
//
//        override fun onAnimationCancel(p0: Animator?) {
//            _onAnimationCancel?.invoke(p0)
//        }
//
//        override fun onAnimationStart(p0: Animator?) {
//            _onAnimationStart?.invoke(p0)
//        }
//
//    }
}
