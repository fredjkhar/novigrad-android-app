package com.example.novigradv2.clientActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.BranchesListView;
import com.example.novigradv2.classes.RequestedService;
import com.example.novigradv2.classes.RequestedServiceListView;
import com.example.novigradv2.classes.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindNewRequestActivity extends AppCompatActivity {

    private EditText searchByAddress;
    private EditText searchByService;
    private EditText searchByWorkHours;

    private ListView searchByBranch;
    private List<Branch> branchesList;

    private DatabaseReference database;

    private BranchesListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_new_request);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.findNewRequestLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();



        searchByAddress = findViewById(R.id.searchByAddress);
        searchByService = findViewById(R.id.searchByServices);
        searchByWorkHours = findViewById(R.id.searchByWorkHours); //to be implemented

        searchByBranch = findViewById(R.id.searchBranchListView);
        branchesList = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("Branches");

        searchByAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchByAddress.getText().toString().equals("") && searchByService.getText().toString().equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new BranchesListView(FindNewRequestActivity.this, branchesList);
                            searchByBranch.setAdapter(adapter);
                        }
                    });
                }
                String address = searchByAddress.getText().toString();
                List<Branch> newList = new ArrayList<>();

                for (Branch branch : branchesList) {
                    if (branch.getAddress().toLowerCase().trim().contains(address.toLowerCase().trim()) || branch.getCity().toLowerCase().trim()
                            .contains(address.toLowerCase().trim())
                                || branch.getProvince().toLowerCase().trim().contains(address.toLowerCase().trim())) newList.add(branch);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new BranchesListView(FindNewRequestActivity.this, newList);
                        searchByBranch.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchByService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchByAddress.getText().toString().equals("") && searchByService.getText().toString().equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new BranchesListView(FindNewRequestActivity.this, branchesList);
                            searchByBranch.setAdapter(adapter);
                        }
                    });
                }
                String ser = searchByService.getText().toString();
                List<Branch> newList = new ArrayList<>();

                for (Branch branch : branchesList) {
                    for (Service service : branch.getServicesList()) {
                        if (service.getTitle().toLowerCase().contains(ser.toLowerCase())) {
                            newList.add(branch);
                            break;
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new BranchesListView(FindNewRequestActivity.this, newList);
                        searchByBranch.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        searchByBranch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch = branchesList.get(position);
                Intent intent = new Intent(FindNewRequestActivity.this, ChooseServiceActivity.class);
                intent.putExtra("branch",branch);
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
                branchesList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Branch service = postSnapshot.getValue(Branch.class);
                    branchesList.add(service);
                }
                adapter = new BranchesListView(FindNewRequestActivity.this, branchesList);
                searchByBranch.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}