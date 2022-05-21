package com.example.novigradv2.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.novigradv2.R;

public class RoleChoiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.roleChoiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        //Make sure when one of the toggle buttons is checked, the other one is not
        ToggleButton employeeToggleButton = findViewById(R.id.employeeToggleButton);
        ToggleButton clientToggleChecked = findViewById(R.id.clientToggleButton);

        employeeToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { clientToggleChecked.setChecked(false);}
        });

        clientToggleChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { employeeToggleButton.setChecked(false);}
        });

        //Setting a on click listener to continue button
        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoleChoiceActivity.this, RegisterActivity.class);
                if (clientToggleChecked.isChecked()) {
                    intent.putExtra("Role","Client");
                    startActivity(intent);
                } else if (employeeToggleButton.isChecked()) {
                    intent.putExtra("Role","Employee");
                    startActivity(intent);
                } else Toast.makeText(RoleChoiceActivity.this, "Please select your role", Toast.LENGTH_LONG).show();
            }
        });


    }
}