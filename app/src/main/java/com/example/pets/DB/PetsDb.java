package com.example.pets.DB;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pets.Pets;

import java.util.ArrayList;

public class PetsDb extends SQLiteOpenHelper {

    private final static String DB_NAME = "pets-v2.db";


    public PetsDb(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pets(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, breed TEXT, gender INTEGER,weight INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pets");
        onCreate(db);
    }

    public void insertPet(String name, String breed, int gender, int weight){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("breed", breed);
        contentValues.put("gender", gender);
        contentValues.put("weight", weight);

        db.insert("pets", null, contentValues);
    }

    public ArrayList<Pets> getAllPets(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Pets> pets = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM pets", null);

        while(cursor.moveToNext()){


            String name = cursor.getString(cursor.getColumnIndex("name"));
            String breed = cursor.getString(cursor.getColumnIndex("breed"));
            int gender = cursor.getInt(cursor.getColumnIndex("gender"));
            int weight = cursor.getInt(cursor.getColumnIndex("weight"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            pets.add(new Pets(name, breed, gender, weight, id));
        }
        return pets;
    }

    public Pets getOneItem(int id){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("pets", new String[] { "id",
                        "name", "breed", "gender","weight"}, "id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String breed = cursor.getString(cursor.getColumnIndex("breed"));
        int gender = cursor.getInt(cursor.getColumnIndex("gender"));
        int weight = cursor.getInt(cursor.getColumnIndex("weight"));

        Pets pets = new Pets(name, breed, gender, weight, id);

        return pets;
    }

    public void updatePet(int id,String name, String breed, int gender, int weight){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("breed", breed);
        contentValues.put("gender", gender);
        contentValues.put("weight", weight);

        db.update("pets", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from pets");
    }

    public void deletePet(int id){

        SQLiteDatabase db = getWritableDatabase();

        db.delete("pets", "id=?", new String[]{String.valueOf(id)});
    }
}
