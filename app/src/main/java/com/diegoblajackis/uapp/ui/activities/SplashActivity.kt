package com.diegoblajackis.uapp.ui.activities

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.*
import com.diegoblajackis.uapp.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        playAnimation()
    }

    private fun playAnimation() {
        val bounceInt = BounceInterpolator()

        val mainAnimation = AnimatorSet()
        val animationFlash = AnimatorSet()
        val animationCamera = AnimatorSet()
        val animationFall = AnimatorSet()
        val animationFadeLogo = AnimatorSet()

        animationCamera.interpolator = bounceInt
        animationCamera.duration = 1000

        val fadeFlash = ObjectAnimator.ofFloat(flash1, View.ALPHA, 0f, 1f, 0f)
        fadeFlash.interpolator = LinearInterpolator()
        fadeFlash.duration = 100

        val fadeflash2 = ObjectAnimator.ofFloat(flash2, View.ALPHA, 0f, 1f, 0f)
        fadeflash2.interpolator = LinearInterpolator()
        fadeflash2.duration = 100

        val fadeFlash3 = ObjectAnimator.ofFloat(flash3, View.ALPHA, 0f, 1f, 0f)
        fadeFlash3.interpolator = LinearInterpolator()
        fadeFlash3.duration = 300

        val scalexCamera = ObjectAnimator.ofFloat(camera, View.SCALE_X, 1f, 1.3f, 1f)
        val scaleyCamera = ObjectAnimator.ofFloat(camera, View.SCALE_Y, 1f, 1.3f, 1f)
        scalexCamera.startDelay = 300
        scaleyCamera.startDelay = 300

        val rotateCamera = ObjectAnimator.ofFloat(camera, View.ROTATION, 0f, 30f, 0f)
        rotateCamera.interpolator = AccelerateDecelerateInterpolator()
        rotateCamera.duration = 400
        rotateCamera.startDelay = 1000
        rotateCamera.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                photo.visibility = View.VISIBLE
                photo.bringToFront()
                photo.invalidate()
                val anim = ScaleAnimation(
                        1f, 1f,
                        0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f)
                anim.fillAfter = true
                anim.duration = 1000

                photo.startAnimation(anim)
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        val rotatePhoto = ObjectAnimator.ofFloat(photo, View.ROTATION, 0f, 40f, 0f)
        rotatePhoto.interpolator = AccelerateInterpolator()
        rotatePhoto.duration = 300


        val translationPhoto = ObjectAnimator.ofFloat(photo, View.TRANSLATION_Y, 0f, 800f)
        translationPhoto.interpolator = AccelerateInterpolator()
        translationPhoto.duration = 1000

        val fadeCameraOut = ObjectAnimator.ofFloat(camera, View.ALPHA, 1f, 0f)
        fadeCameraOut.interpolator = LinearInterpolator()
        fadeCameraOut.duration = 3000

        val fadeLogoIn = ObjectAnimator.ofFloat(logo, View.ALPHA, 0f, 1f)
        fadeLogoIn.interpolator = LinearInterpolator()
        fadeLogoIn.duration = 3000

        animationCamera.playTogether(scalexCamera, scaleyCamera)
        animationFlash.playSequentially(fadeFlash, fadeflash2, fadeFlash3)
        animationFall.playTogether(rotatePhoto, translationPhoto)
        animationFall.startDelay = 2500
        animationFadeLogo.playTogether(fadeCameraOut,fadeLogoIn)
        animationFadeLogo.startDelay = 3700

        mainAnimation.play(animationFlash)
                .with(animationCamera)
                .before(rotateCamera)
                .before(animationFall)
                .before(animationFadeLogo)

        mainAnimation.start()
        mainAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                goToUserList()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }

    private fun goToUserList() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

}
