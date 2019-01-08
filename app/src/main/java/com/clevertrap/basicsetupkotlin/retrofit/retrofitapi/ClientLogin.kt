package com.clevertrap.basicsetupkotlin.retrofit.retrofitapi

import com.clevertrap.basicsetupkotlin.retrofit.retrofitrequests.loginrequest.RequestLogin
import com.clevertrap.basicsetupkotlin.retrofit.retrofitrequests.loginrequest.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClientLogin {

    @POST("leads/login")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLogin>
}