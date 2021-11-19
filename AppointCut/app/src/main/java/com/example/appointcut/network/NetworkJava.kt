package com.example.appointcut.network

import DataModels.Shop
import DataModels.User
import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.net.ConnectException

object NetworkJava {
    @Throws(ConnectException::class)
    fun getToken(email: String, pw: String): User{
        lateinit var token:User
        //wait for async methods
        runBlocking {
                val tokenDeferred = async{ ApcService.retrofitService.getToken(email,pw)}
                token = tokenDeferred.await()
        }
        return token
    }

    @Throws(ConnectException::class)
    fun getShop(): ArrayList<Shop>{
        Log.d("NetworkJava","Fetching shops from server")
        lateinit var shops: ArrayList<Shop>
        //wait for async methods
        runBlocking {
            val shopsDeferred = async{ ApcService.retrofitService.getShops()}
            shops = shopsDeferred.await() as ArrayList<Shop>
        }
        return shops
    }
}