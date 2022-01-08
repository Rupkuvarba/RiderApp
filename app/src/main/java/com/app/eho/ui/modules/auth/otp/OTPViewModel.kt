package com.app.eho.ui.modules.auth.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OTPViewModel : ViewModel() {
    private val TAG: String = "OTPViewModel"

    /*
       You can set value by post and get value from mutable live data
    */
    //Used to get value of field
    val progressIn: MutableLiveData<Boolean> = MutableLiveData()
    val loggingIn: MutableLiveData<Boolean> = MutableLiveData()
}