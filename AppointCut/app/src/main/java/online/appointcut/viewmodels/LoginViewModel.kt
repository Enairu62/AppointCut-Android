package online.appointcut.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import online.appointcut.HomePageBarber
import online.appointcut.HomePageCustomer
import online.appointcut.file.UserFile
import online.appointcut.file.UserFile.read
import online.appointcut.models.User
import online.appointcut.models.User.Companion.AuthStatus
import online.appointcut.network.ApcService
import online.appointcut.network.NetworkJava
import online.appointcut.network.NetworkJava.getToken
import java.lang.Exception
import java.net.ConnectException

class LoginViewModel: ViewModel() {

    /**
     * Checks if a user is currently logged in
     * @param context Context of the invoking object
     * @return [User] The logged in user
     */
    fun checkLoggedUser(context: Context): User?{
        return read(context)
    }

    /**
     * Retrieves user from the server
     * @return The user, null if incorrect credentials or does not exist
     */
    suspend fun getUser(email: String, password: String, type: String): User{
        return ApcService.retrofitService.getToken(email, password, type)
    }
}