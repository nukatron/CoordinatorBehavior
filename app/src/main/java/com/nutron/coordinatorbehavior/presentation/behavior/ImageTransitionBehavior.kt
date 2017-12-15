package com.nutron.coordinatorbehavior.presentation.behavior

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.nutron.coordinatorbehavior.R


class ImageTransitionBehavior : CoordinatorLayout.Behavior<ImageView> {

    private val TAG = "behavior"
    private var context: Context? = null
    private var avatarMaxSize: Float = 0f
    private var finalLeftAvatarPadding: Float = 0f
    private var startXPosition: Int = 0
    private var startToolbarPosition: Float = 0f

    private var startYPosition: Int = 0
    private var finalYPosition: Int = 0
    private var finalHeight: Int = 0
    private var startHeight: Int = 0
    private var finalXPosition: Int = 0

    constructor()
    constructor(context: Context?, attrs: AttributeSet?) {
        this.context = context
    }

    init {
        finalLeftAvatarPadding = context?.resources?.getDimension(R.dimen.text_margin) ?: 0f
        avatarMaxSize = context?.resources?.getDimension(R.dimen.image_width) ?: 0f //TODO: change image width later

    }

    override fun layoutDependsOn(parent: CoordinatorLayout?,
                                 child: ImageView?,
                                 dependency: View?): Boolean {

        return dependency is Toolbar

    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: ImageView?, dependency: View?): Boolean {
        if(child != null && dependency != null) {
            maybeInitProperties(child, dependency)
            val maxScrollDistance = (startToolbarPosition - getStatusBarHeight()).toInt()
            val expandedPercentageFactor = dependency.y / maxScrollDistance

            val distanceYToSubtract = (startYPosition - finalYPosition) * (1f - expandedPercentageFactor) + child.height / 2
            val distanceXToSubtract = (startXPosition - finalXPosition) * (1f - expandedPercentageFactor) + child.width / 2
            val heightToSubtract = (startHeight - finalHeight) * (1f - expandedPercentageFactor)

            child.y = startYPosition - distanceYToSubtract
            child.x = startXPosition - distanceXToSubtract

            val lp = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.width = (startHeight - heightToSubtract).toInt()
            lp.height = (startHeight - heightToSubtract).toInt()
            child.layoutParams = lp
            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)

    }

    @SuppressLint("PrivateResource")
    private fun maybeInitProperties(child: ImageView, dependency: View) {
        if (startYPosition == 0)
            startYPosition = dependency.y.toInt()

        if (finalYPosition == 0)
            finalYPosition = dependency.height / 2

        if (startHeight == 0)
            startHeight = child.height

        if (finalHeight == 0)
            finalHeight = context?.resources?.getDimensionPixelOffset(R.dimen.image_small_width) ?: 0 //TODO: change image small width later

        if (startXPosition == 0)
            startXPosition = (child.x + child.width / 2).toInt()

        if (finalXPosition == 0)
            finalXPosition = (context?.resources?.getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) ?: 0) + finalHeight / 2

        if (startToolbarPosition == 0f)
            startToolbarPosition = dependency.y + dependency.height / 2
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context?.resources?.getIdentifier(
                "status_bar_height",
                "dimen",
                "android") ?: 0

        if (resourceId > 0) {
            result = context?.resources?.getDimensionPixelSize(resourceId) ?: 0
        }
        return result
    }

}