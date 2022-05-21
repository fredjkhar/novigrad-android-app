package com.example.novigradv2.clientActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigradv2.R;
import com.example.novigradv2.authentication.MainActivity;
import com.example.novigradv2.classes.Branch;
import com.example.novigradv2.classes.Client;
import com.example.novigradv2.classes.Entry;
import com.example.novigradv2.classes.RequestedService;
import com.example.novigradv2.classes.Service;
import com.example.novigradv2.classes.WorkHours;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FilloutFormActivity extends AppCompatActivity {

    private List<EditText> editTexts;
    private List<EditText> numericalTexts;
    private List<Button> buttons;

    private List<Entry> clientForm;

    private StorageReference storageReference;

    private
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Service service;
    private Branch branch;
    private String uID;

    private String currentPhotoPath;

    private ImageView selectedImage;

    private List<String> imagesURIList;

    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillout_form);

        //Background Animation code
        ScrollView constraintLayout = findViewById(R.id.filloutFormLayout);
        AnimationDrawable animationDrawable =  (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(1250);
        animationDrawable.start();

        branch = (Branch) getIntent().getSerializableExtra("branch");
        service = (Service) getIntent().getSerializableExtra("service");

        editTexts = new ArrayList<>();
        buttons = new ArrayList<>();
        imagesURIList = new ArrayList<>();
        numericalTexts = new ArrayList<>();
        clientForm = new ArrayList<>();

        selectedImage = findViewById(R.id.imageView66);
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        TextView serviceName = findViewById(R.id.serviceNameEditText);
        serviceName.setText(service.getTitle());

        LinearLayout linearLayout = findViewById(R.id.editTextsLinearLayout);
        LinearLayout documentsLinearLayout = findViewById(R.id.documentsLinearLayout);


        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.setMargins(5, 5, 5, 5);


        TableLayout.LayoutParams btnParams = new TableLayout.LayoutParams();
        btnParams.setMargins(50, 10, 50, 5);



        for (String string : service.getTextFields()) {
            EditText editText = new EditText(this);
            editText.setHint(string);
            editText.setTextSize(20);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setLayoutParams(params);
            editText.setTypeface(Typeface.SANS_SERIF);
            editText.setBackgroundResource(R.drawable.layout_edit_text_1);
            linearLayout.addView(editText);
            editTexts.add(editText);
        }

        for (String string : service.getNumericalFields()) {
            EditText editText = new EditText(this);
            editText.setHint(string);
            editText.setTextSize(20);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setLayoutParams(params);
            editText.setTypeface(Typeface.SANS_SERIF);
            editText.setBackgroundResource(R.drawable.layout_edit_text_1);
            linearLayout.addView(editText);
            numericalTexts.add(editText);
        }
        for (String string : service.getDocumentFields()) {
            Button button = new Button(this);
            button.setHint(string);
            button.setTextSize(20);
            button.setInputType(InputType.TYPE_CLASS_NUMBER);
            button.setLayoutParams(btnParams);
            button.setTypeface(Typeface.SANS_SERIF);
            button.setBackgroundResource(R.drawable.layout_edit_text_1);
            button.setGravity(Gravity.CENTER);
            buttons.add(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn = button;
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ignored) {

                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(FilloutFormActivity.this,
                                    "com.example.novigradv2.clientActivities",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }

                    }
                }
            });
            documentsLinearLayout.addView(button);
        }



        Button submit = findViewById(R.id.submitServiceButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //retrieve data after validating it
                Iterator<String> stringIterator = service.getTextFields().iterator();
                Iterator<EditText> editTextIterator = editTexts.iterator();
                while (stringIterator.hasNext() && editTextIterator.hasNext()) {
                    EditText editText = editTextIterator.next();
                    String string = stringIterator.next();
                    if (editText.getText().toString().isEmpty()) {
                        editText.setError("Please fill this field.");
                        editText.requestFocus();
                        return;
                    }
                    else {
                        clientForm.add(new Entry(string,editText.getText().toString(),"TextField"));
                    }
                }

                stringIterator = service.getNumericalFields().iterator();
                editTextIterator = numericalTexts.iterator();
                while (stringIterator.hasNext() && editTextIterator.hasNext()) {
                    EditText editText = editTextIterator.next();
                    String string = stringIterator.next();
                    if (editText.getText().toString().isEmpty()) {
                        editText.setError("Please fill this field.");
                        editText.requestFocus();
                        return;
                    }
                    else {
                        clientForm.add(new Entry(string,editText.getText().toString(),"NumericalField"));
                    }
                }
                stringIterator = service.getDocumentFields().iterator();
                Iterator<String> imageURIIterator = imagesURIList.iterator();
                while (stringIterator.hasNext() && imageURIIterator.hasNext()) {
                    String URI = imageURIIterator.next();
                    String string = stringIterator.next();
                    if (URI.isEmpty()) {
                        Toast.makeText(FilloutFormActivity.this, "Please take a picture of your ".concat(string).concat("."), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        clientForm.add(new Entry(string,URI,"DocumentField"));
                    }
                }

                //getting client id
                DatabaseReference clientsDatabase = FirebaseDatabase.getInstance().getReference("Clients");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userEmail = user.getEmail();
                clientsDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Client client = postSnapshot.getValue(Client.class);
                            if (client != null && client.getEmail().equals(userEmail)) {
                                uID = client.getId(); //client id
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Requested");
                String id = database.push().getKey();
                RequestedService requestedService = new RequestedService(clientForm,branch,service,id,"Pending");
                database.child(id).setValue(requestedService);
                Toast.makeText(FilloutFormActivity.this, "Service request successfully submitted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FilloutFormActivity.this,ClientActivity.class));
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        imagesURIList.add(image.getName());
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onBackPressed() { }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                selectedImage.setImageURI(Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                Toast.makeText(FilloutFormActivity.this, "Please wait, uploading image.", Toast.LENGTH_SHORT).show();
                uploadImageToFirebase(f.getName(), contentUri);


            }

        }

    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child(name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                    }
                });
                btn.setVisibility(View.GONE);
                Toast.makeText(FilloutFormActivity.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FilloutFormActivity.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
