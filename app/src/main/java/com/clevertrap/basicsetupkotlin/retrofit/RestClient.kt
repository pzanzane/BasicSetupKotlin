package com.clevertrap.basicsetupkotlin.retrofit

import android.util.Log
import com.clevertrap.basicsetupkotlin.utility.Constant
import com.clevertrap.basicsetupkotlin.utility.PreferenceUtils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Created by fidel25 on 12/06/2017.
 */

object RestClient {

    val errorConverter: Converter<ResponseBody, ErrorMessage>
        get() {


            val builder = Retrofit.Builder()
                    .baseUrl(UrlConstants.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
            val retrofit = builder.build()

            return retrofit.responseBodyConverter(ErrorMessage::class.java, arrayOfNulls(0))

        }

    fun <S> createService(serviceClass: Class<S>): S {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(LoggingInterceptor())
        val builder = Retrofit.Builder()
                .baseUrl(UrlConstants.SERVER_URL)
                .client(httpClient.build()).addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }

    fun <S> createServiceWithHeaders(serviceClass: Class<S>, preferenceUtils: PreferenceUtils): S {

        Log.d(Constant.LogConstants.TAG_WASTE, UrlConstants.HEADER_API_KEY + ":" + preferenceUtils.getString(Constant.PreferenceConstants.API_KEY) + "\n" +
                UrlConstants.HEADER_CONTENT_TYPE + ":" + "application/json")
        val interceptor = Interceptor { chain ->
            if (null == preferenceUtils)
                Log.e("NULL", "NULL")
            if (null == preferenceUtils.getString(preferenceUtils.getString(Constant.PreferenceConstants.API_KEY)!!))
                Log.e("NULL", "NULL")

            val request = chain.request().newBuilder()
                    .addHeader(UrlConstants.HEADER_API_KEY, preferenceUtils.getString(Constant.PreferenceConstants.API_KEY))
                    .addHeader(UrlConstants.HEADER_CONTENT_TYPE, "application/json")
                    .build()

            chain.proceed(request)
        }
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(LoggingInterceptor())
        val client = httpClient.build()

        val builder = Retrofit.Builder()
                .baseUrl(UrlConstants.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }


    fun <S> createServiceWithUrl(url: String, serviceClass: Class<S>): S {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(LoggingInterceptor())
        val builder = Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient.build()).addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }

}
