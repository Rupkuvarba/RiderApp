package com.app.eho.ui

import android.app.Activity
import android.os.Bundle
import com.app.eho.data.local.pref.SPHelper
import com.app.eho.fcm.FirebaseHelper
import com.app.eho.mapIntent
import com.app.eho.utils.log.LogUtil
import com.fsm.sharedpreference.SPConstants
import android.os.Handler
import android.os.Looper
import com.app.eho.ui.modules.auth.login.loginIntent

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenNormal()
    }

    //Show splash normal way
    fun splashScreenNormal(){

        //This method is used so that your splash activity
        //can cover the entire screen.
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                FirebaseHelper.getFCMToken(this@SplashActivity)
                LogUtil.displayLog("Splash","Splash activity fcm token "+SPHelper.getMyStringPref(this@SplashActivity,SPConstants.FCM_TOKEN))
                if(SPHelper.getMyBooleanPref(this@SplashActivity,SPConstants.ACCESS_TOKEN)) {
                    startActivity(mapIntent())
                }else{
                    startActivity(loginIntent())
                }
                finish()
            }
        },2000)

    }
}