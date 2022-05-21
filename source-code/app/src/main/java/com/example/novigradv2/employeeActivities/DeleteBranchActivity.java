package com.example.novigradv2.employeeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.BranchesListView;
import com.example.novigradv2.classes.ServiceListView;
import com.example.novigradv2.classes.WorkHoursListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteBranchActivity extends AppCompatActivity {

    private ListView deleteBranchesListView;

    private List<Branch> deleteBranchesList;

    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_branch);


        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.deleteBranchLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        deleteBranchesListView = findViewById(R.id.deleteBranchesListView);
        deleteBranchesList = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("Branches");

        deleteBranchesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch = deleteBranchesList.get(i);
                showDeleteDialog(branch);
                return true;
            }
        });
    }

    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deleteBranchesList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Branch service = postSnapshot.getValue(Branch.class);
                    deleteBranchesList.add(service);
                }
                BranchesListView serviceAdapter = new BranchesListView(DeleteBranchActivity.this, deleteBranchesList);
                deleteBranchesListView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showDeleteDialog(Branch branch) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_branch_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView textView = dialogView.findViewById(R.id.fullAddressTextView);
        ListView workScheduleListView = dialogView.findViewById(R.id.deleteBranchWorkScheduleListView);
        ListView associatedServicesListView = dialogView.findViewById(R.id.deleteBranchAssociatedServicesListView);
        Button deleteBranchButton = dialogView.findViewById(R.id.deleteBranchButtonDialogView);

        String branchName = (branch.getAddress()).concat(", ").concat(branch.getCity()).concat(", ")
                .concat(branch.getProvince()).concat(",  ").concat(branch.getCodeZIP());
        textView.setText("Full Address :".concat(branchName));

        WorkHoursListView serviceAdapter = new WorkHoursListView(DeleteBranchActivity.this,branch.getWorkHoursList());
        workScheduleListView.setAdapter(serviceAdapter);

        ServiceListView serviceListView = new ServiceListView(DeleteBranchActivity.this,branch.getServicesList());
        associatedServicesListView.setAdapter(serviceListView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        deleteBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Branches").child(branch.getId());
                databaseReference.removeValue();
                deleteBranchesList.remove(branch);
                BranchesListView serviceAdapter = new BranchesListView(DeleteBranchActivity.this, deleteBranchesList);
                deleteBranchesListView.setAdapter(serviceAdapter);
                Toast.makeText(DeleteBranchActivity.this, "Branch deleted successfully.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }


}