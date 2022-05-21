package com.example.novigradv2.employeeActivities;

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
import com.example.novigradv2.classes.WorkHours;
import com.example.novigradv2.classes.WorkHoursListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateBranchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String province;

    private EditText city;
    private EditText address;
    private EditText codeZIP;

    private ListView timesListview;
    private Button continueButton;

    private List<WorkHours> workHoursList;

    private ToggleButton monday;
    private ToggleButton tuesday;
    private ToggleButton wednesday;
    private ToggleButton thursday;
    private ToggleButton friday;
    private ToggleButton saturday;
    private ToggleButton sunday;

    private ArrayAdapter<WorkHours> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_branch);

        //Background Animation code
        ScrollView constraintLayout = findViewById(R.id.createBranchLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        initialize();
        adapter = new WorkHoursListView(CreateBranchActivity.this, workHoursList);
        timesListview.setAdapter(adapter);
        //spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.provinceSpinner,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityString = city.getText().toString();
                String addressString = address.getText().toString();
                String codeZIPString = codeZIP.getText().toString();

                if (province == null) {
                    Toast.makeText(CreateBranchActivity.this, "Please select a province.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CreateBranchActivity.this, "You need to provide at least 4 work periods.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(CreateBranchActivity.this, SelectServicesActivity.class);
                intent.putExtra("city", cityString);
                intent.putExtra("address", addressString);
                intent.putExtra("province", province);
                intent.putExtra("codeZIP", codeZIPString);
                intent.putExtra("workHoursList", (Serializable) workHoursList );
                startActivity(intent);
            }
        });

    }

    private void initialize() {
        spinner = findViewById(R.id.provinceSpinner);
        spinner.setOnItemSelectedListener(CreateBranchActivity.this);

        city = findViewById(R.id.cityEditText);
        address = findViewById(R.id.addressEditText);
        codeZIP = findViewById(R.id.codeZIPEditText);

        timesListview = findViewById(R.id.timesListView);
        continueButton = findViewById(R.id.continueBranchButton);

        workHoursList = new ArrayList<>();

        monday = findViewById(R.id.mondayToggleButton);
        tuesday = findViewById(R.id.tuesdayToggleButton);
        wednesday = findViewById(R.id.wednesdayToggleButton);
        thursday = findViewById(R.id.thursdayToggleButton);
        friday = findViewById(R.id.fridayToggleButton);
        saturday = findViewById(R.id.saturdayToggleButton);
        sunday = findViewById(R.id.sundayToggleButton);


    }

    public void onToggleButtonClick(View view) {
        int id = view.getId();

        if (id == R.id.mondayToggleButton && monday.isChecked()) showAddDialog("Monday");
        if (id == R.id.tuesdayToggleButton && tuesday.isChecked()) showAddDialog("Tuesday");
        if (id == R.id.wednesdayToggleButton && wednesday.isChecked()) showAddDialog("Wednesday");
        if (id == R.id.thursdayToggleButton && thursday.isChecked()) showAddDialog("Thursday");
        if (id == R.id.fridayToggleButton && friday.isChecked()) showAddDialog("Friday");
        if (id == R.id.saturdayToggleButton && saturday.isChecked()) showAddDialog("Saturday");
        if (id == R.id.sundayToggleButton && sunday.isChecked()) showAddDialog("Sunday");

        if (!monday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Monday"));
        if (!tuesday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Tuesday"));
        if (!wednesday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Wednesday"));
        if (!thursday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Thursday"));
        if (!friday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Friday"));
        if (!saturday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Saturday"));
        if (!sunday.isChecked()) workHoursList.removeIf(workHours -> workHours.getDay().equals("Sunday"));
    }

    private void showAddDialog(String day) {
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
                validateWorkScheduleInput(startHourString, endHourString,startMinuteString,endMinuteString);

                if (startHourString.isEmpty() || startMinuteString.isEmpty() ||
                        endHourString.isEmpty() || endMinuteString.isEmpty()) {
                    Toast.makeText(CreateBranchActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(startHourString) > 23 || Integer.parseInt(endHourString) > 23) {
                    Toast.makeText(CreateBranchActivity.this, "Invalid hour. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(startMinuteString) > 59 || Integer.parseInt(endMinuteString) > 59) {
                    Toast.makeText(CreateBranchActivity.this, "Invalid minute. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(endHourString) <= Integer.parseInt(startHourString)) {
                    Toast.makeText(CreateBranchActivity.this, "Start time is after end time. Please try again ", Toast.LENGTH_SHORT).show();
                    return;
                }


                workHoursList.add(new WorkHours(day,Integer.parseInt(startHourString),Integer.parseInt(startMinuteString)
                        ,Integer.parseInt(endHourString),Integer.parseInt(endMinuteString)));
                alertDialog.dismiss();
                updateListView();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        province = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void updateListView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               timesListview.setAdapter(adapter);
            }
        });

    }

    public boolean validateWorkScheduleInput(String startHourString, String endHourString, String startMinuteString, String endMinuteString){

        if (startHourString.isEmpty() || startMinuteString.isEmpty() ||
                endHourString.isEmpty() || endMinuteString.isEmpty()) return false;

        if (Integer.parseInt(startHourString) > 23 || Integer.parseInt(endHourString) > 23) return false;

        if (Integer.parseInt(startMinuteString) > 59 || Integer.parseInt(endMinuteString) > 59) return false;

        if (Integer.parseInt(endHourString) <= Integer.parseInt(startHourString)) return false;

        return true;
    }
}