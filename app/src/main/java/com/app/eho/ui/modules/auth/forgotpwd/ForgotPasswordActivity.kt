package com.app.eho.ui.modules.auth.forgotpwd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.eho.R
import com.app.eho.databinding.ActivityForgotPasswordBinding
import com.app.eho.ui.base.BaseActivity
import com.app.eho.utils.common.Event
import com.app.eho.utils.common.Status

class ForgotPasswordActivity : BaseActivity() {

    lateinit var context: Context
    lateinit var viewModel: ForgotPasswordViewModel
    lateinit var activityBinding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initViewModel()
        setupView(savedInstanceState)
        setupObservers()
    }

    override fun initBinding() {
        activityBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }

    override fun initViewModel() {
        context = this
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        //viewModel.setNetworkHelper(NetworkHelper(context))
        activityBinding.mViewModel = viewModel
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(activityBinding.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activityBinding.toolbar.setNavigationOnClickListener(){
            finish()
        }

        activityBinding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        activityBinding.btnSubmit.setOnClickListener { Toast.makeText(context," Do action for forgot password", Toast.LENGTH_SHORT) }
    }

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchDummy.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                //startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })

        viewModel.emailField.observe(this, Observer {
            if (activityBinding.etEmail.text.toString() != it) activityBinding.etEmail.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutEmail.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutEmail.isErrorEnabled = false
            }
        })

        viewModel.progressIn.observe(this, Observer {
            activityBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

}

fun Context.forgotPasswordIntent(): Intent {
    return Intent(this, ForgotPasswordActivity::class.java)
}