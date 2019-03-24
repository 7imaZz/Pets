package com.example.pets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PetsAdapter extends ArrayAdapter<Pets>{
    public PetsAdapter(Context context, List<Pets> pets) {
        super(context, 0, pets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Pets currentPet = getItem(position);

        TextView petName = currentView.findViewById(R.id.tv_pet_name);
        petName.setText(currentPet.getName());

        TextView petBreed = currentView.findViewById(R.id.tv_pet_breed);
        petBreed.setText(currentPet.getBreed());

        return currentView;
    }
}
