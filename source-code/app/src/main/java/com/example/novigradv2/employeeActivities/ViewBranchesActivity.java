package com.example.novigradv2.employeeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.BranchesListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBranchesActivity extends AppCompatActivity {

    private DatabaseReference database;
    private List<Branch> branchList;

    private ListView branchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_beanches);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.viewBranchesLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        branchList = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("Branches");
        branchListView = findViewById(R.id.viewBranches);

        branchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Branch branch = branchList.get(position);
                Intent intent = new Intent(ViewBranchesActivity.this,ViewServicesByBranchActivity.class);
                intent.putExtra("branch", branch);
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
                branchList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Branch service = postSnapshot.getValue(Branch.class);
                    branchList.add(service);
                }
                BranchesListView serviceAdapter = new BranchesListView(ViewBranchesActivity.this, branchList);
                branchListView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}