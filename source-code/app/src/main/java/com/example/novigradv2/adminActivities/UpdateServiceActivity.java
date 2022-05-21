package com.example.novigradv2.adminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateServiceActivity extends AppCompatActivity {

    private EditText price;
    private EditText title;

    private Button updateButton;

    private String serviceId;

    private List<String> textFieldsList;
    private List<String> numericalFieldsList;
    private List<String> documentFieldsList;

    private DatabaseReference database;

    private EditText[] arrayOfTextFields;
    private EditText[] arrayOfNumericalFields;
    private EditText[] arrayOfDocumentFields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);


        ScrollView scrollViewLayout = findViewById(R.id.updateServiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) scrollViewLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        initialize();
        serviceId = getIntent().getExtras().getString("id");


        database = FirebaseDatabase.getInstance().getReference("Services");
        start();



        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> newTextFieldsList = new ArrayList<>();
                List<String> newNumericalFieldsList = new ArrayList<>();
                List<String> newDocumentFieldsList = new ArrayList<>();

                String newTitle = title.getText().toString();
                String newPrice = price.getText().toString();

                int j = 0;
                while (arrayOfTextFields[j] != null && textFieldsList.size() > j) {
                    if (arrayOfTextFields[j].getText().toString().isEmpty()) {
                        arrayOfTextFields[j].setError("Please fill the field");
                        arrayOfTextFields[j].requestFocus();
                        return;
                    }
                    newTextFieldsList.add(arrayOfTextFields[j].getText().toString());
                    j++;
                }

                j = 0;
                while (arrayOfNumericalFields[j] != null && numericalFieldsList.size() > j) {
                    if (arrayOfNumericalFields[j].getText().toString().isEmpty()) {
                        arrayOfNumericalFields[j].setError("Please fill the field");
                        arrayOfNumericalFields[j].requestFocus();
                        return;
                    }
                    newNumericalFieldsList.add(arrayOfNumericalFields[j].getText().toString());
                    j++;
                }

                j = 0;
                while (arrayOfDocumentFields[j] != null && documentFieldsList.size() > j) {
                    if (arrayOfDocumentFields[j].getText().toString().isEmpty()) {
                        arrayOfDocumentFields[j].setError("Please fill the field");
                        arrayOfDocumentFields[j].requestFocus();
                        return;
                    }
                    newDocumentFieldsList.add(arrayOfDocumentFields[j].getText().toString());
                    j++;
                }

                Service service = new Service(serviceId,newTitle,newPrice,newTextFieldsList,newNumericalFieldsList,newDocumentFieldsList);
                database.child(serviceId).setValue(service);
                Toast.makeText(UpdateServiceActivity.this, "Service updated successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateServiceActivity.this, AdminActivity.class));
            }
        });



    }

    private void start() {
        database.child(serviceId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Service service = snapshot.getValue(Service.class);

                title.setText(service.getTitle());
                price.setText(service.getPrice());


                textFieldsList = service.getTextFields();
                numericalFieldsList = service.getNumericalFields();
                documentFieldsList = service.getDocumentFields();


                int nbrOfTextFields = textFieldsList.size();

                int nbrOfNumericalFields = numericalFieldsList.size();

                int nbrOfDocumentFields = documentFieldsList.size();


                for (int i = 4; i >= nbrOfDocumentFields; i--) { arrayOfDocumentFields[i].setVisibility(View.GONE); }

                for (int i = 9; i >= nbrOfNumericalFields; i--) { arrayOfNumericalFields[i].setVisibility(View.GONE); }

                for (int i = 9; i >= nbrOfTextFields; i--) { arrayOfTextFields[i].setVisibility(View.GONE); }


                int j = 0;
                while (arrayOfTextFields.length > j && textFieldsList.size() > j && arrayOfTextFields[j] != null) { arrayOfTextFields[j].setText(textFieldsList.get(j));
                    j++; }

                j = 0;
                while (arrayOfNumericalFields.length > j && numericalFieldsList.size() > j && arrayOfNumericalFields[j] != null) {
                    arrayOfNumericalFields[j].setText(numericalFieldsList.get(j));
                    j++;
                }

                j = 0;
                while (arrayOfDocumentFields.length > j && documentFieldsList.size() > j && arrayOfDocumentFields[j] != null) {
                    arrayOfDocumentFields[j].setText(documentFieldsList.get(j));
                    j++;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateServiceActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initialize(){

        EditText textField1 = findViewById(R.id.textFieldUpdate1);
        EditText textField2 = findViewById(R.id.textFieldUpdate2);
        EditText textField3 = findViewById(R.id.textFieldUpdate3);
        EditText textField4 = findViewById(R.id.textFieldUpdate4);
        EditText textField5 = findViewById(R.id.textFieldUpdate5);
        EditText textField6 = findViewById(R.id.textFieldUpdate6);
        EditText textField7 = findViewById(R.id.textFieldUpdate7);
        EditText textField8 = findViewById(R.id.textFieldUpdate8);
        EditText textField9 = findViewById(R.id.textFieldUpdate9);
        EditText textField10 = findViewById(R.id.textFieldUpdate10);

        EditText numericalField1 = findViewById(R.id.numericalFieldUpdate1);
        EditText numericalField2 = findViewById(R.id.numericalFieldUpdate2);
        EditText numericalField3 = findViewById(R.id.numericalFieldUpdate3);
        EditText numericalField4 = findViewById(R.id.numericalFieldUpdate4);
        EditText numericalField5 = findViewById(R.id.numericalFieldUpdate5);
        EditText numericalField6 = findViewById(R.id.numericalFieldUpdate6);
        EditText numericalField7 = findViewById(R.id.numericalFieldUpdate7);
        EditText numericalField8 = findViewById(R.id.numericalFieldUpdate8);
        EditText numericalField9 = findViewById(R.id.numericalFieldUpdate9);
        EditText numericalField10 = findViewById(R.id.numericalFieldUpdate10);

        EditText documentField1 = findViewById(R.id.documentFieldUpdate1);
        EditText documentField2 = findViewById(R.id.documentFieldUpdate2);
        EditText documentField3 = findViewById(R.id.documentFieldUpdate3);
        EditText documentField4 = findViewById(R.id.documentFieldUpdate4);
        EditText documentField5 = findViewById(R.id.documentFieldUpdate5);

        price = (EditText) findViewById(R.id.priceEditTextUpdate);
        title = (EditText) findViewById(R.id.titleEditTextUpdate);

        updateButton = findViewById(R.id.updateServiceButton);

        textFieldsList = new ArrayList<>();
        numericalFieldsList = new ArrayList<>();
        documentFieldsList = new ArrayList<>();

        arrayOfDocumentFields = new EditText[5];
        arrayOfTextFields = new EditText[10];
        arrayOfNumericalFields = new EditText[10];

        arrayOfDocumentFields[0] = documentField1;
        arrayOfDocumentFields[1] = documentField2;
        arrayOfDocumentFields[2] = documentField3;
        arrayOfDocumentFields[3] = documentField4;
        arrayOfDocumentFields[4] = documentField5;

        arrayOfNumericalFields[0] = numericalField1;
        arrayOfNumericalFields[1] = numericalField2;
        arrayOfNumericalFields[2] = numericalField3;
        arrayOfNumericalFields[3] = numericalField4;
        arrayOfNumericalFields[4] = numericalField5;
        arrayOfNumericalFields[5] = numericalField6;
        arrayOfNumericalFields[6] = numericalField7;
        arrayOfNumericalFields[7] = numericalField8;
        arrayOfNumericalFields[8] = numericalField9;
        arrayOfNumericalFields[9] = numericalField10;

        arrayOfTextFields[0] = textField1;
        arrayOfTextFields[1] = textField2;
        arrayOfTextFields[2] = textField3;
        arrayOfTextFields[3] = textField4;
        arrayOfTextFields[4] = textField5;
        arrayOfTextFields[5] = textField6;
        arrayOfTextFields[6] = textField7;
        arrayOfTextFields[7] = textField8;
        arrayOfTextFields[8] = textField9;
        arrayOfTextFields[9] = textField10;


    }

}