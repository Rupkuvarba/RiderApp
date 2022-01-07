package com.app.eho.fcm

import android.content.Context
import android.util.Log
import com.app.eho.data.local.pref.SPHelper
import com.app.eho.utils.log.LogUtil
import com.fsm.sharedpreference.SPConstants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseHelper {

    fun getFCMToken(context: Context) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                Log.w("FCMHelper", "fcm token Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            val storeToken = SPHelper.getMyStringPref(context, SPConstants.FCM_TOKEN);
            //Log.d("FCMHelper", "fcm token FCM registration token token $token")
            //Log.d("FCMHelper", "fcm token FCM registration token storeToken $storeToken")
            if(!token.isNullOrBlank() && !storeToken.equals(token)){
                SPHelper.setMyStringPref(context, SPConstants.FCM_TOKEN,token)
                LogUtil.displayLog("FCMHelper", "fcm token FCM registration token not same")
            }else{
                LogUtil.displayLog("FCMHelper", "fcm token FCM registration token same")
            }

        })
    }
}