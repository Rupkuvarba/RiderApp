package com.app.eho.ui.modules.auth.registration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.eho.R
import com.app.eho.databinding.ActivityLoginBinding
import com.app.eho.databinding.ActivityRegistrationBinding
import com.app.eho.ui.MainActivity
import com.app.eho.ui.base.BaseActivity
import com.app.eho.ui.modules.auth.login.LoginActivity
import com.app.eho.ui.modules.auth.login.LoginViewModel
import com.app.eho.utils.common.Event
import com.app.eho.utils.common.Status

class RegistrationActivity : BaseActivity() {

    lateinit var context: Context
    lateinit var viewModel: RegistrationViewModel
    lateinit var activityBinding : ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initViewModel()
        setupView(savedInstanceState)
        setupObservers()
    }

    override fun initBinding() {
        activityBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }

    override fun initViewModel() {
        context = this
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
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
        activityBinding.etName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onNameChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        activityBinding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        activityBinding.etEmailConfirm.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailConfirmChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        activityBinding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        activityBinding.etPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordConfirmChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        activityBinding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPhoneChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        activityBinding.btnSubmit.setOnClickListener { viewModel.onRegister() }
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

        viewModel.nameField.observe(this, Observer {
            if (activityBinding.etName.text.toString() != it) activityBinding.etName.setText(it)
        })

        viewModel.emailField.observe(this, Observer {
            if (activityBinding.etEmail.text.toString() != it) activityBinding.etEmail.setText(it)
        })

        viewModel.emailConfirmField.observe(this, Observer {
            if (activityBinding.etEmailConfirm.text.toString() != it) activityBinding.etEmailConfirm.setText(it)
        })

        viewModel.passwordField.observe(this, Observer {
            if (activityBinding.etPassword.text.toString() != it) activityBinding.etPassword.setText(it)
        })

        viewModel.passwordConfirmField.observe(this, Observer {
            if (activityBinding.etPasswordConfirm.text.toString() != it) activityBinding.etPasswordConfirm.setText(it)
        })

        viewModel.nameValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutName.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutName.isErrorEnabled = false
            }
        })

        viewModel.emailValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutEmail.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutEmail.isErrorEnabled = false
            }
        })

        viewModel.emailConfirmValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutEmailConfirm.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutEmailConfirm.isErrorEnabled = false
            }
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutPassword.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutPassword.isErrorEnabled = false
            }
        })

        viewModel.passwordConfirmValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutPasswordConfirm.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutPasswordConfirm.isErrorEnabled = false
            }
        })

        viewModel.phoneValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.layoutPhone.error = it.data?.run { getString(this) }
                else -> activityBinding.layoutPhone.isErrorEnabled = false
            }
        })

        viewModel.progressIn.observe(this, Observer {
            activityBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

}

fun Context.registrationIntent(): Intent {
    return Intent(this, RegistrationActivity::class.java)
}