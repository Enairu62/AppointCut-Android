package online.appointcut.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import online.appointcut.network.ApcService

class SignUpViewModel: ViewModel() {
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var email:String
    lateinit var password: String
    lateinit var contact: String


    suspend fun register(): Int{
        val response = ApcService.retrofitService.registerUser(this)
        Log.d("SUVM", "Server Response: $response")

        return SUCCESS
    }

    companion object{
        const val SUCCESS = 0
        const val EMAIL = 1
        const val FAIL = 2
    }
}