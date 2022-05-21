package com.example.novigradv2.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.novigradv2.R;

public class FillTextFields extends AppCompatActivity {

    private EditText[] fieldsArray;

    private Button continueButton;

    private TextView textFields;

    private int nbr;
    private String title;
    private String nbrOfTextFields;
    private String nbrOfNumericalFields;
    private String nbrOfDocumentFields;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_text_fields);

        //Background Animation code
        ScrollView scrollViewLayout = findViewById(R.id.fillTextFieldsLayout);
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


            textFields.setText("Text Fields");
            nbr = Integer.parseInt(nbrOfTextFields);

            for (int i = 9; i >= nbr; i--) {
                fieldsArray[i].setVisibility(View.GONE);
                fieldsArray[i] = null;
            }
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] textFieldsArrayString = new String[10];
                for (int i = 0; i < nbr; i++) {
                    if (fieldsArray[i].getText() != null) {
                        if (fieldsArray[i].getText().toString().isEmpty()) {
                            fieldsArray[i].setError("Please fill the field");
                            fieldsArray[i].requestFocus();
                            return;
                        }
                        textFieldsArrayString[i] = fieldsArray[i].getText().toString();
                    }
                }
                Intent intent = new Intent(FillTextFields.this, FillNumericalFields.class);
                intent.putExtra("title",title);
                intent.putExtra("nbrOfTextFields",nbrOfTextFields);
                intent.putExtra("nbrOfNumericalFields",nbrOfNumericalFields);
                intent.putExtra("nbrOfDocumentFields",nbrOfDocumentFields);
                intent.putExtra("price",price);
                intent.putExtra("textFieldsArray",textFieldsArrayString);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        EditText textField1 = findViewById(R.id.editTextField1);
        EditText textField2 = findViewById(R.id.editTextField2);
        EditText textField3 = findViewById(R.id.editTextField3);
        EditText textField4 = findViewById(R.id.editTextField4);
        EditText textField5 = findViewById(R.id.editTextField5);
        EditText textField6 = findViewById(R.id.editTextField6);
        EditText textField7 = findViewById(R.id.editTextField7);
        EditText textField8 = findViewById(R.id.editTextField8);
        EditText textField9 = findViewById(R.id.editTextField9);
        EditText textField10 = findViewById(R.id.editTextField10);

        fieldsArray = new EditText[10];
        fieldsArray[0] = textField1;
        fieldsArray[1] = textField2;
        fieldsArray[2] = textField3;
        fieldsArray[3] = textField4;
        fieldsArray[4] = textField5;
        fieldsArray[5] = textField6;
        fieldsArray[6] = textField7;
        fieldsArray[7] = textField8;
        fieldsArray[8] = textField9;
        fieldsArray[9] = textField10;

        textFields = findViewById(R.id.textFields);

        continueButton = findViewById(R.id.continueButtonTextFieldsButton);
    }
}