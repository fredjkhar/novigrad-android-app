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

public class FillDocumentFields extends AppCompatActivity {

    private EditText[] fieldsArray;

    private int nbr;
    private String title;
    private String nbrOfTextFields;
    private String nbrOfNumericalFields;
    private String nbrOfDocumentFields;
    private String price;
    private String[] textFieldsArray;
    private String[] numericalFieldsArray;

    private TextView documentFields;

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_document_fields);

        //Background Animation code
        ScrollView scrollViewLayout = findViewById(R.id.fillDocumentFieldsLayout);
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
            numericalFieldsArray = getIntent().getStringArrayExtra("numericalFieldsArray");

            documentFields.setText("Document Fields");
            nbr = Integer.parseInt(nbrOfDocumentFields);

            for (int i = 4; i >= nbr; i--) {
                fieldsArray[i].setVisibility(View.GONE);
                fieldsArray[i] = null;
            }
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] documentFieldsArrayString = new String[10];
                for (int i = 0; i < nbr; i++) {
                    if (fieldsArray[i].getText() != null) {
                        if (fieldsArray[i].getText().toString().isEmpty()) {
                            fieldsArray[i].setError("Please fill the field");
                            fieldsArray[i].requestFocus();
                            return;
                        }
                        documentFieldsArrayString[i] = fieldsArray[i].getText().toString();
                    }
                }
                Intent intent = new Intent(FillDocumentFields.this, AddServiceActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("nbrOfTextFields",nbrOfTextFields);
                intent.putExtra("nbrOfNumericalFields",nbrOfNumericalFields);
                intent.putExtra("nbrOfDocumentFields",nbrOfDocumentFields);
                intent.putExtra("price",price);
                intent.putExtra("textFieldsArray",textFieldsArray);
                intent.putExtra("numericalFieldsArray",numericalFieldsArray);
                intent.putExtra("documentFieldsArray",documentFieldsArrayString);
                startActivity(intent);
            }
        });

    }

    private void initialize(){
        EditText documentField1 = findViewById(R.id.editDocumentField1);
        EditText documentField2 = findViewById(R.id.editDocumentField2);
        EditText documentField3 = findViewById(R.id.editDocumentField3);
        EditText documentField4 = findViewById(R.id.editDocumentField4);
        EditText documentField5 = findViewById(R.id.editDocumentField5);


        fieldsArray = new EditText[5];
        fieldsArray[0] = documentField1;
        fieldsArray[1] = documentField2;
        fieldsArray[2] = documentField3;
        fieldsArray[3] = documentField4;
        fieldsArray[4] = documentField5;

       documentFields = findViewById(R.id.documentFields);

       continueButton = findViewById(R.id.continueButtonDocumentFieldsButton);

    }
}