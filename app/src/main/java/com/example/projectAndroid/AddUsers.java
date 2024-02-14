package com.example.projectAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectAndroid.data.Profil;
import com.example.seminar_5.R;

public class AddUsers extends AppCompatActivity {

    EditText editEmail,editName,editAge;
    Button btnAdd;
    Spinner listTypeScholarship;
    CheckBox isIntegrally;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        editEmail = findViewById(R.id.editTextEmail);
        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        btnAdd = findViewById(R.id.btnAddUser);
        listTypeScholarship = findViewById(R.id.spinnerAddTypeScolarship);
        isIntegrally = findViewById(R.id.checkBoxAddIntegrally);

        ArrayAdapter<TypeScholarship> scholarshipAdapter = Profil.createScholarshipArrayAdapter(this);
        scholarshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listTypeScholarship.setAdapter(scholarshipAdapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = editEmail.getText().toString();
                    String name = editName.getText().toString();
                    int age = Integer.parseInt(editAge.getText().toString());
                    boolean integrally = isIntegrally.isChecked();
                    String typeScholarship = listTypeScholarship.getSelectedItem().toString();

                    Intent intent = new Intent();
                    intent.putExtra("newUser", new Profil(email, name, age, integrally, TypeScholarship.valueOf(typeScholarship)));
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(AddUsers.this, "Wrong input!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}