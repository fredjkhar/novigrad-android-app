package com.example.novigradv2.employeeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.RequestedService;
import com.example.novigradv2.classes.RequestedServiceListView;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientsRequestsActivity extends AppCompatActivity {

    private DatabaseReference database;

    private List<RequestedService> requestedServiceList;

    private ListView requestedServicesView;

    private ArrayAdapter<RequestedService> adapter;

    private Branch branch;

    private Service service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_requests);


        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.clientsRequestLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        database = FirebaseDatabase.getInstance().getReference("Requested");

        requestedServiceList = new ArrayList<>();

        requestedServicesView = findViewById(R.id.viewRequestedServicesListView);


        service = (Service) getIntent().getSerializableExtra("service");
        branch = (Branch) getIntent().getSerializableExtra("branch");



        requestedServicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RequestedService service = requestedServiceList.get(position);
                Intent intent = new Intent(ClientsRequestsActivity.this, ViewClientRequestedService.class);
                intent.putExtra("service",service);
                startActivity(intent);
                return true;
            }
        });

    }



    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestedServiceList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RequestedService RService = postSnapshot.getValue(RequestedService.class);
                    if (RService != null && RService.getService().getTitle().equals(service.getTitle()) && RService.getBranch().getCity().equals(branch.getCity()) )
                        requestedServiceList.add(RService);
                }
                adapter = new RequestedServiceListView(ClientsRequestsActivity.this, requestedServiceList);
                requestedServicesView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
