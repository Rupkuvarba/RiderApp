package com.app.eho.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @Expose
    @SerializedName("email")      //Example : name@domain.local
    var email: String,

    @Expose
    @SerializedName("email_confirmation")  //Example : name@domain.local
    var emailConfirmation: String,

    @Expose
    @SerializedName("username")
    var username: String,

    @Expose
    @SerializedName("password")
    var password: String,

    @Expose
    @SerializedName("password_confirmation")
    var passwordConfirmation: String,

    @Expose
    @SerializedName("first_name")
    var firstName: String,

    @Expose
    @SerializedName("last_name")
    var lastName: String,

    @Expose
    @SerializedName("invite_token")
    var inviteToken: String,
)