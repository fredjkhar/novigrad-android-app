package com.example.novigradv2.clientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.BranchesListView;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseServiceActivity extends AppCompatActivity {

    private ListView services;
    private Button scheduleAppointment;

    private List<Service> servicesList;

    private DatabaseReference database;
    private ArrayAdapter<Service> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.chooseServiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        Branch branch = (Branch) getIntent().getSerializableExtra("branch");

        TextView address = findViewById(R.id.branchAddress);
        address.setText((branch.getAddress()).concat(", ").concat(branch.getCity()).concat(", ")
                .concat(branch.getProvince()).concat(",  ").concat(branch.getCodeZIP()));

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        int[] ratings = branch.getRatings();
        if (ratings == null) ratingBar.setNumStars(0);
        else {
            int sum = 0;
            for (Integer integer : ratings) sum = sum + integer;
            sum = sum/ratings.length;
            ratingBar.setNumStars(sum);
        }


        services = findViewById(R.id.offeredServices);
        servicesList = branch.getServicesList();
        adapter = new ServiceListView(ChooseServiceActivity.this, servicesList);
        services.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference("Services");

        scheduleAppointment = findViewById(R.id.scheduleAppointmentButton);
        scheduleAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        services.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseServiceActivity.this, FilloutFormActivity.class);
                intent.putExtra("branch",branch);
                intent.putExtra("service",servicesList.get(position));
                startActivity(intent);
                return false;
            }
        });


    }
}