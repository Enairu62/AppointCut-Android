package online.appointcut

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import online.appointcut.databinding.ActivitySignUpBinding
import online.appointcut.viewmodels.SignUpViewModel
import java.net.ConnectException
import java.net.SocketTimeoutException

class SignUp : AppCompatActivity() {
    var inputFirstName: EditText? = null
    var inputLastName: EditText? = null
    var inputPasswordSignUp: EditText? = null
    var inputContact: EditText? = null
    var inputEmailAdd: EditText? = null
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputFirstName = findViewById<View>(R.id.inputFirstName) as EditText
        inputLastName = findViewById<View>(R.id.inputLastName) as EditText
        inputPasswordSignUp = findViewById<View>(R.id.inputPasswordSignUp) as EditText
        inputContact = findViewById<View>(R.id.inputContact) as EditText
        inputEmailAdd = findViewById<View>(R.id.inputEmailAdd) as EditText

        val termText = "Terms and Conditions"
        val ss = SpannableString(termText)
        ss.setSpan(UnderlineSpan(), 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.linkTerms.text = ss
        binding.btnRegister.setOnClickListener(onRegisterClick)
        binding.imageView.setOnClickListener {
            val intent = Intent(this@SignUp, LoginFragment::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {}

    private val onRegisterClick = View.OnClickListener{
        //check for empty inputs
        if (checkInputs()) {
            Toast.makeText(
                this@SignUp,
                "All details are required.",
                Toast.LENGTH_SHORT
            ).show()
            return@OnClickListener
        }

        //block the button
        toggleButtons(false)

        //give data to viewModel
        viewModel.firstName = binding.inputFirstName.text.toString()
        viewModel.lastName = binding.inputLastName.text.toString()
        viewModel.password = binding.inputPasswordSignUp.text.toString()
        viewModel.contact = binding.inputContact.text.toString()
        viewModel.email = binding.inputEmailAdd.text.toString()

        lifecycleScope.launch {
            //register the user
            //if success go back to login
            try {
                val serverResponse = viewModel.register()
                when (serverResponse) {
                    SignUpViewModel.SUCCESS -> {
                        startActivity(Intent(this@SignUp, LoginFragment::class.java))
                    }
                    SignUpViewModel.EMAIL -> {
                        //otherwise unblock button
                        toggleButtons(true)
                        Toast.makeText(this@SignUp, "Email taken", Toast.LENGTH_SHORT)
                            .show()
                    }
                    SignUpViewModel.FAIL -> {
                        //otherwise unblock button
                        toggleButtons(true)
                        Toast.makeText(this@SignUp, "Failed for unknown reason", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }catch(e: ConnectException){
                toggleButtons(true)
                Toast.makeText(this@SignUp, "Server unreachable", Toast.LENGTH_SHORT)
                    .show()
            }catch(e: SocketTimeoutException){
                toggleButtons(true)
                Toast.makeText(this@SignUp, "Server timed out", Toast.LENGTH_SHORT)
                    .show()
            }catch(e: Exception){
                toggleButtons(true)
                Toast.makeText(this@SignUp, "An unknown error occurred", Toast.LENGTH_SHORT)
                    .show()
            }
        }

//            val intent = Intent(this@SignUp, HomePageCustomer::class.java)
//            intent.putExtra("fullName", fullName)
    }

    private fun toggleButtons(activate: Boolean){
        //disable
        if(!activate){
            binding.registerWait.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.INVISIBLE
            binding.imageView.visibility = View.GONE
        }else{//enable
            binding.registerWait.visibility = View.GONE
            binding.btnRegister.visibility = View.VISIBLE
            binding.imageView.visibility = View.VISIBLE
        }
    }

    private fun checkInputs(): Boolean{
        val first = binding.inputFirstName.text.toString()
        val last = binding.inputLastName.text.toString()
        val pass = binding.inputPasswordSignUp.text.toString()
        val contact = binding.inputContact.text.toString()
        val email = binding.inputEmailAdd.text.toString()

        return first.trim { it <= ' ' }.isEmpty() || last.trim { it <= ' ' }
            .isEmpty() || pass.trim { it <= ' ' }.isEmpty() || contact.trim { it <= ' ' }
            .isEmpty() || email.trim { it <= ' ' }.isEmpty()
    }
}