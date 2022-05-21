package com.example.novigradv2.adminActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.novigradv2.R;

public class FillNumericalFields extends AppCompatActivity {

    private EditText[] fieldsArray;

    private Button continueButton;

    private TextView numericalFields;

    private int nbr;
    private String title;
    private String nbrOfTextFields;
    private String nbrOfNumericalFields;
    private String nbrOfDocumentFields;
    private String price;
    private String[] textFieldsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_numerical_fields);

        //Background Animation code
        ScrollView scrollViewLayout = findViewById(R.id.fillNumericalFieldsLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) scrollViewLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        initialize();

        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString("title");
            nbrOfTextFields = getIntent().getExtras().getString("nbrOfTextFields");
            nbrOfNumericalFields = getIntent().getExtras().getString("nbrOfNumericalFields");
            nbrOfDocumentFields = getIntent().getExtras().getString("nbrOfDocumentFields");
            price = getIntent().getExtras().getString("price");
            textFieldsArray = getIntent().getStringArrayExtra("textFieldsArray");

            nbr = Integer.parseInt(nbrOfNumericalFields);

            for (int i = 9; i >= nbr ; i--) {
                fieldsArray[i].setVisibility(View.GONE);
                fieldsArray[i] = null;
            }
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] numericalFieldsArrayString = new String[10];
                for (int i = 0; i < nbr; i++) {
                    if (fieldsArray[i].getText() != null) {
                        if (fieldsArray[i].getText().toString().isEmpty()) {
                            fieldsArray[i].setError("Please fill the field");
                            fieldsArray[i].requestFocus();
                            return;
                        }
                        numericalFieldsArrayString[i] = fieldsArray[i].getText().toString();
                    }
                }
                Intent intent = new Intent(FillNumericalFields.this, FillDocumentFields.class);
                intent.putExtra("title",title);
                intent.putExtra("nbrOfTextFields",nbrOfTextFields);
                intent.putExtra("nbrOfNumericalFields",nbrOfNumericalFields);
                intent.putExtra("nbrOfDocumentFields",nbrOfDocumentFields);
                intent.putExtra("price",price);
                intent.putExtra("textFieldsArray",textFieldsArray);
                intent.putExtra("numericalFieldsArray",numericalFieldsArrayString);
                startActivity(intent);
            }
        });

    }

    private void initialize() {
        EditText numericalField1 = findViewById(R.id.editNumericalField1);
        EditText numericalField2 = findViewById(R.id.editNumericalField2);
        EditText numericalField3 = findViewById(R.id.editNumericalField3);
        EditText numericalField4 = findViewById(R.id.editNumericalField4);
        EditText numericalField5 = findViewById(R.id.editNumericalField5);
        EditText numericalField6 = findViewById(R.id.editNumericalField6);
        EditText numericalField7 = findViewById(R.id.editNumericalField7);
        EditText numericalField8 = findViewById(R.id.editNumericalField8);
        EditText numericalField9 = findViewById(R.id.editNumericalField9);
        EditText numericalField10 = findViewById(R.id.editNumericalField10);

        fieldsArray = new EditText[10];
        fieldsArray[0] = numericalField1;
        fieldsArray[1] = numericalField2;
        fieldsArray[2] = numericalField3;
        fieldsArray[3] = numericalField4;
        fieldsArray[4] = numericalField5;
        fieldsArray[5] = numericalField6;
        fieldsArray[6] = numericalField7;
        fieldsArray[7] = numericalField8;
        fieldsArray[8] = numericalField9;
        fieldsArray[9] = numericalField10;

        continueButton = findViewById(R.id.continueButtonNumericalFieldsButton);
    }
}