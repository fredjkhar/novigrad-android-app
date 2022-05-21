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

public class ModifyBranchActivity extends AppCompatActivity {

    private DatabaseReference database;

    private List<Branch> modifyBranchList;

    private ListView modifyBranchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_branch);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.activityModifyBranchLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        database = FirebaseDatabase.getInstance().getReference("Branches");
        modifyBranchList = new ArrayList<>();

        modifyBranchListView = findViewById(R.id.modifyBranchesListView);

        modifyBranchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ModifyBranchActivity.this, ModifyBranchesActivity.class);
                intent.putExtra("branch",modifyBranchList.get(i));
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
                modifyBranchList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Branch service = postSnapshot.getValue(Branch.class);
                    modifyBranchList.add(service);
                }
                BranchesListView serviceAdapter = new BranchesListView(ModifyBranchActivity.this, modifyBranchList);
                modifyBranchListView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}