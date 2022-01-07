package com.app.eho.utils.constant

object APIServer {

    //Base - URL
    lateinit var URL: String; //publish -> //https://hlth.care/

    const val API = "api/v"
    var URL_VERSION = "1"
    var BASE_URL: String = URL + API + URL_VERSION + "/"
    //var APP_NAME: String = ""
}