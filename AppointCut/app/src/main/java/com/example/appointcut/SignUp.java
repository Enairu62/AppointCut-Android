package com.example.appointcut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SignUp extends AppCompatActivity {

    private static String first, last, email, pass, contact, confirm;

    ImageView imageBack;

    EditText txtFirstName, txtLastName, txtEmailSignUp, txtPasswordSignUp, txtContact, txtConfirmPass;

    TextView linkTerms;

    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        declareViews();
        textFilters();
        signUpCondition();

        String termText = "Terms and Conditions";
        SpannableString ss = new SpannableString(termText);
        ss.setSpan(new UnderlineSpan(),0,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        linkTerms.setText(ss);

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
        imageBack = (ImageView) findViewById(R.id.imageViewBackXML);

        txtFirstName = (EditText) findViewById(R.id.txtFirstNameXML);
        txtLastName = (EditText) findViewById(R.id.txtLastNameXML);
        txtEmailSignUp = (EditText) findViewById(R.id.txtEmailSignUpXML);
        txtPasswordSignUp = (EditText) findViewById(R.id.txtPasswordSignUpXML);
        txtContact = (EditText) findViewById(R.id.txtContactXML);
        linkTerms = (TextView) findViewById(R.id.linkTerms);
        txtConfirmPass = (EditText) findViewById(R.id.txtConfirmPassXML);

        btnRegister = (Button) findViewById(R.id.btnRegisterXML);

    }
    //letters only for firstname and lastname edittext
    private void textFilters(){
        txtFirstName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        txtLastName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        txtFirstName.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
        txtLastName.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
        //11 digits only for contact edittext
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(11);
        txtContact.setFilters(filterArray);


    }

    private void signUpCondition(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first = txtFirstName.getText().toString();
                last= txtLastName.getText().toString();
                email = txtEmailSignUp.getText().toString();
                pass= txtPasswordSignUp.getText().toString();
                contact = txtContact.getText().toString();
                confirm = txtConfirmPass.getText().toString();

                if(first.trim().isEmpty() || last.trim().isEmpty() || email.trim().isEmpty() || pass.trim().isEmpty() || contact.trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Please insert all necessary details.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!pass.trim().equals(confirm.trim())){
                        Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(SignUp.this, HomePageCustomer.class);
                        startActivity(intent);
                    }
                }
            }
        });

    //password eye will be visible if the password editText has a value. Otherwise, it will be hidden
        txtPasswordSignUp.addTextChangedListener(new TextWatcher() {

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
                    txtConfirmPass.setEnabled(true);
                }
                else{
                    txtConfirmPass.setEnabled(false);
                }
            }
        });

    }
    //since di ako nakaconnect sa db. Nagtesting lang ako magpass ng data as argument then un method na ito will be called in profileCustomer fragment
    //i will fix pa un Ui ng profileCustomer fragment
    public static Bundle passDataIntoProfile(){
        Bundle args = new Bundle();
        args.putString("profileFirstName", first);
        args.putString("profileLastName", last);
        args.putString("profileEmail", email);
        args.putString("profileContact", contact);
        args.putString("profilePass", pass);
        return args;
    }
}