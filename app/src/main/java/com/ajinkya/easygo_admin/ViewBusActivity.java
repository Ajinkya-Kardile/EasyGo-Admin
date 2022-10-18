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

import com.ajinkya.easygo_admin.Adapters.BusViewTableAdapter;
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

public class ViewBusActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText Date;
    private Button Search;
    private ProgressDialog progressDialog;
    private ArrayList<BusModel> busModelArrayList;
    private BusViewTableAdapter adapter;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus);

        Initialize();
        Buttons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Date.setText("");
        date="";
    }

    private void Buttons() {
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Date.getText().toString().isEmpty()){
                    progressDialog.show();
                    busModelArrayList = new ArrayList<>();
                    adapter = new BusViewTableAdapter(ViewBusActivity.this,busModelArrayList);
                    recyclerView.setAdapter(adapter);
                    FirebaseDataRetrieve();
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(ViewBusActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void FirebaseDataRetrieve() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("Buses").child(date);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DatabaseReference databaseReference1 = snapshot.getRef();
                Log.e(TAG, "onChildAdded: "+databaseReference1 );
                databaseReference1.addChildEventListener(new ChildEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        BusModel busModel = new BusModel();
                        busModel.setBusNo(Objects.requireNonNull(snapshot.child("BusNo").getValue()).toString());
                        busModel.setDate(Objects.requireNonNull(snapshot.child("Date").getValue()).toString());
                        busModel.setFromLocation(Objects.requireNonNull(snapshot.child("FromLocation").getValue()).toString());
                        busModel.setToLocation(Objects.requireNonNull(snapshot.child("ToLocation").getValue()).toString());
                        busModel.setArrivalTime(Objects.requireNonNull(snapshot.child("StartTime").getValue()).toString());
                        busModel.setDepartureTime(Objects.requireNonNull(snapshot.child("EndTime").getValue()).toString());
                        busModel.setTicketPrice(Objects.requireNonNull(snapshot.child("TicketPrice").getValue()).toString());
                        busModel.setTypeSit(Objects.requireNonNull(snapshot.child("BusType").getValue()).toString());
                        busModel.setAvailableSeat(Objects.requireNonNull(snapshot.child("NumberOfSeat").getValue()).toString());
                        Log.e(TAG, "onChildAdded: "+ Objects.requireNonNull(snapshot.child("BusNo").getValue()) );
                        busModelArrayList.add(busModel);
                        adapter.notifyDataSetChanged();
                        Log.e(TAG, "onChildAdded: "+busModelArrayList.size());

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
    }


    private void Initialize() {
        Date = findViewById(R.id.DateVB);
        Search = findViewById(R.id.SearchVB);
        recyclerView = findViewById(R.id.RecyclerViewVB);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        Toolbar toolbar = findViewById(R.id.ToolbarVB);
        setSupportActionBar(toolbar);
        toolbar.setTitle("View Buses");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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
                new DatePickerDialog(ViewBusActivity.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Searching for Buses...");
        progressDialog.setMessage("Just a Movement..");
        progressDialog.setCancelable(false);

    }
}