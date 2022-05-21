package com.example.novigradv2.employeeActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.classes.Entry;
import com.example.novigradv2.classes.RequestedService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class ViewClientRequestedService extends AppCompatActivity {

    List<Entry> editTexts;
    List<Entry> numericalTexts;
    List<Entry> documentTexts;

    LinearLayout textLayout;
    LinearLayout documentLayout;

    Button accept;
    Button decline;

    RequestedService service;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client_requested_service);

        //Background Animation code
        ScrollView constraintLayout = findViewById(R.id.viewRequestedServiceLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        service = (RequestedService) getIntent().getSerializableExtra("service");

        editTexts = new ArrayList<>();
        numericalTexts = new ArrayList<>();
        documentTexts = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference();

        accept = findViewById(R.id.accept);
        decline = findViewById(R.id.decline);


        List<Entry> form = service.getForm();
        for (Entry entry: form) {
            if (entry.getType().equals("TextField")) editTexts.add(entry);
            if (entry.getType().equals("NumericalField")) editTexts.add(entry);
            if (entry.getType().equals("DocumentField")) documentTexts.add(entry);
        }

        textLayout = findViewById(R.id.textNumericalFieldsRelativeLayout);
        documentLayout = findViewById(R.id.documentFieldsRelativeLayout);


        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.setMargins(5, 5, 5, 5);
        TableLayout.LayoutParams btnParams = new TableLayout.LayoutParams();
        btnParams.setMargins(5, 5, 5, 25);


        for (Entry entry: editTexts) {
            TextView description = new TextView(this);
            description.setText(entry.getDescription());
            description.setTextSize(22);
            description.setTypeface(Typeface.SANS_SERIF);
            description.setInputType(InputType.TYPE_CLASS_TEXT);
            description.setLayoutParams(params);
            textLayout.addView(description);

            TextView answer = new TextView(this);
            answer.setText(entry.getAnswer());
            answer.setTextSize(20);
            answer.setTypeface(Typeface.SANS_SERIF);
            answer.setInputType(InputType.TYPE_CLASS_TEXT);
            answer.setLayoutParams(btnParams);
            textLayout.addView(answer);
        }

        for (Entry entry: documentTexts) {
            TextView description = new TextView(this);
            description.setText(entry.getDescription());
            description.setTextSize(22);
            description.setTypeface(Typeface.SANS_SERIF);
            description.setInputType(InputType.TYPE_CLASS_TEXT);
            description.setLayoutParams(params);
            documentLayout.addView(description);

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(btnParams);
            StorageReference st = storageReference.child("Images/".concat(entry.getAnswer()));
            int index = entry.getAnswer().indexOf(".");
            String prefix = entry.getAnswer().substring(0,index-1);
            String suffix = entry.getAnswer().substring(index+1);
            try {
                final File image = File.createTempFile(prefix,suffix);
                st.getFile(image).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                        imageView.setImageBitmap(bitmap);
                        documentLayout.addView(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewClientRequestedService.this, "smth is wrong", Toast.LENGTH_SHORT).show();

                    }
                });
            } catch (IOException EX) {
                Toast.makeText(ViewClientRequestedService.this, "shit dawg, cannot find the image", Toast.LENGTH_SHORT).show();
                EX.printStackTrace();
            }


        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.setStatus("Accepted");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requested");
                databaseReference.child(service.getUid()).setValue(service);
                Toast.makeText(ViewClientRequestedService.this, "Service accepted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewClientRequestedService.this,ClientsRequestsActivity.class));

            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.setStatus("Declined");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requested");
                databaseReference.child(service.getUid()).setValue(service);
                Toast.makeText(ViewClientRequestedService.this, "Service declined", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewClientRequestedService.this,ClientsRequestsActivity.class));
            }
        });
    }

}