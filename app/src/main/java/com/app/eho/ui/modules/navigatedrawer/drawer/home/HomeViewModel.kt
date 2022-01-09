package com.app.eho.ui.modules.navigatedrawer.drawer.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eho.data.model.Ambulance

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _reponse = MutableLiveData<Ambulance>()
    val response: LiveData<Ambulance>
        get() = _reponse

    init {
        getApiResponse()
    }

    private fun getApiResponse() {

        // ArrayList of class ItemsViewModel
        val data = mutableListOf<Ambulance>() //ArrayList<Ambulance>()
        data.add(Ambulance(1, "Zydus hospital", "₹ 300.32", "7:13pm 11 min away"))
        data.add(Ambulance(2, "Sterling hospital", "₹ 150", "7:13pm 11 min away"))
        data.add(Ambulance(3, "Sola hospital", "₹ 100", "7:13pm 11 min away"))
        _reponse.value = Ambulance(1, "Zydus hospital", "₹ 300.32", "7:13pm 11 min away")
        _reponse.value = Ambulance(2, "Sterling hospital", "₹ 150", "7:13pm 11 min away")
        _reponse.value = Ambulance(3, "Sola hospital", "₹ 100", "7:13pm 11 min away")
        _reponse.value = Ambulance(4, "Sterling hospital 2", "₹ 190", "7:13pm 11 min away")

    }
}