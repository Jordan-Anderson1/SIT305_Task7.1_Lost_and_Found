package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostOverview extends AppCompatActivity {

    LostFoundItem item;

    TextView postType, name, phone, description, date, location;

    Button removeButton;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        item = (LostFoundItem) intent.getSerializableExtra("item");

        fStore = FirebaseFirestore.getInstance();

        removeButton = findViewById(R.id.removeButton);

        postType = findViewById(R.id.postType);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);

        postType.setText(item.getPostType());

        String nameText = "posted by: " + item.getName();
        name.setText(nameText);

        String phoneText = "Contact number: " + item.getPhone();
        phone.setText(phoneText);

        String descriptionText = "Description: " + item.getDescription();
        description.setText(descriptionText);

        String dateText = "Posted on: " + item.getDate();
        date.setText(dateText);

        String locationText = "Location: " + item.getLocation();
        location.setText(locationText);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("lost_found_items").document(item.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PostOverview.this, "Post deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostOverview.this, ShowAllActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostOverview.this, "Unable to remove item", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostOverview.this, ShowAllActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });




    }
}