package com.example.novigradv2.employeeActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.novigradv2.R;
import com.example.novigradv2.authentication.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeSectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.employeeLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        TextView createBranchButton = findViewById(R.id.createBranchButton);
        TextView viewBranchButton = findViewById(R.id.viewBranchButton);
        TextView deleteBranchButton = findViewById(R.id.deleteBranchButton);
        TextView modifyBranchButton = findViewById(R.id.modifyBranchButton);

        Button logout = findViewById(R.id.logoutEmployeeButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EmployeeSectionActivity.this, MainActivity.class));
            }
        });

        createBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeSectionActivity.this, CreateBranchActivity.class));
            }
        });
        viewBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeSectionActivity.this, ViewBranchesActivity.class));
            }
        });
        deleteBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeSectionActivity.this, DeleteBranchActivity.class));
            }
        });
        modifyBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeSectionActivity.this, ModifyBranchActivity.class));
            }
        });
    }
}