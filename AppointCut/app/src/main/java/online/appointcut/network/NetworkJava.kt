package online.appointcut.network

import online.appointcut.models.Shop
import online.appointcut.models.User
import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.net.ConnectException

object NetworkJava {
    @Throws(ConnectException::class)
    fun getToken(email: String, pw: String, type: String): User {
        lateinit var token: User
        //wait for async methods
        runBlocking {
                val tokenDeferred = async{ ApcService.retrofitService.getToken(email,pw,type)}
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