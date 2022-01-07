package com.app.eho.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.app.eho.R


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