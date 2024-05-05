package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class CreateAdvertActivity extends AppCompatActivity {

    EditText nameEditText, phoneEditText, descriptionEditText, dateEditText, locationEditText;
    Button submitButton;

    FirebaseFirestore fStore;
    RadioGroup postTypeRadioGroup;

    String postTypeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        locationEditText = findViewById(R.id.locationEditText);

        submitButton = findViewById(R.id.submitButton);

        postTypeRadioGroup = findViewById(R.id.postType);

        fStore = FirebaseFirestore.getInstance();


        //gets the post type and stores in postTypeString
        postTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton != null){
                    postTypeString = radioButton.getText().toString();
                }
            }
        });

        //create new item object and add to fireStore DB on submit
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, phone, description, date, location;

                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                date = dateEditText.getText().toString();
                location = locationEditText.getText().toString();

                if(TextUtils.isEmpty(name)
                || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(description)
                || TextUtils.isEmpty(date)
                || TextUtils.isEmpty(location)
                || TextUtils.isEmpty(postTypeString)){
                    Toast.makeText(CreateAdvertActivity.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                LostFoundItem newItem = new LostFoundItem(name, phone, description, date, location, postTypeString);

                DocumentReference documentReference = fStore.collection("lost_found_items").document(newItem.getId());

                Map<String, Object> item = new HashMap<>();
                item.put("name", newItem.getName());
                item.put("phone", newItem.getPhone());
                item.put("description", newItem.getDescription());
                item.put("date", newItem.getDate());
                item.put("location", newItem.getLocation());
                item.put("UUID", newItem.getId());
                item.put("postType", newItem.getPostType());

                documentReference.set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateAdvertActivity.this, "item added to db", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAdvertActivity.this, "Unable to add to DB", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(CreateAdvertActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}