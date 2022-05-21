package com.example.novigradv2.adminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Client;
import com.example.novigradv2.classes.Employee;
import com.example.novigradv2.classes.ClientsListView;
import com.example.novigradv2.classes.EmployeesListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteUserAccountActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;

    private DatabaseReference employeesDatabase;
    private DatabaseReference clientsDatabase;

    private List<Employee> employeeList;
    private List<Client> clientList;

    private ListView clientsListView;
    private ListView employeesListView;

    private ToggleButton clientsToggle;
    private ToggleButton employeesToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_account);

        //Background Animation code
        RelativeLayout relativeLayout = findViewById(R.id.deleteUserAccountLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        //Instantiating variables
        initialize();


        clientsToggle = findViewById(R.id.clientAdminToggleButton);
        employeesToggle = findViewById(R.id.employeeAdminToggleButton);
        //On click listeners for toggle buttons
        employeesToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientsToggle.setChecked(false);
                clientsListView.setVisibility(View.GONE);
                EmployeesListView employeesAdapter = new EmployeesListView(DeleteUserAccountActivity.this, employeeList);
                employeesListView.setAdapter(employeesAdapter);
                employeesListView.setVisibility(View.VISIBLE);

            }
        });
        clientsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeesToggle.setChecked(false);
                employeesListView.setVisibility(View.GONE);
                ClientsListView clientsAdapter = new ClientsListView(DeleteUserAccountActivity.this, clientList);
                clientsListView.setAdapter(clientsAdapter);
                clientsListView.setVisibility(View.VISIBLE);
            }
        });

        //Set on long click listener for listview elements
        employeesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = employeeList.get(i);
                showDeleteDialog(employee);
                return true;
            }
        });

        clientsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client client = clientList.get(i);
                showDeleteDialog(client);
                return true;
            }
        });

        //Set on change listeners for both edit texts
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clientsListView.setVisibility(View.GONE);
                employeesListView.setVisibility(View.GONE);
                String emailString = email.getText().toString();
                List<Client> newClientList = new ArrayList<>();
                List<Employee> newEmployeeList = new ArrayList<>();

                if (clientsToggle.isChecked()) {
                    if (clientList != null) {
                        for (int k = 0; k < clientList.size(); k++) {
                            if (clientList.get(k).getEmail().contains(emailString)) {
                                newClientList.add(clientList.get(k));
                            }
                        }
                    }
                    ClientsListView clientsAdapter = new ClientsListView(DeleteUserAccountActivity.this, newClientList);
                    clientsListView.setAdapter(clientsAdapter);
                    clientsListView.setVisibility(View.VISIBLE);
                } else if (employeesToggle.isChecked()) {
                    if (employeeList != null) {
                        for (int k = 0; k < employeeList.size(); k++) {
                            if (employeeList.get(k).getEmail().contains(emailString)) {
                                newEmployeeList.add(employeeList.get(k));
                            }
                        }
                    }
                    EmployeesListView employeesAdapter = new EmployeesListView(DeleteUserAccountActivity.this, newEmployeeList);
                    employeesListView.setAdapter(employeesAdapter);
                    employeesListView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clientsListView.setVisibility(View.GONE);
                employeesListView.setVisibility(View.GONE);
                String usernameString = username.getText().toString();
                List<Client> newClientList = new ArrayList<>();
                List<Employee> newEmployeeList = new ArrayList<>();

                if (clientsToggle.isChecked()) {
                    if (clientList != null) {
                        for (int k = 0; k < clientList.size(); k++) {
                            if (clientList.get(k).getUsername().contains(usernameString)) {
                                newClientList.add(clientList.get(k));
                            }
                        }
                    }
                    ClientsListView clientsAdapter = new ClientsListView(DeleteUserAccountActivity.this, newClientList);
                    clientsListView.setAdapter(clientsAdapter);
                    clientsListView.setVisibility(View.VISIBLE);
                } else if (employeesToggle.isChecked()) {
                    if (employeeList != null) {
                        for (int k = 0; k < employeeList.size(); k++) {
                            if (employeeList.get(k).getUsername().contains(usernameString)) {
                                newEmployeeList.add(employeeList.get(k));
                            }
                        }
                    }
                    EmployeesListView employeesAdapter = new EmployeesListView(DeleteUserAccountActivity.this, newEmployeeList);
                    employeesListView.setAdapter(employeesAdapter);
                    employeesListView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void initialize() {
        employeeList = new ArrayList<>();
        clientList = new ArrayList<>();

        clientsListView = findViewById(R.id.clientListView);
        employeesListView = findViewById(R.id.employeeListView);

        email = findViewById(R.id.searchByEmailEditText);
        username = findViewById(R.id.searchByUsernameEditText);

        String emailString = email.getText().toString();
        String usernameString = username.getText().toString();

        employeesDatabase = FirebaseDatabase.getInstance().getReference("Employees");
        clientsDatabase = FirebaseDatabase.getInstance().getReference("Clients");



    }
    protected void onStart() {
        super.onStart();
        employeesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    employeeList.add(postSnapshot.getValue(Employee.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clientsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    clientList.add(postSnapshot.getValue(Client.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showDeleteDialog(Employee employee) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView firstName = (TextView) dialogView.findViewById(R.id.firstNameDeleteDialogx);
        TextView lastName = (TextView) dialogView.findViewById(R.id.lastNameDeleteDialog);
        TextView email = (TextView) dialogView.findViewById(R.id.emailDeleteDialog);
        TextView username = (TextView) dialogView.findViewById(R.id.usernameDeleteDialog);
        TextView password = (TextView) dialogView.findViewById(R.id.passwordDeleteDialog);

        Button deleteUser = dialogView.findViewById(R.id.buttonDeleteUser);

        firstName.setText("First name: ".concat(employee.getFirstName()));
        lastName.setText("Last name: ".concat(employee.getLastName()));
        email.setText("Email: ".concat(employee.getEmail()));
        username.setText("Username: ".concat(employee.getUsername()));
        password.setText("Password: ".concat(employee.getPassword()));


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();



        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeesDatabase.child(employee.getId()).removeValue();
                alertDialog.dismiss();
                employeesListView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDeleteDialog(Client client) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView firstName = (TextView) dialogView.findViewById(R.id.firstNameDeleteDialogx);
        TextView lastName = (TextView) dialogView.findViewById(R.id.lastNameDeleteDialog);
        TextView email = (TextView) dialogView.findViewById(R.id.emailDeleteDialog);
        TextView username = (TextView) dialogView.findViewById(R.id.usernameDeleteDialog);
        TextView password = (TextView) dialogView.findViewById(R.id.passwordDeleteDialog);

        Button deleteUser = dialogView.findViewById(R.id.buttonDeleteUser);

        firstName.setText("First name: ".concat(client.getFirstName()));
        lastName.setText("Last name: ".concat(client.getLastName()));
        email.setText("Email: ".concat(client.getEmail()));
        username.setText("Username: ".concat(client.getUsername()));
        password.setText("Password: ".concat(client.getPassword()));


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();



        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientsDatabase.child(client.getId()).removeValue();
                alertDialog.dismiss();
                clientsListView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }
}