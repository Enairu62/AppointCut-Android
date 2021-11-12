package com.example.appointcut.network

import DataModels.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "http://192.168.1.19:3000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApcServiceInterface {
    @GET("rest/token/{email}-{pw}")
    suspend fun getToken(@Path("email") email: String,
        @Path("pw") pw: String):User
}

object ApcService {
    val retrofitService: ApcServiceInterface by lazy {
        retrofit.create(ApcServiceInterface::class.java)
    }
}