package com.ajinkya.easygo_admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ajinkya.easygo_admin.Interface.IFirebaseLoadDone;
import com.ajinkya.easygo_admin.Model.IDs;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddBuses extends AppCompatActivity implements IFirebaseLoadDone {
    private Spinner BusType,ToLocation, FromLocation;
    private EditText BusNumber,StartTime,EndTime,SeatAvailable,Price,Date;
    private Button AddBus;

    private String fromLocation,toLocation,startTime,endTime,date,busType;

    private boolean isStartTime = true;
    private ProgressDialog sendingData, progressDialog;
    private List<IDs> iDs;
    private final Calendar myCalendar = Calendar.getInstance();
    private IFirebaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buses);
        Initialize();
        DateTimePicker();
        setDateToEditText();
        FirebaseDataRetrieve();
        spinner();
        Confirming();
    }

    private void Initialize() {
        Toolbar toolbar = findViewById(R.id.toolbarAddBus);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        BusType = findViewById(R.id.BusTypeSpinnerAB);
        BusNumber = findViewById(R.id.BusNumberAB);
        ToLocation = findViewById(R.id.ToLocationAB);
        StartTime = findViewById(R.id.StartTimeAB);
        EndTime = findViewById(R.id.EndTimeAB);
        SeatAvailable = findViewById(R.id.SeatsAB);
        Price = findViewById(R.id.TicketPriceAB);
        Date = findViewById(R.id.DateAB);
        AddBus = findViewById(R.id.AddBusAB);
        FromLocation = findViewById(R.id.StartLocationAB);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data...");
        progressDialog.setMessage("Just a Second...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sendingData = new ProgressDialog(this);
        sendingData.setTitle("Saving data to database");
        sendingData.setCancelable(false);



    }


    private void DateTimePicker(){
        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime =true;
                SetTime();
            }
        });

        EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = false;
                SetTime();

            }
        });


    }


    private void setDateToEditText() {
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
                new DatePickerDialog(AddBuses.this, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void FirebaseDataRetrieve(){
        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("Locations");
        iFirebaseLoadDone = this;
        locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<IDs> iDs = new ArrayList<>();

                for (DataSnapshot idSnapShot:dataSnapshot.getChildren()){
                    iDs.add(idSnapShot.getValue(IDs.class));
                }
                iFirebaseLoadDone.onFirebaseLoadSuccess(iDs);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    private void Confirming(){
        AddBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner();
                String busNumber = BusNumber.getText().toString();
                String dbLocationDeparture = fromLocation;
                String dbLocationDestination = toLocation;
                String AB_NoOfSeatSt = SeatAvailable.getText().toString();

                if(!dbLocationDeparture.equals(dbLocationDestination)){

                    if(!busType.isEmpty() && !busNumber.isEmpty() && !dbLocationDeparture.isEmpty()
                            &&!dbLocationDestination.isEmpty() &&!startTime.isEmpty()&&!AB_NoOfSeatSt.isEmpty()&&!date.isEmpty()&&!Price.getText().toString().isEmpty()){

                        sendingData.show();
                        SendBusData(busType,busNumber,fromLocation,toLocation,startTime,endTime,date,AB_NoOfSeatSt);

                    }else {
                        Toast.makeText(AddBuses.this,"Please fill each box",Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(AddBuses.this,"Location is repeated",Toast.LENGTH_SHORT).show();


                }






            }
        });

    }

    private void SendBusData(String busType,String dbBusNumber,
                             String fromLocation,String toLocation,
                             String StartTime,String EndTime,String date,String busNo){
        String  route = (fromLocation + toLocation);
        DatabaseReference storingData = FirebaseDatabase.getInstance().getReference().
                child("Buses").child(date).child(route).child(dbBusNumber);
        String Ticket_price = Price.getText().toString();
        HashMap<String, String> loci = new HashMap<>();
        loci.put("FromLocation",fromLocation);
        loci.put("ToLocation",toLocation);
        loci.put("StartTime",StartTime);
        loci.put("EndTime",EndTime);
        loci.put("Date",date);
        loci.put("BusType",busType.toLowerCase());
        loci.put("BusNo",dbBusNumber);
        loci.put("NumberOfSeat",busNo);
        loci.put("TicketPrice",Ticket_price);
        storingData.setValue(loci).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    sendingData.dismiss();
                    Toast.makeText(AddBuses.this,"Bus Added Successfully..." ,Toast.LENGTH_SHORT).show();
                }else {
                    sendingData.dismiss();
                    Toast.makeText(AddBuses.this,"Failed to Add bus, Please try Again Later" ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void spinner(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.BusType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BusType.setAdapter(adapter);
        BusType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                busType =adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        FromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IDs iD = iDs.get(i);
                fromLocation = iD.getPlace();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        ToLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IDs iD = iDs.get(position);
                toLocation = iD.getPlace();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });



    }



    private void SetTime(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddBuses.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String am_pm;
                if (i>12){
                    i = i-12;
                    am_pm = "PM";
                }else am_pm = "AM";

                if (isStartTime){
                    StartTime.setText(i+":"+i1+" "+am_pm);
                    startTime = (i+":"+i1+" "+am_pm);
                }else {
                    EndTime.setText(i+":"+i1+" "+am_pm);
                    endTime = (i+":"+i1+" "+am_pm);
                }
            }
        }, hour, minute,false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }


    @Override
    public void onFirebaseLoadSuccess(List<IDs> LocationList) {

        iDs = LocationList;
        List<String> id_list= new ArrayList<>();
        for(IDs id: LocationList){
            id_list.add(id.getPlace());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,id_list);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,id_list);
            FromLocation.setAdapter(adapter);
            ToLocation.setAdapter(adapter2);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFirebaseLoadFailed(String Message) {
        progressDialog.dismiss();
        Toast.makeText(AddBuses.this,Message,Toast.LENGTH_LONG).show();

    }
}