package com.app.eho.ui.modules.navigatedrawer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eho.utils.common.*

class NavigateDrawerViewModel(): ViewModel() {
    private val TAG: String = "RegistrationViewModel"

    /*
    You can set value by post and get value from mutable live data
     */
    //Used to get value of field
    val progressIn: MutableLiveData<Boolean> = MutableLiveData()

    val launchDummy: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()

    private val validationsList: MutableLiveData<List<Validation>> = MutableLiveData()

}