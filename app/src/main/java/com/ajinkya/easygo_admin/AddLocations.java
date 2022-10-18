package com.ajinkya.easygo_admin;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AddLocations extends AppCompatActivity {
    private EditText locality, locationPin;
    private Button addLoc;
    private String Locality, LocationPin, state;

    private ProgressDialog progressDialog;

    private DatabaseReference databaseAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_locations);

        Initialize();
        Buttons();
    }

    private void Buttons() {
        addLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean filled = checkInput();
                if (filled) {
                    progressDialog = new ProgressDialog(AddLocations.this);
                    progressDialog.setTitle("Registering");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    SetLocation(Locality, LocationPin, state);
                }else {
                    Toast.makeText(AddLocations.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SetLocation(String Location, String PinCode, String State) {

        HashMap<String, String> loci = new HashMap<>();
        loci.put("Location_pin", PinCode);
        loci.put("Place", Location.toUpperCase());
        loci.put("State", State.toUpperCase());
        Log.e(TAG, "SetLocation: " + loci);

        databaseAddress.child(PinCode).setValue(loci).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddLocations.this, "Location Added Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    clearEditText();
                } else {
                    Toast.makeText(AddLocations.this, "Failed TO Add Location", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }


    private Boolean checkInput() {
        Locality = locality.getText().toString();
        LocationPin = locationPin.getText().toString();

        if (Locality.isEmpty()) {
            locality.setError("Enter locality");
            return false;
        } else if (LocationPin.isEmpty()) {
            locationPin.setError("Enter Location Pin");
            return false;
        } else if (state.isEmpty()) {
            Toast.makeText(this, "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private void Initialize() {
        Toolbar toolbar = findViewById(R.id.toolbarLoc);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        locality = findViewById(R.id.LocalityLoc);
        locationPin = findViewById(R.id.LocationPinLoc);
        Spinner stateSpinner = findViewById(R.id.StateSpinnerLoc);
        addLoc = findViewById(R.id.AddLoc);


        String[] State = new String[]{"Maharashtra", "Bihar", "Jharkhand", "Delhi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddLocations.this, android.R.layout.simple_spinner_item, State);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        databaseAddress = FirebaseDatabase.getInstance().getReference().child("Locations");

    }

    private void clearEditText() {
        locality.setText("");
        locationPin.setText("");
    }


}