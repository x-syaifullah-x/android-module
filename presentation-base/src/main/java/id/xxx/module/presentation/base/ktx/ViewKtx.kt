@file:JvmName("ViewKtx")

package id.xxx.module.presentation.base.ktx

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.util.Pair
import androidx.core.view.isVisible
import kotlin.math.hypot

fun View.setVisibleWithCircularReveal(isVisible: Boolean) {
    post {
        try {
            val cx = (width / 2)
            val cy = (height / 2)

            val radius = hypot(cx.toFloat(), cy.toFloat())
            val startRadius = if (isVisible) 0F else radius
            val endRadius = if (isVisible) radius else 0F
            val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, startRadius, endRadius)
            if (isVisible) {
                this.isVisible = isVisible
            } else {
                anim.doOnEnd { this.isVisible = isVisible }
            }
            anim.start()
        } catch (e: Throwable) {
            if (isVisible) this.isVisible = isVisible
        }
    }
}

fun View.startPostponedEnterTransition(isStart: Boolean) {
    if (isStart) {
        viewTreeObserver.addOnPreDrawListener {
            viewTreeObserver.removeOnPreDrawListener { true }
            ((context as ContextWrapper).baseContext as Activity).startPostponedEnterTransition()
            return@addOnPreDrawListener true
        }
    }
}

fun View.createPair(transitionName: String): Pair<View, String> {
    this.transitionName = transitionName
    return Pair.create(this, this.transitionName)
}