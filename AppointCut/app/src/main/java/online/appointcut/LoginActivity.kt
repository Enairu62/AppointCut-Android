package online.appointcut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import online.appointcut.databinding.ActivityLoginBinding
import online.appointcut.file.UserFile
import online.appointcut.file.UserFile.save
import online.appointcut.models.User
import online.appointcut.models.User.Companion.AuthStatus
import online.appointcut.network.NetworkJava
import online.appointcut.network.NetworkJava.getToken
import online.appointcut.viewmodels.LoginViewModel
import java.lang.Exception
import java.net.ConnectException

class LoginActivity: AppCompatActivity() {
    private val loginViewModel = LoginViewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))

        //region check if a user is logged in
        val user = loginViewModel.checkLoggedUser(this)
        if (user != null) {
            lateinit var intent: Intent
            if (user.authStatus == User.Companion.AuthStatus.CUSTOMER) {
                Toast.makeText(
                    this,
                    "Welcome back " + user.firstName,
                    Toast.LENGTH_SHORT
                ).show()
                intent = Intent(this, HomePageCustomer::class.java)
            }
            else if (user.authStatus == User.Companion.AuthStatus.BARBER) {
                Toast.makeText(
                    this,
                    "Logged in as barber!",
                    Toast.LENGTH_SHORT
                ).show()
                intent = Intent(this, HomePageBarber::class.java)
            }

            intent.putExtra(FULL_NAME, user.firstName + " " + user.lastName)
            intent.putExtra(USER_ID, user.id)
            intent.putExtra(USER_TOKEN, user.token)
            startActivity(intent)
        }
        //endregion

        binding.btnLogin.setOnClickListener(::login)
        binding.linkSignUp.setOnClickListener {
            startActivity(
                Intent(this, SignUp::class.java)
            )
        }

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun login(view: View){
        //region check if fields are empty
        if(binding.inputUsername.text.isEmpty() || binding.inputPassword.text.isEmpty()){
            Toast.makeText(
                this,
                "Please insert all necessary details.",
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
        //endregion

        //region get user from server
        val userType = if (binding.typeSwitch.isChecked) "Employee" else "Customer"
        lifecycleScope.launch {
            try {
                openHomepage(
                    loginViewModel.getUser(
                        binding.inputUsername.text.toString(),
                        binding.inputPassword.text.toString(),
                        userType
                    )
                )
            }catch (e: ConnectException) {
                Toast.makeText(
                    this@LoginActivity,
                    "Unable to connect to server",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("LoginActivity", "server unreachable", e)
            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    "An unexpected error has occurred!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("LoginActivity", "An error Happened", e)
            }
        }
        //endregion
    }

    /**
     * Opens the homepage of the User
     * @param user The logged in user
     */
    private fun openHomepage(user: User){
        lateinit var intent: Intent
        when (user.authStatus) {
            AuthStatus.CUSTOMER -> {
                intent = Intent(this, HomePageCustomer::class.java)
            }
            AuthStatus.BARBER -> {
                intent = Intent(this, HomePageBarber::class.java)
            }
            AuthStatus.DESK -> {
                Toast.makeText(
                    this,
                    "Desk User is not allowed",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            AuthStatus.EMAIL -> {
                Toast.makeText(
                    this,
                    "Email not found",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            AuthStatus.PASSWORD -> {
                Toast.makeText(
                    this,
                    "Incorrect password!",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            AuthStatus.VERIFY -> {
                Toast.makeText(
                    this,
                    "Email Verification is required",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }

        Toast.makeText(this, "Successfully Login!", Toast.LENGTH_SHORT).show()
        intent.putExtra(FULL_NAME, user.firstName + " " + user.lastName)
        intent.putExtra(USER_ID, user.id)
        intent.putExtra(USER_TOKEN, user.token)
        //save user data to file
        save(this, user)
        startActivity(intent)
    }

    companion object{
        const val FULL_NAME = "FULL_NAME"
        const val USER_ID = "USER_ID"
        const val USER_TOKEN = "USER_TOKEN"
    }
}