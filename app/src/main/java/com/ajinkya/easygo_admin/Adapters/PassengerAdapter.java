package com.ajinkya.easygo_admin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Model.UserModel;
import com.ajinkya.easygo_admin.R;

import java.util.ArrayList;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder> {
    ArrayList<UserModel> userModelArrayList;

    public PassengerAdapter(ArrayList<UserModel> userModelArrayList) {
        this.userModelArrayList = userModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.passenger_info, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = userModelArrayList.get(position);
        holder.name.setText(userModel.getName());
        holder.phoneNo.setText(userModel.getPhoneNo());
        holder.seatNo.setText(userModel.getSeat());


    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phoneNo, seatNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.NameRPS2);
            this.phoneNo = itemView.findViewById(R.id.PhoneNoRPS2);
            this.seatNo = itemView.findViewById(R.id.SeatNoRPS2);
        }
    }
}
