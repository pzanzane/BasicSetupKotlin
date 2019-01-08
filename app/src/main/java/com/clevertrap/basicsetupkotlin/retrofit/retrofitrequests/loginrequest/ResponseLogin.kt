package com.clevertrap.basicsetupkotlin.retrofit.retrofitrequests.loginrequest

import com.google.gson.annotations.SerializedName

class ResponseLogin {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("mobile")
    var mobile: String? = null

}