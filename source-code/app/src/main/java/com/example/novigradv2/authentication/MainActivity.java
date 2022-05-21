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
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.WelcomeActivity;
import com.example.novigradv2.adminActivities.AdminActivity;
import com.example.novigradv2.classes.Client;
import com.example.novigradv2.classes.Employee;
import com.example.novigradv2.clientActivities.ClientActivity;
import com.example.novigradv2.employeeActivities.EmployeeSectionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.loginLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        email = findViewById(R.id.emailOrUserNameEditTextLogin);
        password = findViewById(R.id.passwordEditTextLogin);

        mAuth = FirebaseAuth.getInstance();

        //Register Button
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RoleChoiceActivity.class));
            }
        });

        //Forgot password button
        TextView forgotPasswordEditText = findViewById(R.id.forgotPasswordEditText);
        forgotPasswordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class)); }
        });

        //Login button
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }



    private void loginUser(){
        String emailOrUsernameString = email.getText().toString();
        String passwordString = password.getText().toString();

        if (emailOrUsernameString.equals("admin") && passwordString.equals("123admin456")) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                return;
        }

        //Email Verification
        if (emailOrUsernameString.isEmpty() || emailOrUsernameString.equals("email")) {
            email.setError("Please enter your Email");
            email.requestFocus();
            return;
        } else if (!emailOrUsernameString.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }

        //Password Verification
        if (passwordString.isEmpty() || passwordString.equals("password")) {
            password.setError("Please enter your Password");
            password.requestFocus();
            return;
        } else if (passwordString.length()< 6) {
            password.setError("Password must contain at least six elements");
            password.requestFocus();
            return;
        }

        //Email Verification
        if (emailOrUsernameString.isEmpty() || emailOrUsernameString.equals("email")) {
            email.setError("Please enter your Email");
            email.requestFocus();
            return;
        } else if (!emailOrUsernameString.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.setError("Please enter a valid Email");
            email.requestFocus();
            return;
        }



        mAuth.signInWithEmailAndPassword(emailOrUsernameString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userEmail = user.getEmail();

                    DatabaseReference employeesDatabase = FirebaseDatabase.getInstance().getReference("Employees");

                    employeesDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                Employee employee = postSnapshot.getValue(Employee.class);
                                if (employee != null && employee.getEmail().equals(userEmail)) {
                                    startActivity(new Intent(MainActivity.this, EmployeeSectionActivity.class));
                                    return;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    DatabaseReference clientsDatabase = FirebaseDatabase.getInstance().getReference("Clients");

                    clientsDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                Client client = postSnapshot.getValue(Client.class);
                                if (client != null && client.getEmail().equals(userEmail)) {
                                    Intent intent = new Intent(MainActivity.this, ClientActivity.class);
                                    intent.putExtra("firstName",client.getFirstName());
                                    startActivity(intent);
                                    return;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Wrong email or password. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}