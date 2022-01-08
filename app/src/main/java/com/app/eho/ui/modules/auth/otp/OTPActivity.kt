package com.app.eho.ui.modules.auth.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.eho.R
import com.app.eho.databinding.ActivityOtpBinding
import com.app.eho.ui.base.BaseActivity
import com.app.eho.ui.mainIntent
import com.app.eho.ui.modules.navigatedrawer.navigateDrawerIntent


class OTPActivity : BaseActivity() {

    lateinit var context: Context
    lateinit var viewModel: OTPViewModel
    lateinit var activityBinding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
        setupView(savedInstanceState)
        setupObservers()
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(activityBinding.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_black_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activityBinding.toolbar.setNavigationOnClickListener(){
            finish()
        }

        activityBinding.otpView.setOnFinishListener {
            Log.i("MainActivity", it)
            startActivity(navigateDrawerIntent())
        }
        activityBinding.otpView.setOnCharacterUpdatedListener {
            if(it)
                Log.i("MainActivity", "The view is filled")
            else
                Log.i("MainActivity", "The view is NOT Filled")
            //otp_view.isFilled()
        }
    }

    override fun initBinding() {
        activityBinding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }

    override fun initViewModel() {
        context = this
        viewModel = ViewModelProvider(this)[OTPViewModel::class.java]
        //viewModel.setNetworkHelper(NetworkHelper(context))
        activityBinding.mViewModel = viewModel
    }

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.progressIn.observe(this, Observer {
            activityBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}

fun Context.otpIntent(): Intent {
    return Intent(this, OTPActivity::class.java)
}