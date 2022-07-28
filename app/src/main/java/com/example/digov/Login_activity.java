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

public class Login_activity extends AppCompatActivity {

    TextView SignInPasswordTxt;
    TextView SignInUserEmailTxt;
    Button signInButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SignInUserEmailTxt = findViewById(R.id.SignInUserEmailIdTxt);
        SignInPasswordTxt = findViewById(R.id.SignInUserPasswordTxt);
        signInButton = (Button)findViewById(R.id.SignInBtn);

        mAuth = FirebaseAuth.getInstance();


        signInButton.setOnClickListener(v -> {

            LoginUser();
            Intent intent1 = new Intent(this , MainActivity.class);
            startActivity(intent1);
            finish();
        });

        Button signUpButton = (Button)findViewById(R.id.SignUpBtnFromSignIn);
        signUpButton.setOnClickListener(v->{
            Intent intent2 = new Intent(this , Register.class);
            startActivity(intent2);
            finish();
        });

    }

    private void LoginUser() {

        String Email = SignInUserEmailTxt.getText().toString();
        String password = SignInPasswordTxt.getText().toString();


        if(TextUtils.isEmpty(Email))
        {
            SignInUserEmailTxt.setError("Cannot be empty");
            SignInUserEmailTxt.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            SignInPasswordTxt.setError("Cannot be empty");
            SignInPasswordTxt.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(Email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login_activity.this , "User Logged in  successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Login_activity.this , MainActivity.class));
                    }
                    else{
                        Toast.makeText(Login_activity.this , "Log in  Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}