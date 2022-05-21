package com.example.novigradv2.adminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.example.novigradv2.classes.Client;
import com.example.novigradv2.classes.ClientsListView;
import com.example.novigradv2.classes.Employee;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DeleteServiceActivity extends AppCompatActivity {

    private ListView allServicesView;

    private DatabaseReference database;

    private List<Service> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service);

        //Background Animation code
        RelativeLayout relativeLayout = findViewById(R.id.deleteServiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("Services");
        allServicesView = findViewById(R.id.deleteServiceListView);

        allServicesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = list.get(i);
                showDeleteDialog(service);
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
                ServiceListView serviceAdapter = new ServiceListView(DeleteServiceActivity.this, list);
                allServicesView.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showDeleteDialog(Service service) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_modify_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView title = (TextView) dialogView.findViewById(R.id.titleDeleteDialog);
        TextView price = (TextView) dialogView.findViewById(R.id.priceDeleteDialog);


        Button deleteUser = dialogView.findViewById(R.id.buttonDeleteUser);

        title.setText("Title : ".concat(service.getTitle()));
        price.setText("Price : ".concat(service.getPrice()));

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();



        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        database.child(service.getId()).removeValue();
                        database.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                list.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Service service = postSnapshot.getValue(Service.class);
                                    list.add(service);
                                }
                                ServiceListView serviceAdapter = new ServiceListView(DeleteServiceActivity.this, list);
                                allServicesView.setAdapter(serviceAdapter);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

}