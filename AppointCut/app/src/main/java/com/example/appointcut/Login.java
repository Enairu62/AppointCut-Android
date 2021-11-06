package com.example.appointcut;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Login extends AppCompatActivity {

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
                Intent intent = new Intent(Login.this,SignUp.class);
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

                String userText = inputUsername.getText().toString();
                String passText= inputPassword.getText().toString();

                if(userText.trim().isEmpty() || passText.trim().isEmpty()){
                    Toast.makeText(Login.this, "Please insert all necessary details.", Toast.LENGTH_SHORT).show();
                }
                else{
                    isValidCus = validateCus(userText, passText);
                    isValidBarber = validateBarber(userText, passText);

                    if (isValidCus) {
                        Toast.makeText(Login.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, HomePageCustomer.class);
                        intent.putExtra("fullName", "Customer");
                        startActivity(intent);
                    }
                    else if(isValidBarber){
                        Toast.makeText(Login.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, HomePageBarber.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Login.this, "Incorrect username and/or password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
}