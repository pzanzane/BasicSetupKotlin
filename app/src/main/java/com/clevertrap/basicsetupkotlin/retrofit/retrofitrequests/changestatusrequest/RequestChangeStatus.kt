package com.clevertrap.basicsetupkotlin.retrofit.retrofitrequests.changestatusrequest

import com.google.gson.annotations.SerializedName

class RequestChangeStatus {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("junkreason")
    var junkreason: String? = null
}