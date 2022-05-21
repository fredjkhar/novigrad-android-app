package com.example.novigradv2.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.authentication.MainActivity;
import com.example.novigradv2.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.adminActivityLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        Button logout = findViewById(R.id.logoutAdminButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminActivity.this, "Successfully logged out.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminActivity.this, MainActivity.class));
            }
        });

        TextView deleteUser = findViewById(R.id.deleteUserButton);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, DeleteUserAccountActivity.class));
            }
        });

        TextView addService = findViewById(R.id.addServiceButton);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AddServicesAdmin.class));
            }
        });

        TextView deleteService = findViewById(R.id.deleteServiceButton);
        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,DeleteServiceActivity.class));
            }
        });

        TextView modifyService = findViewById(R.id.modifyServiceButton);
        modifyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,ModifyServiceActivity.class));
            }
        });

        TextView viewServices = findViewById(R.id.viewServicesButton);
        viewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminActivity.this, "Not implemented yet.", Toast.LENGTH_LONG).show();
            }
        });
    }

}