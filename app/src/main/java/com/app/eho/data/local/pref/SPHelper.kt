package com.app.eho.data.local.pref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class SPHelper {

    companion object {
        private const val APP_SHARED_PREFERENCE_NAME = "Base_application"

        fun getPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        }

        fun getMyStringPref(context: Context, keyName: String?): String? {
            return getPrefs(context).getString(keyName, "")
        }

        fun setMyStringPref(context: Context, keyName: String?, value: String?) {
            getPrefs(context).edit().putString(keyName, value).apply()
        }

        fun getMyBooleanPref(context: Context, keyName: String?): Boolean {
            return getPrefs(context).getBoolean(keyName, false)
        }

        fun setMyBooleanPref(context: Context, keyName: String?, value: Boolean) {
            getPrefs(context).edit().putBoolean(keyName, value).apply()
        }

        fun getMyIntPref(context: Context, id: String?): Int {
            return getPrefs(context).getInt(id, 0)
        }

        fun setMyIntPref(context: Context, id: String?, value: Int) {
            getPrefs(context).edit().putInt(id, value).apply()
        }

        fun clearMyPref(context: Context) {
            getPrefs(context).edit().clear().apply()
        }
    }


}
