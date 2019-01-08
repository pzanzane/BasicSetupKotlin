package com.clevertrap.basicsetupkotlin.retrofit.retrofitapi

import com.clevertrap.basicsetupkotlin.retrofit.retrofitrequests.changestatusrequest.RequestChangeStatus
import com.fidelitservices.brownstone.models.RMLeads
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientLead {

    @GET("leads/getLeadsBySalesPerson/{id}")
    fun getAllLeads(@Path("id") salesPersonId: String): Call<List<RMLeads>>

    @PUT("/leads/updateLeadStatus")
    fun changeLeadStatus(@Body request: RequestChangeStatus): Call<ResponseBody>

    @GET("/leads/delete/{id}/{archive}")
    fun  archiveLeads(@Path("id") salesPersonId: String,@Path("archive") archiveStatus: Boolean): Call<List<RMLeads>>
}