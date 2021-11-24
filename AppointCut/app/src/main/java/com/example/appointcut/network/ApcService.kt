package com.example.appointcut.network

import com.example.appointcut.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


const val BASE_URL = "http://192.168.1.19:3000"

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
        @Path("pw") pw: String): User

    @GET("rest/shops")
    suspend fun getShops(): List<Shop>

    @GET("rest/shops/services/{id}")
    suspend fun getShopServices(@Path("id") id: Int): List<ShopService>

    @GET("rest/barbers/withshopservice/{id}")
    suspend fun  getSpecializationWithService(@Path("id") id: Int):
            List<EmployeeSpecialization>

    @GET("rest/barbers/{id}")
    suspend fun getBarber(@Path("id") id: Int): Barber

    @GET("rest/barbers/schedule/{id}-{month}-{year}")
    suspend fun getBarberScheduleForMonthYear(
        @Path("id") id: Int,
        @Path("month") month: Int,
        @Path("year") year: Int
    ): List<Appointment>
}

object ApcService {
    val retrofitService: ApcServiceInterface by lazy {
        retrofit.create(ApcServiceInterface::class.java)
    }
}