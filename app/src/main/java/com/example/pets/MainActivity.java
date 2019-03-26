package com.example.pets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pets.DB.PetsDb;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PetsDb db;
    ListView petsListView;
    FloatingActionButton fab;
    View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new PetsDb(this);

        fab = findViewById(R.id.fab);
        petsListView = findViewById(R.id.lv_pets);
        emptyView = findViewById(R.id.view_empty);

        petsListView.setEmptyView(emptyView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        showPets();

        petsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pets pet = (Pets) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);

                intent.putExtra("name", pet.getName());
                intent.putExtra("breed", pet.getBreed());
                intent.putExtra("gender", pet.getGender());
                intent.putExtra("weight", pet.getWeight());
                intent.putExtra("id", pet.getId());

                startActivity(intent);
            }
        });

        petsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Pets pet = (Pets) parent.getItemAtPosition(position);
                //make an alert dialog to confirm that the user really need to delete selected pet.
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmation")
                        .setMessage("Do You Want To Delete This Pet?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletePet(pet.getId());
                                showPets();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                return true;
            }
        });
    }

    public void showPets(){
        ArrayList<Pets> pets = db.getAllPets();

        PetsAdapter adapter = new PetsAdapter(this, pets);

        petsListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirmation")
                    .setMessage("Do You Want To Delete  All Pets?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteAll();
                            showPets();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Override this method to update list with every pet added or deleted.
    @Override
    protected void onStart() {
        showPets();
        super.onStart();
    }
}
