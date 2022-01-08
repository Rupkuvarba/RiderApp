package com.app.eho.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.app.eho.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //Toast.makeText(this, "Content ready ", Toast.LENGTH_LONG).show()
        //loginIntent()

    }


}

fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
}