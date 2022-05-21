package com.example.novigradv2.employeeActivities;

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
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;

import java.util.List;

public class ViewServicesByBranchActivity extends AppCompatActivity {

    private List<Service> serviceList;
    private Branch branch;
    private ListView servicesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services_by_branch);

        //Background Animation code
        RelativeLayout constraintLayout = findViewById(R.id.viewServicesByBranchLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        branch = (Branch) getIntent().getSerializableExtra("branch");
        serviceList = branch.getServicesList();

        servicesListView = findViewById(R.id.viewServices);

        ServiceListView serviceAdapter = new ServiceListView(ViewServicesByBranchActivity.this, serviceList);
        servicesListView.setAdapter(serviceAdapter);

        servicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = serviceList.get(position);
                Intent intent = new Intent(ViewServicesByBranchActivity.this, ClientsRequestsActivity.class);
                intent.putExtra("branch", branch);
                intent.putExtra("service", service);
                startActivity(intent);
                return false;
            }
        });


    }


}