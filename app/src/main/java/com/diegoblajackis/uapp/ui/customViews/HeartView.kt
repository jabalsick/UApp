package com.diegoblajackis.uapp.ui.customViews

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

import com.diegoblajackis.uapp.R
import android.animation.ValueAnimator
import android.view.animation.*


class HeartView : RelativeLayout {
    private var mainHeart: View? = null
    private var heart1: View? = null
    private var heart2: View? = null
    private var heart3: View? = null
    private var heart4: View? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.heart_view_layout, this)
        this.mainHeart = findViewById(R.id.mainHeart)
        this.heart1 = findViewById(R.id.heart1)
        this.heart2 = findViewById(R.id.heart2)
        this.heart3 = findViewById(R.id.heart3)
        this.heart4 = findViewById(R.id.heart4)
    }

    private fun animateHeart(view: View?, duration: Long) {
        val animationHeart = AnimatorSet()

        val translationAnimation = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, -400f)
        translationAnimation.interpolator = AccelerateInterpolator()
        translationAnimation.duration = duration
        translationAnimation.repeatCount = ValueAnimator.INFINITE
        translationAnimation.repeatMode = ValueAnimator.RESTART

        val fadeAnimation = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 0.2f)
        fadeAnimation.interpolator = LinearInterpolator()
        fadeAnimation.duration = duration

        animationHeart.playTogether(translationAnimation, fadeAnimation)
        animationHeart.start()
    }

    private fun animateMainHeart() {
        val animationHeart = AnimatorSet()
        val scalexHeart = ObjectAnimator.ofFloat(mainHeart, View.SCALE_X, 1f, 1.3f, 1f)
        val scaleyHeart = ObjectAnimator.ofFloat(mainHeart, View.SCALE_Y, 1f, 1.3f, 1f)
        scalexHeart.repeatCount = Animation.INFINITE
        scaleyHeart.repeatCount = Animation.INFINITE
        animationHeart.playTogether(scalexHeart, scaleyHeart)
        animationHeart.duration = 2000
        animationHeart.interpolator = BounceInterpolator()
        animationHeart.start()
    }


    fun animateHearts() {
        animateHeart(heart1,1000)
        animateHeart(heart2,1500)
        animateHeart(heart3,1300)
        animateHeart(heart4,1100)

        animateMainHeart()
    }
}
