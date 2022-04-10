package com.example.connectingus.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connectingus.R;
import com.example.connectingus.conversation.ConversationList;
import com.example.connectingus.models.Users;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileEdit extends AppCompatActivity {
    Button next;
    TextInputEditText name;
    TextInputEditText about;
    TextInputEditText phone;
    String userID,deviceID;
    String user_number,user_name,user_about,user_profile_pic;
    Intent intent;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ShapeableImageView profile_pic;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 && data!= null){
           Uri selectedImage = data.getData();
           profile_pic.setImageURI(selectedImage);
           user_profile_pic = selectedImage.getPath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        next = findViewById(R.id.next);
        name = findViewById(R.id.name);
        about = findViewById(R.id.about);
        phone = findViewById(R.id.phone);

        firebaseAuth = FirebaseAuth.getInstance();
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        user_number = firebaseAuth.getCurrentUser().getPhoneNumber();
        phone.setText(user_number);
        deviceID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        userID = firebaseAuth.getCurrentUser().getUid();
        profile_pic = findViewById(R.id.profile_pic);

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,3);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flag=true;
                user_name = name.getText().toString().trim();
                user_about = about.getText().toString().trim();
                if(name.getText().toString().isEmpty() && about.getText().toString().isEmpty()){
                    name.setError("Please fill name");
                    about.setError("Please fill about");
                }
                else if(name.getText().toString().isEmpty())
                    name.setError("Please fill name");
                else if(about.getText().toString().isEmpty())
                    about.setError("Please fill about");
                else{
                    Users users = new Users(user_number,userID,user_name,user_about,deviceID,user_profile_pic);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("users").child(userID);
                    databaseReference.setValue(users);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChildren())
                            {
                                Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(),"Failed to add data",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                Intent intent=new Intent(getApplicationContext(), ConversationList.class);
                startActivity(intent);
                finish();
            }
        });

        /*signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }
}