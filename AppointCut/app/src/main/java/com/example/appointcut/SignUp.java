package com.example.appointcut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragments.FragmentSchedule;

public class SignUp extends AppCompatActivity {

    ImageView imageBack;

    EditText inputFirstName, inputLastName, inputUserNameSignUp, inputPasswordSignUp, inputContact, inputEmailAdd;

    TextView linkTerms;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        declareViews();

        String termText = "Terms and Conditions";
        SpannableString ss = new SpannableString(termText);
        ss.setSpan(new UnderlineSpan(),0,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        linkTerms.setText(ss);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = inputFirstName.getText().toString();
                String last= inputLastName.getText().toString();
                String user = inputUserNameSignUp.getText().toString();
                String pass= inputPasswordSignUp.getText().toString();
                String contact = inputContact.getText().toString();
                String email= inputEmailAdd.getText().toString();

                String fullName = first + " " + last;

                if(first.trim().isEmpty() || last.trim().isEmpty() || user.trim().isEmpty() || pass.trim().isEmpty() || contact.trim().isEmpty() || email.trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Please insert all necessary details.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SignUp.this, HomePageCustomer.class);
                    intent.putExtra("fullName", fullName);
                    startActivity(intent);
                }
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void declareViews(){
        imageBack = (ImageView) findViewById(R.id.imageView);

        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputLastName = (EditText) findViewById(R.id.inputLastName);
        inputUserNameSignUp = (EditText) findViewById(R.id.inputUserNameSignUp);
        inputPasswordSignUp = (EditText) findViewById(R.id.inputPasswordSignUp);
        inputContact = (EditText) findViewById(R.id.inputContact);
        inputEmailAdd = (EditText) findViewById(R.id.inputEmailAdd);
        linkTerms = (TextView) findViewById(R.id.linkTerms);

        btnRegister = (Button) findViewById(R.id.btnRegister);
    }
}