package com.example.novigradv2.adminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ModifyServiceActivity extends AppCompatActivity {

    List<Service> list;

    private ListView allServicesView;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service);

        RelativeLayout relativeLayout = findViewById(R.id.modifyServiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        list = new ArrayList<>();


        database = FirebaseDatabase.getInstance().getReference("Services");
        allServicesView = findViewById(R.id.modifyServiceListView);

        allServicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = list.get(i);
                String serviceId = service.getId();
                Intent intent = new Intent(ModifyServiceActivity.this,UpdateServiceActivity.class);
                intent.putExtra("id",serviceId);
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
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    list.add(service);
                }
                ServiceListView serviceAdapter = new ServiceListView(ModifyServiceActivity.this, list);
                allServicesView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}