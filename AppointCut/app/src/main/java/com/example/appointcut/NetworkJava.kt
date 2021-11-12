package com.example.appointcut

import DataModels.User
import android.util.Log
import com.example.appointcut.network.ApcService
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object NetworkJava {
    fun getToken(email: String, pw: String): User {
        lateinit var token:User
        //wait for async methods
        runBlocking {
            try {
                val tokenDeferred = async{ ApcService.retrofitService.getToken(email,pw)}
                token = tokenDeferred.await()
            }catch (e: Exception){
                Log.d("NetworkJava", e.toString())
            }
        }
        return token
    }
}