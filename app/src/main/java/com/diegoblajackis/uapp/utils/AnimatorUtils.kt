package com.diegoblajackis.uapp.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.BounceInterpolator

class AnimatorUtils {

    private object Holder {
        val INSTANCE = AnimatorUtils()
    }

    companion object {
        val instance: AnimatorUtils by lazy { Holder.INSTANCE }
    }

    fun playTwirlAnimation(viewToAnimate: View?) {
        val animatorSet = AnimatorSet()
        val bounceInt = BounceInterpolator()

        val rotateA = ObjectAnimator.ofFloat(viewToAnimate, View.ROTATION, 0f, 360f)

        animatorSet.setDuration(800)
        animatorSet.interpolator = bounceInt
        animatorSet.playTogether(rotateA)
        animatorSet.start()

    }
}