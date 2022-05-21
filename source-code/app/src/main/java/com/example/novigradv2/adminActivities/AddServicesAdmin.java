package com.example.novigradv2.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novigradv2.R;

public class AddServicesAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services_admin);

        //Background Animation code
        ConstraintLayout constraintLayout = findViewById(R.id.addServicesAdminLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        EditText title = findViewById(R.id.editTextServiceTitle);
        EditText nbrOfTextFields = findViewById(R.id.editTextNumberOfTextFields);
        EditText nbrOfNumericalFields = findViewById(R.id.editTextTextNumberOfNumericalFields);
        EditText nbrOfDocumentsFields = findViewById(R.id.editTextNumberOfDocuments);
        EditText price = findViewById(R.id.editTextPrice);

        Button cont = findViewById(R.id.continueButtonAddService);



        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleString = title.getText().toString();//empty
                String nbrOfTextFieldsString = nbrOfTextFields.getText().toString();//10
                String nbrOfNumericalFieldsString = nbrOfNumericalFields.getText().toString();//10
                String nbrOfDocumentFieldsString = nbrOfDocumentsFields.getText().toString();//5
                String priceString = price.getText().toString();//100

                if (titleString.isEmpty()) {
                    title.setError("Please set a title");
                    title.requestFocus();
                    return;
                }
                if (nbrOfTextFieldsString.isEmpty()) {
                    nbrOfTextFields.setError("Please enter the number of text fields");
                    nbrOfTextFields.requestFocus();
                    return;
                } else if (Integer.parseInt(nbrOfTextFields.getText().toString()) > 10) {
                    nbrOfTextFields.setError("Number of text fields cannot be more than 10.");
                    nbrOfTextFields.requestFocus();
                    return;
                }
                if (nbrOfNumericalFieldsString.isEmpty()) {
                    nbrOfNumericalFields.setError("Please enter the number of numerical fields");
                    nbrOfNumericalFields.requestFocus();
                    return;
                } else if (Integer.parseInt(nbrOfNumericalFields.getText().toString()) > 10) {
                    nbrOfNumericalFields.setError("Number of numerical fields cannot be more than 10.");
                    nbrOfNumericalFields.requestFocus();
                    return;
                }
                if (nbrOfDocumentFieldsString.isEmpty()) {
                    nbrOfDocumentsFields.setError("Please enter the number of document fields.");
                    nbrOfDocumentsFields.requestFocus();
                    return;
                } else if (Integer.parseInt(nbrOfDocumentsFields.getText().toString()) > 10) {
                    nbrOfDocumentsFields.setError("Number of document fields cannot be more than 5.");
                    nbrOfDocumentsFields.requestFocus();
                    return;
                }
                if (priceString.isEmpty()) {
                    price.setError("Please set a price");
                    price.requestFocus();
                    return;
                } else if (Double.parseDouble(price.getText().toString()) > 200) {
                    price.setError("The price cannot be more than 200");
                    price.requestFocus();
                    return;
                }

                Intent intent = new Intent(AddServicesAdmin.this, FillTextFields.class);
                intent.putExtra("title", titleString);
                intent.putExtra("nbrOfTextFields", nbrOfTextFieldsString);
                intent.putExtra("nbrOfNumericalFields", nbrOfNumericalFieldsString);
                intent.putExtra("nbrOfDocumentFields", nbrOfDocumentFieldsString);
                intent.putExtra("price", priceString);
                startActivity(intent);
            }
        });
    }
}