package com.example.pets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pets.DB.PetsDb;

public class EditActivity extends AppCompatActivity {

    private static final String [] spinnerItems = {"Unknown", "Male", "Female"};
    private int mGender = 0;
    EditText nameEditText, breedEditText, weightEditText;
    Spinner genderSpinner;

    PetsDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new PetsDb(this);

        nameEditText = findViewById(R.id.edit_pet_name);
        breedEditText = findViewById(R.id.edit_pet_breed);
        weightEditText = findViewById(R.id.edit_pet_weight);
        genderSpinner = findViewById(R.id.spinner_gender);

        setupSpinner();
    }

    public void setupSpinner(){

        ArrayAdapter genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);

        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (selection == "Male")
                    mGender = 1;
                else if (selection == "Female")
                    mGender = 2;
                else
                    mGender = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home)
            finish();

       if (id == R.id.ed_action_save)
        {
            db.insertPet(nameEditText.getText().toString(),
                    breedEditText.getText().toString(),
                    mGender,
                    Integer.parseInt(weightEditText.getText().toString()));

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_edit, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
