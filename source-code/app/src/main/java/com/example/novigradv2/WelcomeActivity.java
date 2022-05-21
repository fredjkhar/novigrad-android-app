package com.example.novigradv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.novigradv2.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.welcomeActivityLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        TextView welcome = findViewById(R.id.textViewWelcome);
        TextView connected = findViewById(R.id.textViewConnected);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        database.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user!=null) {
                    String firstName = user.getFirstName();
                    String type = user.getType();

                    welcome.setText("Welcome, ".concat(firstName));
                    connected.setText("you are connected as ".concat(type));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}