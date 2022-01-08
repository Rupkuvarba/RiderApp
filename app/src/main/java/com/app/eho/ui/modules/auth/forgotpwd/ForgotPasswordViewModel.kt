package com.app.eho.ui.modules.auth.forgotpwd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.eho.utils.common.*

class ForgotPasswordViewModel(): ViewModel() {
    private val TAG: String = "RegistrationViewModel"

    /*
    You can set value by post and get value from mutable live data
     */
    //Used to get value of field
    val emailField: MutableLiveData<String> = MutableLiveData()

    val progressIn: MutableLiveData<Boolean> = MutableLiveData()

    val launchDummy: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validation>> = MutableLiveData()

    //Used to show validation error
    val emailValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.EMAIL)

    private fun filterValidation(field: Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }
    //var responseLiveData: MutableLiveData<LoginResponse>? = MutableLiveData<LoginResponse>()

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onForgotPassword() {
        val email = emailField.value

        val validations = Validator.validateForgotPasswordFields(email)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size) {

                progressIn.postValue(false)
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