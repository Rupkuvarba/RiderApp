package com.app.eho.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    /*@Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("status")
    var status: Int,*/

    @Expose
    @SerializedName("success")
    var success: Boolean,

    @Expose
    @SerializedName("message")
    var message: String,

    @Expose
    @SerializedName("thanks_message")
    var thanksMessage: String,

    @Expose
    @SerializedName("user")
    var user: RegisterUser,

)

data class RegisterUser(
    @Expose
    @SerializedName("first_name")
    var firstName: String,

    @Expose
    @SerializedName("last_name")
    var lastName: String,

    @Expose
    @SerializedName("email")
    var email: String,

    @Expose
    @SerializedName("client_id")
    var clientId: String,

    @Expose
    @SerializedName("secret")
    var secret: String,

)