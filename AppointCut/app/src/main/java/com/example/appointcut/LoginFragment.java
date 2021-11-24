package com.example.appointcut;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointcut.file.UserFile;
import com.example.appointcut.network.NetworkJava;

import com.example.appointcut.models.User;

public class LoginFragment extends AppCompatActivity {

    TextView linkSignUp;
    private EditText inputUsername;
    private EditText inputPassword;
    Button btnLogin;

    String userCustomer = "customer";
    String passCustomer = "customer";
    String userBarber = "barber";
    String passBarber = "barber";

    boolean isValidCus;
    boolean isValidBarber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //see if there is a logged in user
        Log.d("LoginFragment", "Reading file");
        User user = UserFile.INSTANCE.read(this);
        if (user != null){
            Intent intent;
            switch (user.getAuthStatus()){
                case CUSTOMER:
                    Toast.makeText(LoginFragment.this, "Welcome back "+ user.getFirstName(), Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginFragment.this, HomePageCustomer.class);
                    intent.putExtra("fullName", "Customer");
                    startActivity(intent);
                    break;
                case BARBER:
                    Toast.makeText(LoginFragment.this, "Logged in as barber!", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginFragment.this, HomePageBarber.class);
                    startActivity(intent);
            }
        }

        linkSignUp = (TextView) findViewById(R.id.linkSignUp);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        loginCondition();
        signUp();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void signUp(){
        String signupText = "Not a member? Signup now";
        SpannableString ss = new SpannableString(signupText);
        ss.setSpan(new UnderlineSpan(),14,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        linkSignUp.setText(ss);

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFragment.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateCus(String username, String password){
        if(username.equals(userCustomer) && password.equals(passCustomer)){
            return true;
        }
        return false;
    }

    private boolean validateBarber(String username, String password){
        if(username.equals(userBarber) && password.equals(passBarber)){
            return true;
        }
        return false;
    }

    private void loginCondition(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Credentials input
                String userText = inputUsername.getText().toString();
                String passText= inputPassword.getText().toString();

                //no input
                if(userText.trim().isEmpty() || passText.trim().isEmpty()){
                    Toast.makeText(LoginFragment.this,
                            "Please insert all necessary details.",
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                //get user from server
                User user = null;
                try {
                    user = NetworkJava.INSTANCE.getToken(userText,passText);
                }catch (java.net.ConnectException e){
                    Toast.makeText(LoginFragment.this,
                            "Unable to connect to server",
                            Toast.LENGTH_SHORT)
                            .show();
                    Log.e("LoginFragment", "server unreachable",e);
                }catch (Exception e){
                    Toast.makeText(LoginFragment.this,
                            "An unexpected error has occurred!",
                            Toast.LENGTH_SHORT)
                            .show();
                    Log.e("LoginFragment", "An error Happened",e);
                }
                if (user == null)return;
                Log.d("LoginFragment", user.toString());

                Intent intent;
                //Authenticity
                switch (user.getAuthStatus()){
                    case CUSTOMER:
                        Toast.makeText(LoginFragment.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginFragment.this, HomePageCustomer.class);
                        intent.putExtra("fullName", "Customer");
                        //save user data to file
                        UserFile.INSTANCE.save(LoginFragment.this,user);
                        startActivity(intent);
                    break;
                    case BARBER:
                        Toast.makeText(LoginFragment.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(LoginFragment.this, HomePageBarber.class);
                        //save user data to file
                        UserFile.INSTANCE.save(LoginFragment.this,user);
                        startActivity(intent);
                    break;
                    case DESK:
                        Toast.makeText(LoginFragment.this, "Desk User is not allowed", Toast.LENGTH_SHORT).show();
                    break;
                    case EMAIL:
                        Toast.makeText(LoginFragment.this, "Email not found", Toast.LENGTH_SHORT).show();
                    break;
                    case PASSWORD:
                        Toast.makeText(LoginFragment.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
    }
}