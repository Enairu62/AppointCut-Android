package com.example.appointcut;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextView linkSignUp;
    private EditText txtEmail;
    private EditText txtPassword;
    Button btnLogin;
    TextInputLayout layoutEyePass;

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

        linkSignUp = (TextView) findViewById(R.id.linkSignUpXML);
        txtEmail = (EditText) findViewById(R.id.txtEmailAddXML);
        txtPassword = (EditText) findViewById(R.id.txtPasswordXML);
        btnLogin = (Button) findViewById(R.id.btnLoginXML);
        layoutEyePass = (TextInputLayout) findViewById(R.id.layoutPassXML);
        layoutEyePass.setEndIconVisible(false);

        loginCondition();
        eyePassMethod();
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

                String userText = txtEmail.getText().toString();
                String passText= txtPassword.getText().toString();

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
    //password eye will be visible if the password editText has a value. Otherwise, it will be hidden
    private void eyePassMethod(){
        txtPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    layoutEyePass.setEndIconVisible(true);
                }
                else{
                    layoutEyePass.setEndIconVisible(false);
                }
            }
        });
    }
}