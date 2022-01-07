package com.app.eho.utils.apptheme

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

object ThemeUtil {

    @SuppressLint("InlinedApi")
    fun setTransparentStatusBar(mContext: Activity) {

        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(mContext, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            mContext.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(
                mContext,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                true
            )
            setWindowFlag(mContext, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            setWindowFlag(mContext, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false)
            mContext.window.statusBarColor = Color.TRANSPARENT
            setWindowFlag(mContext, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, true)
        }
    }

    //@Param on (if true - addFlags, if false - clearFlags)
    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}