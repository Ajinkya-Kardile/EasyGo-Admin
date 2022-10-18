package com.ajinkya.easygo_admin;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Adapters.PassengerAdapter;
import com.ajinkya.easygo_admin.Model.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class PassengerScreen2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private String BusKey,date;

    ArrayList<UserModel> userModelArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_screen2);
        Initialize();
        Display();
    }

    private void Display() {
        PassengerAdapter adapter = new PassengerAdapter(userModelArrayList);
        recyclerView.setAdapter(adapter);

        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("Tickets").child("AdminSideCheck").child(date).child(BusKey);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserModel userModel = new UserModel();
                Log.e(TAG, "onChildAdded: "+ snapshot.child("PassengerName").getValue());
                userModel.setName(snapshot.child("PassengerName").getValue().toString());
                userModel.setPhoneNo(snapshot.child("PassengerPhone").getValue().toString());
                userModel.setSeat(snapshot.child("PassengerSeatNo").getValue().toString());
                userModelArrayList.add(userModel);
                adapter.notifyDataSetChanged();
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
        recyclerView = findViewById(R.id.RecyclerViewPS2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.ToolbarRPS2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        BusKey = getIntent().getStringExtra("BusKey");
        date = getIntent().getStringExtra("Date");


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Information...");
        progressDialog.setMessage("Just a Movement..");
        progressDialog.setCancelable(false);

    }
}