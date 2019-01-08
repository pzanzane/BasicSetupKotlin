package com.clevertrap.basicsetupkotlin.retrofit.retrofitrequests.loginrequest

import com.google.gson.annotations.SerializedName

class RequestLogin {

    @SerializedName("username")
    var username: String? = null

    @SerializedName("password")
    var password: String? = null
}