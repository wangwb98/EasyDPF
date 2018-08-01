package com.asrmicro.os.easydpf

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.View
//import com.asrmicro.os.easydpf.R.id.fullscreen_content_controls
import kotlinx.android.synthetic.main.activity_fullscreen.*

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import java.util.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : Activity() {
    private var mPicIndex = 0
    private var mBackgroundTimer: Timer? = null

    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_DPAD_RIGHT ) updateBackground(true)
        if (keyCode == KEYCODE_DPAD_LEFT ) updateBackground(false)

        return super.onKeyUp(keyCode, event)
    }

    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up the user interaction to manually show or hide the system UI.
//        fullscreen_content.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        dummy_button.setOnTouchListener(mDelayHideTouchListener)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)

        fullscreen_content.isFocusable = true
        fullscreen_content.requestFocus()
/*
        fullscreen_content.postDelayed(Runnable {
            fullscreen_content.isFocusable = true
            fullscreen_content.requestFocus()},
                1000)
*/
        Glide.with(this).load(pic_list[mPicIndex])
                .error(R.color.black_overlay)
                //.centerCrop()
                .crossFade()
                .placeholder(R.color.black_overlay).into(fullscreen_content);

        startBackgroundTimer()
    }

    private fun hide() {
        // Hide UI first
        actionBar?.hide()

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHideHandler.post { updateBackground(true) }
        }
    }

    @Synchronized private fun updateBackground(forward: Boolean) {
        if (forward) {
            mPicIndex = (mPicIndex + 1) % pic_list.size
        }
        else {
            if (mPicIndex < 1) mPicIndex = pic_list.size - 1
            else mPicIndex--
        }

        Glide.with(this)
                .load(pic_list[mPicIndex])
                .error(R.color.black_overlay)
                //.centerCrop()
                .crossFade()
                .placeholder(R.color.black_overlay).into(fullscreen_content);
        startBackgroundTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300

        private var pic_list = arrayOf(
                "http://f.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ece5f167da2a9759ee3d6ddb37.jpg",
                "http://i1.hdslb.com/bfs/archive/96dce37d84f4c86595b6ad2f5b31f2547e7a6f06.jpg",
                "http://img.tupianzj.com/uploads/allimg/20151229/pbovne5t13p202.jpg",
                "http://img.tupianzj.com/uploads/allimg/160518/9-16051Q51I1I3.JPG"
        )

        private val TAG = "EasyDPF"
        private val BACKGROUND_UPDATE_DELAY = 2000

    }
}
