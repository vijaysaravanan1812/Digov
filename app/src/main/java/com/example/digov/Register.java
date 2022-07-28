package com.example.digov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextView EmailId ;
    TextView Password ;
    TextView ConfirmPassword ;
    Button SignUpButton, LoginHere;

    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EmailId = findViewById(R.id.SignUpEmailTxt);
        Password = findViewById(R.id.SignUpNewPasswordTxt);
        ConfirmPassword = findViewById(R.id.SignUpConfirmPasswordTxt);
        SignUpButton = findViewById(R.id.SignUpBtn) ;
        LoginHere = findViewById(R.id.LoginBtn);


        mAuth = FirebaseAuth.getInstance();

        SignUpButton.setOnClickListener(v->{
            createUser();
        });

        LoginHere.setOnClickListener(v->{
            startActivity(new Intent(Register.this , Login_activity.class));
        });


    }

    private void createUser() {
        String Email = EmailId.getText().toString();
        String password = Password.getText().toString();
        String confirm_password = ConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(Email))
        {
            EmailId.setError("Cannot be empty");
            EmailId.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            Password.setError("Cannot be empty");
            Password.requestFocus();
        }
        else if(!password.equals(confirm_password)){
            Password.setError("Not match");
            Password.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(Email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this , "User registered successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Register.this , Login_activity.class));
                    }
                    else{
                        Toast.makeText(Register.this , "User registration Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}