package com.example.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pets.DB.PetsDb;

public class UpdateActivity extends AppCompatActivity {

    private static final String [] spinnerItems = {"Unknown", "Male", "Female"};
    private int mGender = 0;
    private int dbID;
    EditText nameEditText, breedEditText, weightEditText;
    Spinner genderSpinner;

    PetsDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new PetsDb(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();

        dbID = intent.getIntExtra("id", 0);

        Pets currentPet = db.getOneItem(dbID);

        nameEditText = findViewById(R.id.upd_edit_pet_name);
        breedEditText = findViewById(R.id.upd_edit_pet_breed);
        weightEditText = findViewById(R.id.upd_edit_pet_weight);
        genderSpinner = findViewById(R.id.upd_spinner_gender);

        setupSpinner();

        nameEditText.setText(currentPet.getName());
        breedEditText.setText(currentPet.getBreed());
        weightEditText.setText(""+currentPet.getWeight());
        genderSpinner.setSelection(currentPet.getGender());
    }

    public void setupSpinner(){

        ArrayAdapter genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);

        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selection = parent.getSelectedItemPosition();

                if (selection == 0)
                    mGender = 0;
                else if (selection == 1)
                    mGender = 1;
                else
                    mGender = 2;
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

        if (id == R.id.upd_action_save)
        {
            db.updatePet(dbID, nameEditText.getText().toString(),
                   breedEditText.getText().toString(),
                    mGender,
                    Integer.parseInt(weightEditText.getText().toString()));
            finish();
        }

        if (id == R.id.upd_action_delete){
            db.deletePet(dbID);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_update, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
