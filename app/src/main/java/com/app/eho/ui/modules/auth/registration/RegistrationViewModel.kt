package com.app.eho.ui.modules.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.eho.utils.common.*
import io.reactivex.disposables.CompositeDisposable

class RegistrationViewModel(): ViewModel() {
    private val TAG: String = "RegistrationViewModel"

    /*
    You can set value by post and get value from mutable live data
     */
    //Used to get value of field
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val emailConfirmField: MutableLiveData<String> = MutableLiveData()
    val passwordConfirmField: MutableLiveData<String> = MutableLiveData()
    var nameField: MutableLiveData<String> = MutableLiveData()
    var phoneField: MutableLiveData<String> = MutableLiveData()

    val progressIn: MutableLiveData<Boolean> = MutableLiveData()

    val launchDummy: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validation>> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    //Used to show validation error
    val nameValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.NAME)
    val emailValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.EMAIL)
    val passwordValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.PASSWORD)
    val emailConfirmValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.EMAIL)
    val passwordConfirmValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.PASSWORD)
    val phoneValidation: LiveData<Resource<Int>> = filterValidation(Validation.Field.PHONE)

    private fun filterValidation(field: Validation.Field) =
        Transformations.map(validationsList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }
    //var responseLiveData: MutableLiveData<LoginResponse>? = MutableLiveData<LoginResponse>()

    fun onNameChange(name: String) = nameField.postValue(name)

    fun onEmailChange(email: String) = emailField.postValue(email)

    fun onPasswordChange(password: String) = passwordField.postValue(password)

    fun onEmailConfirmChange(emailConfirm: String) = emailConfirmField.postValue(emailConfirm)

    fun onPasswordConfirmChange(passwordConfirm: String) = passwordConfirmField.postValue(passwordConfirm)

    fun onPhoneChange(phone: String) = phoneField.postValue(phone)

    fun onRegister() {
        val name = nameField.value
        val email = emailField.value
        val password = passwordField.value
        val emailConfirm = emailConfirmField.value
        val passwordConfirm = passwordConfirmField.value
        val phone = phoneField.value

        val validations = Validator.validateRegistrationFields(name, email, password, emailConfirm, passwordConfirm, phone)
        validationsList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }
            if (successValidation.size == validations.size) {

                progressIn.postValue(false)
                launchDummy.postValue(Event(emptyMap()))

               /* compositeDisposable.addAll(
                    UserRepository.doUserLogin(email, password)
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
                //Call api
                /*loggingIn.postValue(true)
                */
            }
        }
    }

    /*override fun onCreate() {
        TODO("Not yet implemented")
    }*/



}