package com.example.projectAndroid.data;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projectAndroid.TypeScholarship;

import java.io.Serializable;

@Entity(tableName = "users")
public class Profil implements Serializable {

    // We use the @ColumnInfo(name="") annotation if we want a field to be named differently in the database
    // If we want a field to not be present in the database, we use the @Ignore annotation
    @PrimaryKey
    @NonNull
    private String email;
    private String name;
    private int age;
    private boolean integrally;

    private TypeScholarship typeScholarship;

    public Profil(String email, String name, int age, boolean integrally, TypeScholarship typeScholarship) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.integrally = integrally;
        this.typeScholarship = typeScholarship;
    }

    public boolean isIntegrally() {
        return integrally;
    }

    public void setIntegrally(boolean integrally) {
        this.integrally = integrally;
    }

    public TypeScholarship getTypeScholarship() {
        return typeScholarship;
    }

    public void setTypeScholarship(TypeScholarship typeScholarship) {
        this.typeScholarship = typeScholarship;
    }

    public Profil() {
    }

    public Profil(String email, String name, int age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Profil(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Profil" + '\n' +
                "Email: " + email + '\n' +
                "Name: " + name + '\n' +
                "Age: " + age;
    }

    public static ArrayAdapter<TypeScholarship> createScholarshipArrayAdapter(Context context) {
        TypeScholarship[] values = TypeScholarship.values();
        return new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);
    }
}
