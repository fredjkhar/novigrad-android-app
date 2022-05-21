package com.example.novigradv2.clientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.authentication.MainActivity;
import com.example.novigradv2.classes.Client;
import com.example.novigradv2.employeeActivities.EmployeeSectionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientActivity extends AppCompatActivity {

    private DatabaseReference clientDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.clientActivityLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        TextView clientGreeting = findViewById(R.id.clientGreetingTextView);
        clientGreeting.setText("Welcome");


        Button logOut = findViewById(R.id.clientLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ClientActivity.this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ClientActivity.this, MainActivity.class));
            }
        });

        TextView newRequest = findViewById(R.id.submitServiceTextView);
        TextView pastRequests = findViewById(R.id.viewRequestsTextView);
        TextView updateInfo = findViewById(R.id.updateAccountTextView);

        newRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientActivity.this,FindNewRequestActivity.class));
            }
        });

        pastRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientActivity.this, ViewPastRequestsActivity.class));
            }
        });
    }
}