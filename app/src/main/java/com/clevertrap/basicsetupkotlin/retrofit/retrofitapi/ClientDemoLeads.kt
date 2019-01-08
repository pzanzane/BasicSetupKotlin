package com.clevertrap.basicsetupkotlin.retrofit.retrofitapi

import com.fidelitservices.brownstone.models.RMLeads
import retrofit2.Call
import retrofit2.http.GET

interface ClientDemoLeads {

    @GET("leads")
    fun getAllLeads(): Call<List<RMLeads>>
}