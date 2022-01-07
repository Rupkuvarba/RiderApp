package com.app.eho.ui.modules.auth.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.eho.R
import com.app.eho.data.local.pref.SPHelper
import com.app.eho.ui.base.BaseViewModel
import com.app.eho.ui.custom.AlertDialogUtil
import com.app.eho.utils.common.*
import com.app.eho.utils.log.LogUtil
import com.app.eho.utils.network.NetUtils
import com.fsm.sharedpreference.SPConstants
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import java.lang.Exception

const val GOOGLE_SIGN_IN : Int = 9001

class LoginViewModel(): BaseViewModel() {
    private val TAG: String = "LoginViewModel"

    /*
    You can set value by post and get value from mutable live data
     */
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()

    val loggingIn: MutableLiveData<Boolean> = MutableLiveData()   //progressbar

    val launchDummy: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validation>> = MutableLiveData()
    val emailValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.PASSWORD)

    private fun filterValidation(field: Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }
    //var responseLiveData: MutableLiveData<LoginResponse>? = MutableLiveData<LoginResponse>()

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(password: String) = passwordField.postValue(password)

    fun onLogin() {
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateLoginFields(email, password)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size) {

                loggingIn.postValue(false)
                launchDummy.postValue(Event(emptyMap()))

                //Call api
                /*loggingIn.postValue(true)
                compositeDisposable.addAll(
                    userRepository.doUserLogin(email, password)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                userRepository.saveCurrentUser(it)
                                loggingIn.postValue(false)
                                launchDummy.postValue(Event(emptyMap()))
                            },
                            {
                                handleNetworkError(it)
                                loggingIn.postValue(false)
                            }
                        )
                )*/
            }
        }
    }


}