package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener { // fragments should not directly modify activity data
        void addCity(City city);
        void editCity(City city, int position);
    }

    private AddCityDialogListener listener; // fragment fields
    private City cityToEdit;
    private int position;

    public static AddCityFragment newInstance(City city, int position) { // passing data into the fragment
        Bundle args = new Bundle();

        args.putSerializable("city", city); //new city

        args.putInt("position", position); //new positon

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if (context instanceof AddCityDialogListener) { // checks if the activity implements the interface
            listener = (AddCityDialogListener) context;
        }

        else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);

        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        boolean isEdit = false;

        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
            position = getArguments().getInt("position");


            if (cityToEdit != null) {
                isEdit = true;
                editCityName.setText(cityToEdit.getName());
                editProvinceName.setText(cityToEdit.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit City" : "Add City")
                .setNegativeButton("Cancel", null)


                .setPositiveButton("OK", (dialog, which) -> { //setting the new city
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (!cityName.isEmpty() && !provinceName.isEmpty()) {

                        if (cityToEdit != null) { //if its in edit mode
                            cityToEdit.setName(cityName);
                            cityToEdit.setProvince(provinceName);
                            listener.editCity(cityToEdit, position);
                        }

                        else {
                            listener.addCity(new City(cityName, provinceName));
                        }
                    }
                })
                .create();
    }
}
