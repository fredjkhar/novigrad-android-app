package com.example.novigradv2.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.forgotPasswordLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        email = findViewById(R.id.emailReset);

        mAuth = FirebaseAuth.getInstance();

        Button resetPassword = findViewById(R.id.resetPassword);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    private void resetPassword(){
        String emailString = email.getText().toString();

        //email verification
        if (emailString.isEmpty() || emailString.equals("email")) {
            email.setError("Please enter your Email");
            email.requestFocus();
            return;
        } else if (!emailString.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(emailString).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPasswordActivity.this, "Please check your email to reset your password", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email you entered does not exist", Toast.LENGTH_LONG).show();
                    }
            }
        });

    }
}