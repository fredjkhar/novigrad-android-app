package com.example.novigradv2.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.ClientsListView;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.SeviceListView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;

public class AddServiceActivity extends AppCompatActivity {

    private Button addServiceButton;
    private TextView title;
    private TextView price;

    private ListView serviceFieldsView;

    private DatabaseReference database;

    private String titleString;
    private String priceString;
    private String[] textFieldsArray;
    private String[] numericalFieldsArray;
    private String[] documentFieldsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        //Background Animation code
        RelativeLayout relativeLayout = findViewById(R.id.addServiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        database = FirebaseDatabase.getInstance().getReference("Services");

        title = findViewById(R.id.addServiceTitle);
        price = findViewById(R.id.addServicePrice);
        serviceFieldsView = findViewById(R.id.addServiceList);


        if (getIntent().getExtras() != null) {
            titleString = getIntent().getExtras().getString("title");
            priceString = getIntent().getExtras().getString("price");
            textFieldsArray = getIntent().getStringArrayExtra("textFieldsArray");
            numericalFieldsArray = getIntent().getStringArrayExtra("numericalFieldsArray");
            documentFieldsArray = getIntent().getStringArrayExtra("documentFieldsArray");
            String nbrOfTextFields = getIntent().getExtras().getString("nbrOfTextFields");
            String nbrOfDocumentFields = getIntent().getExtras().getString("nbrOfDocumentFields");
            String nbrOfNumericalFields = getIntent().getExtras().getString("nbrOfNumericalFields");

            int nbrTxt = Integer.parseInt(nbrOfTextFields);
            int nbrNmr = Integer.parseInt(nbrOfNumericalFields);
            int nbrDoc = Integer.parseInt(nbrOfDocumentFields);


            title.setText("Service title : ".concat(titleString));
            price.setText("Price : ".concat(priceString));

            List<String> list = new ArrayList<>();
            for (int i = 0; i < nbrTxt; i++) {
                if (textFieldsArray[i] != null) {
                    list.add(textFieldsArray[i]);
                }
            }
            for (int i = 0; i < nbrNmr; i++) {
                if (numericalFieldsArray[i] != null) {
                    list.add(numericalFieldsArray[i]);
                }
            }
            for (int i = 0; i < nbrDoc; i++) {
                if (documentFieldsArray[i] != null) {
                    list.add(documentFieldsArray[i]);
                }
            }

            SeviceListView serviceAdapter = new SeviceListView(AddServiceActivity.this, list);
            serviceFieldsView.setAdapter(serviceAdapter);
            serviceFieldsView.setVisibility(View.VISIBLE);
        }
        addServiceButton = findViewById(R.id.AddServiceButton1);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> textList= Arrays.asList(textFieldsArray);
                List<String> numericalList = Arrays.asList(numericalFieldsArray);
                List<String> documentList = Arrays.asList(documentFieldsArray);

                String id = database.push().getKey();
                Service service = new Service(id,titleString,priceString,textList,numericalList,documentList);

                database.child(id).setValue(service);
                Toast.makeText(AddServiceActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddServiceActivity.this,AdminActivity.class));
            }
        });




    }
}