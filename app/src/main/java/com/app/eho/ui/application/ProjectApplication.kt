package com.app.eho.ui.application

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.app.eho.utils.helper.Foreground

class ProjectApplication : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var contextApp: Context
    }

    override fun onCreate() {
        super.onCreate()
        contextApp = applicationContext
        Foreground.init(this)
        /*APIServer.URL = getResources().getString(R.string.api_url);
        APIServer.URL_VERSION = getResources().getString(R.string.url_version);
        APIServer.BASE_URL = APIServer.URL+ APIServer.API + APIServer.URL_VERSION + "/";

        LogUtil.displayLog("TAG", "onCreate: URL: " + APIServer.URL );*/

        /*APIServer.URL = BuildConfig.A
        APIServer.BASE_URL = APIConstants.URL + APIConstants.API + APIConstants.URL_VERSION + "/"
        Log.i("FSM", "FSMApplication - onCreate - DB_NAME: " + APIConstants.DB_NAME)
        Log.i("FSM", "FSMApplication - onCreate - BASE_URL: " + APIConstants.BASE_URL)*/
    }
}