package com.example.projectAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.projectAndroid.data.Profil;
import com.example.seminar_5.R;

public class ProfileDetails extends AppCompatActivity {

    EditText edEmail, edName,edAge;
    Button backButton;
    CheckBox isIntegrally;
    Spinner typeScolarship;
    ArrayAdapter<TypeScholarship> typeScholarshipArrayAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        edEmail = findViewById(R.id.editEmail);
        edName = findViewById(R.id.editName);
        edAge = findViewById(R.id.editAge);
        backButton = findViewById(R.id.btnBack);

        isIntegrally = findViewById(R.id.checkBoxIntegrally);
        typeScolarship = findViewById(R.id.spinnerTypeScolarship);

        typeScholarshipArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TypeScholarship.values());
        typeScolarship.setAdapter(typeScholarshipArrayAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            Profil profil = (Profil) intent.getSerializableExtra("user");
            if (profil != null) {

                edEmail.setText(profil.getEmail());
                edName.setText(profil.getName());
                edAge.setText(String.valueOf(profil.getAge()));

                isIntegrally.setChecked(profil.isIntegrally());

                TypeScholarship scolarshipSelect = profil.getTypeScholarship();
                int position = typeScholarshipArrayAdapter.getPosition(scolarshipSelect);
                typeScolarship.setSelection(position);
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}