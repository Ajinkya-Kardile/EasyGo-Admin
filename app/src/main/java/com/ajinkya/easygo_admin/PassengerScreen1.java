package com.ajinkya.easygo_admin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Adapters.PassengerScreenBusAdapter;
import com.ajinkya.easygo_admin.Model.BusModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class PassengerScreen1 extends AppCompatActivity {
    private EditText Date;
    private Button FindBtn;
    private RecyclerView recyclerView;

    private ArrayList<BusModel> busModelArrayList;
    private ProgressDialog progressDialog;

    private String date;
    PassengerScreenBusAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_screen1);

        Initialize();
        Buttons();
    }

    protected void onStart() {
        super.onStart();
        Date.setText("");
        date=null;
    }

    private void Buttons() {
        FindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Date.getText().toString().isEmpty()){
                    progressDialog.show();
                    busModelArrayList = new ArrayList<>();
                    adapter = new PassengerScreenBusAdapter(PassengerScreen1.this,busModelArrayList);
                    recyclerView.setAdapter(adapter);
                    FirebaseDataRetrieve();
                }
                else {
                    Toast.makeText(PassengerScreen1.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void FirebaseDataRetrieve() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("Tickets").child("AdminSideCheck").child(date);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                assert key != null;
                String[] strings = key.split(",");
                BusModel busModel = new BusModel();
                busModel.setBusNo(strings[0]);
                busModel.setFromLocation(strings[1]);
                busModel.setToLocation(strings[2]);
                busModel.setDepartureTime(strings[3]);
                busModel.setArrivalTime(strings[4]);
                busModel.setDate(date);
                busModelArrayList.add(busModel);
                adapter.notifyDataSetChanged();
                Log.e(TAG,"Strings are "+strings[0]+strings[1]+strings[2]);

                Log.e(TAG, "onChildAdded: "+key);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressDialog.dismiss();
    }


    private void Initialize() {
        Date = findViewById(R.id.DatePS1);
        FindBtn = findViewById(R.id.FindPS1);
        recyclerView = findViewById(R.id.RecyclerViewPS1);



        Toolbar toolbar = findViewById(R.id.ToolbarRPS1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Seating datePicker to EditText
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "dd MMM yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                date = dateFormat.format(myCalendar.getTime());
                Date.setText(date);

            }
        };
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PassengerScreen1.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Searching for Buses...");
        progressDialog.setMessage("Just a Movement..");
        progressDialog.setCancelable(false);

    }
}