package com.app.eho.ui.modules.auth.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.eho.R
import com.app.eho.data.local.pref.SPHelper
import com.app.eho.databinding.ActivityLoginBinding
import com.app.eho.ui.base.BaseActivity
import com.app.eho.ui.custom.AlertDialogUtil
import com.app.eho.ui.mainIntent
import com.app.eho.ui.modules.auth.otp.otpIntent
import com.app.eho.utils.common.Event
import com.app.eho.utils.common.Status
import com.app.eho.utils.log.LogUtils
import com.app.eho.utils.network.NetUtils
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.fsm.sharedpreference.SPConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginActivity : BaseActivity() {

    lateinit var context: Context
    lateinit var viewModel: LoginViewModel
    lateinit var activityBinding: ActivityLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState)
        isLoggedIn = false

        initBinding()
        initViewModel()
        setupView(savedInstanceState)
        setupObservers()
        setUpGmail()

    }

    override fun initBinding() {
        activityBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }

    override fun initViewModel() {
        context = this@LoginActivity
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        //viewModel.setNetworkHelper(NetworkHelper(context))
        activityBinding.mViewModel = viewModel
    }

    override fun setupView(savedInstanceState: Bundle?) {
//        activityBinding.tvGmail.paintFlags = Paint.UNDERLINE_TEXT_FLAG
//        activityBinding.tvForgotPwd.paintFlags = Paint.UNDERLINE_TEXT_FLAG
//
//        activityBinding.etEmail.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.onEmailChange(s.toString())
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//        })

        activityBinding.etNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

//        activityBinding.btnSubmit.setOnClickListener { viewModel.onLogin() }
        activityBinding.btnSubmit.setOnClickListener { startActivity(otpIntent()) }

////        activityBinding.btnRegistration.setOnClickListener { startActivity(registrationIntent()) }
////
////        activityBinding.tvForgotPwd.setOnClickListener { startActivity(forgotPasswordIntent()) }
////
////        activityBinding.tvNavigate.setOnClickListener { startActivity(navigateDrawerIntent()) }
////
////        activityBinding.tvGmail.setOnClickListener { //viewModel.onGmailLogin()
//         }

        activityBinding.signInButton.setSize(SignInButton.SIZE_STANDARD);

        activityBinding.signInButton.setOnClickListener { signIn() }

        activityBinding.ivGmail.setOnClickListener { signIn() }
        activityBinding.ivFacebook.setOnClickListener { FacebookLogin() }
    }

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity
        viewModel.launchDummy.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                SPHelper.setMyBooleanPref(this@LoginActivity, SPConstants.ACCESS_TOKEN, true)
                startActivity(mainIntent())
                finish()
            }
        })

//        viewModel.emailField.observe(this, Observer {
//            if (activityBinding.etEmail.text.toString() != it) activityBinding.etEmail.setText(it)
//        })
//
//        viewModel.emailValidation.observe(this, Observer {
//            when (it.status) {
//                Status.ERROR -> activityBinding.layoutEmail.error = it.data?.run { getString(this) }
//                else -> activityBinding.layoutEmail.isErrorEnabled = false
//            }
//        })

        viewModel.passwordField.observe(this, Observer {
            if (activityBinding.etNumber.text.toString() != it) activityBinding.etNumber.setText(
                it
            )
        })

        viewModel.passwordValidation.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> activityBinding.etNumber.error =
                    it.data?.run { getString(this) }
            }
        })

        viewModel.loggingIn.observe(this, Observer {
            activityBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI(account)
        }
    }

    fun updateUI(account: GoogleSignInAccount?) {
        //Show login user details

        LogUtils.displayLog("Tag", " User login detail acct: " + account)
        if (account != null) {
            val personName = account.getDisplayName()
            val personGivenName = account.getGivenName()
            val personFamilyName = account.getFamilyName()
            val personEmail = account.getEmail()
            val personId = account.getId()
            val personPhoto = account.getPhotoUrl()

            var userInfo = "\nName: " + personName +
                    "\npersonId: " + personId +
                    "\npersonEmail: " + personEmail +
                    "\npersonFamilyName: " + personFamilyName +
                    "\npersonGivenName: " + personGivenName +
                    "\npersonPhoto: " + personPhoto

            Toast.makeText(mContext, " User login details: $userInfo", Toast.LENGTH_LONG).show()
            LogUtils.displayLog("Tag", " User login detail : $userInfo")
        }
        startActivity(mainIntent())
        finish()
    }

    fun setUpGmail() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.getSignInIntent()
        resultLauncher.launch(signInIntent)
        //startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            LogUtils.displayLog("Login", " Login activity result.resultCode: " + result.resultCode)
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                LogUtils.displayLog("Login", " Login activity " + data)
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
                //doSomeOperations()
            }
        }

    //Social Login
    private fun SocialLogin() {
        //First Logout
        // signOutGoogle() //Sign out from google,if sign in
        if (NetUtils.isNetworkAvailable(mContext)) {
            //Google
            signIn()
        } else AlertDialogUtil.showAlert(
            mContext,
            getString(R.string.app_name),
            getString(R.string.check_internet_connection)
        )
    }

    //Sign out - Google
    private fun signOutGoogle() {
        try {
            /*Auth.GoogleSignInApi.signOut(googleSignInClient).setResultCallback { status ->
                LogUtil.displayLog(
                    "WelcomeActivity: ",
                    "signOut : status : $status"
                )
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            LogUtils.displayLog("TAG", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    fun FacebookLogin() {
        var callbackManager = CallbackManager.Factory.create();
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        val EMAIL = "email"
        var loginButton = findViewById<View>(R.id.login_button) as LoginButton
//
        loginButton.setReadPermissions(Arrays.asList(EMAIL, "public_profile"))
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        // If you are using in a fragment, call loginButton.setFragment(this);

//        val manager= LoginManager.getInstance();
//        manager.logInWithReadPermissions(this, Arrays.asList("public_profile"));
//        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {


            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                LogUtils.displayLog("Login", " Login activity " + exception?.message)

                // App code
            }

            override fun onSuccess(result: LoginResult?) {
                LogUtils.displayLog("Login", " Login activity " + result?.accessToken)
                startActivity(mainIntent())
                finish()
            }
        })

        loginButton.callOnClick()

    }

}

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}