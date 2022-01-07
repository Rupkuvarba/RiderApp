package com.app.eho.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
@Expose - an annotation that indicates this member should be exposed for JSON serialization or deserialization.
@SerializedName annotation can be used to serialize a field with a different name instead of an actual field name.
 */

data class Dummy(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("imageUrl")
    val imageUrl: String?
)