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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.novigradv2.R;

import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.ServiceListView;
import com.example.novigradv2.classes.WorkHours;
import com.example.novigradv2.classes.WorkHoursListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ModifyBranchesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private Spinner spinner;
    private String province;

    private EditText city;
    private EditText address;
    private EditText codeZIP;

    private ListView timesListview;
    private ListView selectedListView;
    private ListView otherListView;

    private Button updateButton;
    private Button servicesButton;

    private List<WorkHours> workHoursList;
    private List<Service> allServicesList;
    private List<Service> selectedServicesList;


    private ToggleButton monday;
    private ToggleButton tuesday;
    private ToggleButton wednesday;
    private ToggleButton thursday;
    private ToggleButton friday;
    private ToggleButton saturday;
    private ToggleButton sunday;

    private String branchId;

    private Branch branch;

    private DatabaseReference database;
    private boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_branches);

        //Background Animation code
        ScrollView constraintLayout = findViewById(R.id.modifyBranchesLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        initialize();

        branch = (Branch) getIntent().getSerializableExtra("branch");
        String cityString = branch.getCity();
        String codeZIPString = branch.getCodeZIP();
        String addressString = branch.getAddress();
        branchId = branch.getId();
        workHoursList = branch.getWorkHoursList();
        selectedServicesList = branch.getServicesList();

        if (state) {
            onTimeMethod();
            state = false;
        }


        city.setText(cityString);
        address.setText(addressString);
        codeZIP.setText(codeZIPString);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.provinceSpinner,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityString = city.getText().toString();
                String addressString = address.getText().toString();
                String codeZIPString = codeZIP.getText().toString();

                if (province == null) {
                    Toast.makeText(ModifyBranchesActivity.this, "Please select a province.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cityString.isEmpty()) {
                    city.setError("Please provide a city name.");
                    city.requestFocus();
                    return;
                } else if (!cityString.matches("^[A-Za-z]+$")) {
                    city.setError("City name is invalid. Please try again.");
                    city.requestFocus();
                    return;
                }

                if (addressString.isEmpty()) {
                    address.setError("Please provide an address.");
                    address.requestFocus();
                    return;
                }

                if (codeZIPString.isEmpty()) {
                    codeZIP.setError("Please provide a ZIP code.");
                    codeZIP.requestFocus();
                    return;
                } else if (!codeZIPString.matches( "[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ] ?[0-9][ABCEGHJKLMNPRSTVWXYZ][0-9]")) {
                    codeZIP.setError("ZIP code is invalid. Please try again.");
                    codeZIP.requestFocus();
                    return;
                }



                if (workHoursList.size() < 4) {
                    Toast.makeText(ModifyBranchesActivity.this, "You need to provide at least 4 work periods.", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Branches");
                Branch branch = new Branch(branchId,cityString,province,addressString,codeZIPString,workHoursList,selectedServicesList);
                databaseReference.child(branchId).setValue(branch);
                startActivity(new Intent(ModifyBranchesActivity.this, EmployeeSectionActivity.class));
                Toast.makeText(ModifyBranchesActivity.this, "Branch successfully updated.", Toast.LENGTH_SHORT).show();
            }
        });

        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogView();
            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!monday.isChecked()) {
                    monday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Monday"));
                    updateWorkHours();
                }
                else { monday.setChecked(true);
                    showAddTimeDialog("Monday");
                }
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tuesday.isChecked()) {
                    tuesday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Tuesday"));
                    updateWorkHours();
                }
                else { tuesday.setChecked(true);
                    showAddTimeDialog("Tuesday");
                }
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wednesday.isChecked()) {
                    wednesday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Wednesday"));
                    updateWorkHours();
                }
                else { wednesday.setChecked(true);
                    showAddTimeDialog("Wednesday");
                }
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!thursday.isChecked()) {
                    thursday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Thursday"));
                    updateWorkHours();
                }
                else { thursday.setChecked(true);
                    showAddTimeDialog("Thursday");
                }
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!friday.isChecked()) {
                    friday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Friday"));
                    updateWorkHours();
                }
                else { friday.setChecked(true);
                    showAddTimeDialog("Friday");
                }
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saturday.isChecked()) {
                    saturday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Saturday"));
                    updateWorkHours();
                }
                else{ saturday.setChecked(true);
                    showAddTimeDialog("Saturday");
                }
            }
        });
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sunday.isChecked()) {
                    sunday.setChecked(false);
                    workHoursList.removeIf(workHours -> workHours.getDay().equals("Sunday"));
                    updateWorkHours();
                }
                else { sunday.setChecked(true);
                    showAddTimeDialog("Sunday");
                }
            }
        });

        timesListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                WorkHours workHours = workHoursList.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ModifyBranchesActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_hours_dialog, null);
                dialogBuilder.setView(dialogView);

                EditText startHour = dialogView.findViewById(R.id.startHourEditText);
                EditText startMinute = dialogView.findViewById(R.id.startMinuteEditText);
                EditText endHour = dialogView.findViewById(R.id.endHourEditText);
                EditText endMinute = dialogView.findViewById(R.id.endMinuteEditText);

                Button addButton = dialogView.findViewById(R.id.addTimeButton);

                startHour.setText(String.valueOf(workHours.getStartHour()));
                startMinute.setText(String.valueOf(workHours.getStartMinute()));

                endHour.setText(String.valueOf(workHours.getEndHour()));
                endMinute.setText(String.valueOf(workHours.getEndMinute()));

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String startHourString = startHour.getText().toString();
                        String startMinuteString = startMinute.getText().toString();
                        String endHourString = endHour.getText().toString();
                        String endMinuteString = endMinute.getText().toString();

                        if (startHourString.isEmpty() || startMinuteString.isEmpty() ||
                                endHourString.isEmpty() || endMinuteString.isEmpty()) {
                            Toast.makeText(ModifyBranchesActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (Integer.parseInt(startHourString) > 23 || Integer.parseInt(endHourString) > 23) {
                            Toast.makeText(ModifyBranchesActivity.this, "Invalid hour. Please try again.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Integer.parseInt(startMinuteString) > 59 || Integer.parseInt(endMinuteString) > 59) {
                            Toast.makeText(ModifyBranchesActivity.this, "Invalid minute. Please try again.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        workHoursList.set(position,new WorkHours(workHours.getDay(),Integer.parseInt(startHourString),Integer.parseInt(startMinuteString)
                                ,Integer.parseInt(endHourString),Integer.parseInt(endMinuteString)));
                        updateWorkHours();
                        alertDialog.dismiss();

                    }
                });
                return true;
            }
        });
    }
    private void updateWorkHours() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Branches").child(branchId)
                .child("workHoursList");
        databaseReference.setValue(workHoursList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workHoursList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    WorkHours service = postSnapshot.getValue(WorkHours.class);
                    workHoursList.add(service);
                }
                WorkHoursListView serviceAdapter = new WorkHoursListView(ModifyBranchesActivity.this,  workHoursList);
                timesListview.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void showAddTimeDialog(String day) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_hours_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText startHour = dialogView.findViewById(R.id.startHourEditText);
        EditText startMinute = dialogView.findViewById(R.id.startMinuteEditText);
        EditText endHour = dialogView.findViewById(R.id.endHourEditText);
        EditText endMinute = dialogView.findViewById(R.id.endMinuteEditText);

        Button addButton = dialogView.findViewById(R.id.addTimeButton);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startHourString = startHour.getText().toString();
                String startMinuteString = startMinute.getText().toString();
                String endHourString = endHour.getText().toString();
                String endMinuteString = endMinute.getText().toString();

                if (startHourString.isEmpty() || startMinuteString.isEmpty() ||
                        endHourString.isEmpty() || endMinuteString.isEmpty()) {
                    Toast.makeText(ModifyBranchesActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(startHourString) > 23 || Integer.parseInt(endHourString) > 23) {
                    Toast.makeText(ModifyBranchesActivity.this, "Invalid hour. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(startMinuteString) > 59 || Integer.parseInt(endMinuteString) > 59) {
                    Toast.makeText(ModifyBranchesActivity.this, "Invalid minute. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(endHourString) <= Integer.parseInt(startHourString)) {
                    Toast.makeText(ModifyBranchesActivity.this, "End hour is lower or equal to start hour. Please try again ", Toast.LENGTH_SHORT).show();
                    return;
                }

                workHoursList.add(new WorkHours(day,Integer.parseInt(startHourString),Integer.parseInt(startMinuteString)
                        ,Integer.parseInt(endHourString),Integer.parseInt(endMinuteString)));
                alertDialog.dismiss();
                updateWorkHours();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance().getReference("Services");
        database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allServicesList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Service service = postSnapshot.getValue(Service.class);
                        allServicesList.add(service);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        for(Service service : selectedServicesList) allServicesList.remove(service);

    }

    private void initialize() {
        spinner = findViewById(R.id.provinceModifySpinner);
        spinner.setOnItemSelectedListener(ModifyBranchesActivity.this);

        city = findViewById(R.id.cityModifyEditText);
        address = findViewById(R.id.addressModifyEditText);
        codeZIP = findViewById(R.id.codeZIPModifyEditText);

        timesListview = findViewById(R.id.timesModifyListView);

        workHoursList = new ArrayList<>();
        selectedServicesList = new ArrayList<>();
        allServicesList = new ArrayList<>();


        monday = (ToggleButton) findViewById(R.id.mondayToggleButton1);
        tuesday = (ToggleButton) findViewById(R.id.tuesdayToggleButton1);
        wednesday = (ToggleButton) findViewById(R.id.wednesdayToggleButton1);
        thursday = (ToggleButton) findViewById(R.id.thursdayToggleButton1);
        friday = (ToggleButton) findViewById(R.id.fridayToggleButton1);
        saturday = (ToggleButton) findViewById(R.id.saturdayToggleButton1);
        sunday = (ToggleButton) findViewById(R.id.sundayToggleButton1);

        updateButton = findViewById(R.id.updateBranchButton);
        servicesButton = findViewById(R.id.viewSelectedServices);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        province = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void showDialogView() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.modify_selected_services_dialog, null);
        dialogBuilder.setView(dialogView);

        selectedListView = dialogView.findViewById(R.id.selectedServicesListView1);
        otherListView = dialogView.findViewById(R.id.restOfServicesListView);
        Button finish = dialogView.findViewById(R.id.doneBruddah);



        ArrayAdapter<Service> adapter = new ServiceListView(ModifyBranchesActivity.this, selectedServicesList);
        selectedListView.setAdapter(adapter);

        ArrayAdapter<Service> adapter1 = new ServiceListView(ModifyBranchesActivity.this, allServicesList);
        otherListView.setAdapter(adapter1);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        selectedListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Service service = selectedServicesList.get(position);
                        selectedServicesList.remove(service);
                        allServicesList.add(service);
                        selectedListView.setAdapter(adapter);
                        otherListView.setAdapter(adapter1);
                    }
                });
                return true;
            }
        });
        otherListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Service service = allServicesList.get(position);
                        allServicesList.remove(service);
                        selectedServicesList.add(service);
                        selectedListView.setAdapter(adapter);
                        otherListView.setAdapter(adapter1);
                    }
                });
                return true;
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void onTimeMethod() {
        for (WorkHours workHours : workHoursList) {
            if (workHours.getDay().equals("Monday")) monday.setChecked(true);
            if (workHours.getDay().equals("Tuesday")) tuesday.setChecked(true);
            if (workHours.getDay().equals("Wednesday")) wednesday.setChecked(true);
            if (workHours.getDay().equals("Thursday")) thursday.setChecked(true);
            if (workHours.getDay().equals("Friday")) friday.setChecked(true);
            if (workHours.getDay().equals("Saturday")) saturday.setChecked(true);
            if (workHours.getDay().equals("Sunday")) sunday.setChecked(true);
        }
        WorkHoursListView workListViewAdapter = new WorkHoursListView(ModifyBranchesActivity.this,  workHoursList);
        timesListview.setAdapter(workListViewAdapter);
    }
}