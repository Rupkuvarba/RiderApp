package com.mindorks.bootcamp.instagram.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
@Expose - an annotation that indicates this member should be exposed for JSON serialization or deserialization.
@SerializedName annotation can be used to serialize a field with a different name instead of an actual field name.
 */

data class User(

    @Expose
    @SerializedName("userId")
    val id: String,

    @Expose
    @SerializedName("userName")
    val name: String,

    @Expose
    @SerializedName("userEmail")
    val email: String,

    @Expose
    @SerializedName("accessToken")
    val accessToken: String,

    @Expose
    @SerializedName("profilePicUrl")
    val profilePicUrl: String? = null
)