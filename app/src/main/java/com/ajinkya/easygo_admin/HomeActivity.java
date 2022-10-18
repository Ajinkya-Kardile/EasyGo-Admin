package com.ajinkya.easygo_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    private CardView addLocation, addBus, viewBuses, viewPassengers, viewUsers, support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Initialize();
        Buttons();
    }

    private void Buttons() {
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddLocations.class);
                startActivity(intent);
            }
        });
        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddBuses.class);
                startActivity(intent);
            }
        });
        viewBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewBusActivity.class);
                startActivity(intent);
            }
        });
        viewPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PassengerScreen1.class);
                startActivity(intent);
            }
        });

        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ViewUsersActivity.class);
                startActivity(intent);
            }
        });


    }

    private void Initialize() {
        Toolbar toolbar = findViewById(R.id.HomeToolbar);
        toolbar.setTitle("EasyGo");
        setSupportActionBar(toolbar);


        addLocation = findViewById(R.id.AddLocationCard);
        addBus = findViewById(R.id.AddBusCard);
        viewBuses = findViewById(R.id.ViewBusesCard);
        viewPassengers = findViewById(R.id.ViewPassengersCard);
        viewUsers = findViewById(R.id.ViewUsersCard);
        support = findViewById(R.id.SupportCard);

    }
}