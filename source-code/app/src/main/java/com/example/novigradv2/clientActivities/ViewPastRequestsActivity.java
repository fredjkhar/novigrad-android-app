package com.example.novigradv2.clientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.RequestedService;
import com.example.novigradv2.classes.RequestedServiceListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPastRequestsActivity extends AppCompatActivity {

    private DatabaseReference database;
    private List<RequestedService> list;
    private ListView allServicesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_requests);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.viewPastRequestsActivityLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        database = FirebaseDatabase.getInstance().getReference("Requested");
        list = new ArrayList<>();
        allServicesView = findViewById(R.id.pastRequestsLitView);

    }

    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RequestedService service = postSnapshot.getValue(RequestedService.class);
                    list.add(service);
                }
                RequestedServiceListView serviceAdapter = new RequestedServiceListView(ViewPastRequestsActivity.this, list);
                allServicesView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}