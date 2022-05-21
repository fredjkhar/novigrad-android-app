package com.example.novigradv2.employeeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;
import com.example.novigradv2.classes.WorkHours;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectServicesActivity extends AppCompatActivity {

    private ListView allServicesView;

    private Button finishButton;
    private Button viewSelectedServicesButton;

    private DatabaseReference database;

    private ServiceListView serviceAdapter;


    private List<WorkHours> workHoursList;
    private List<Service> serviceList;
    private List<Service> selectedServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_services);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.selectServicesLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        database = FirebaseDatabase.getInstance().getReference("Services");

        finishButton = findViewById(R.id.finishButton);
        viewSelectedServicesButton = findViewById(R.id.viewSelectedServicesButton);

        allServicesView = findViewById(R.id.selectServicesListView);

        workHoursList = new ArrayList<>();
        serviceList = new ArrayList<>();
        selectedServicesList = new ArrayList<>();


        String city = getIntent().getExtras().getString("city");
        String province = getIntent().getExtras().getString("province");
        String address = getIntent().getExtras().getString("address");
        String codeZIP = getIntent().getExtras().getString("codeZIP");

        workHoursList = (List<WorkHours>) getIntent().getSerializableExtra("workHoursList");


        allServicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedServicesList.add(serviceList.remove(i));
                serviceAdapter = new ServiceListView(SelectServicesActivity.this, serviceList);
                allServicesView.setAdapter(serviceAdapter);
                finishButton.setVisibility(View.VISIBLE);
                viewSelectedServicesButton.setVisibility(View.VISIBLE);
                return true;
            }
        });

        viewSelectedServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedServicesDialog();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Branches");
                String id = database.push().getKey();
                Branch branch = new Branch(id,city,province,address,codeZIP,workHoursList,selectedServicesList);
                database.child(id).setValue(branch);
                Toast.makeText(getApplicationContext(), "Branch created successfully.", Toast.LENGTH_LONG).show();

                startActivity(new Intent(SelectServicesActivity.this, EmployeeSectionActivity.class));
            }
        });
    }

    private void showSelectedServicesDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_selected_services_dialog, null);
        dialogBuilder.setView(dialogView);

        Button deleteAllServicesButton = dialogView.findViewById(R.id.deleteSelectedServicesButton);
        ListView allSelectedServicesView = dialogView.findViewById(R.id.viewSelectedServicesListView);

        ServiceListView selectedServicesAdapter = new ServiceListView(SelectServicesActivity.this, selectedServicesList);
        allSelectedServicesView.setAdapter(selectedServicesAdapter);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        deleteAllServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceList.addAll(selectedServicesList);
                selectedServicesList = new ArrayList<>();
                finishButton.setVisibility(View.GONE);
                viewSelectedServicesButton.setVisibility(View.GONE);

                serviceAdapter = new ServiceListView(SelectServicesActivity.this, serviceList);
                allServicesView.setAdapter(serviceAdapter);

                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(), "All injections were removed from selected services list successfully.", Toast.LENGTH_LONG).show();
            }
        });

    }

    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    serviceList.add(service);
                }
                serviceAdapter = new ServiceListView(SelectServicesActivity.this, serviceList);
                allServicesView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}