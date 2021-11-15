package com.example.appointcut

import DataModels.User
import android.util.Log
import com.example.appointcut.network.ApcService
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.jvm.Throws

object NetworkJava {
    @Throws(java.net.ConnectException::class)
    fun getToken(email: String, pw: String): User{
        lateinit var token:User
        //wait for async methods
        runBlocking {
                val tokenDeferred = async{ ApcService.retrofitService.getToken(email,pw)}
                token = tokenDeferred.await()
        }
        return token
    }
}