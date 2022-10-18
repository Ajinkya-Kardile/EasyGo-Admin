package com.ajinkya.easygo_admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Adapters.UserTableAdapter;
import com.ajinkya.easygo_admin.Model.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ViewUsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ArrayList<UserModel> userModelArrayList;
    private UserTableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        Initialize();
        DisplayDetails();
    }

    private void DisplayDetails() {
        progressDialog.show();
        userModelArrayList = new ArrayList<>();
        adapter = new UserTableAdapter(ViewUsersActivity.this, userModelArrayList);
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.hasChild("Name") && snapshot.hasChild("Email") && snapshot.hasChild("Phone")) {
                    {
                        UserModel userModel = new UserModel();
                        userModel.setName(Objects.requireNonNull(snapshot.child("Name").getValue()).toString());
                        userModel.setPhoneNo(Objects.requireNonNull(snapshot.child("Phone").getValue()).toString());
                        userModel.setEmail(Objects.requireNonNull(snapshot.child("Email").getValue()).toString());
                        userModelArrayList.add(userModel);
                        adapter.notifyDataSetChanged();
                    }
                }else
                    Toast.makeText(ViewUsersActivity.this, "Data not Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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
        recyclerView = findViewById(R.id.RecyclerViewVU);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.ToolbarVU);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitle("User Details");


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Searching for Users...");
        progressDialog.setMessage("Just a Movement..");
        progressDialog.setCancelable(false);
    }
}