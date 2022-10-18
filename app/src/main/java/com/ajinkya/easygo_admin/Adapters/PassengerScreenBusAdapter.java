package com.ajinkya.easygo_admin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Model.BusModel;
import com.ajinkya.easygo_admin.PassengerScreen2;
import com.ajinkya.easygo_admin.R;

import java.util.ArrayList;

public class PassengerScreenBusAdapter extends RecyclerView.Adapter<PassengerScreenBusAdapter.ViewHolder>{
    ArrayList<BusModel> busModelArrayList;
    Context context;

    public PassengerScreenBusAdapter(Context context ,ArrayList<BusModel> List){
        this.context= context;
        this.busModelArrayList = List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.bus_info_ps1, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusModel busModel = busModelArrayList.get(position);
        holder.BusNo.setText(busModel.getBusNo());
        holder.Date.setText(busModel.getDate());
        holder.FromLocation.setText(busModel.getFromLocation());
        holder.ToLocation.setText(busModel.getToLocation());
        holder.StartTime.setText(busModel.getDepartureTime());
        holder.EndTime.setText(busModel.getArrivalTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BusKey = busModel.getBusNo()+","+busModel.getFromLocation()+","+busModel.getToLocation()+","+busModel.getDepartureTime()+","+busModel.getArrivalTime();
                Intent intent = new Intent(context, PassengerScreen2.class);
                intent.putExtra("BusKey",BusKey);
                intent.putExtra("Date",busModel.getDate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return busModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView BusNo,Date,FromLocation,ToLocation, StartTime,EndTime;
        public LinearLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.CardViewRPS1);
            this.BusNo = itemView.findViewById(R.id.BusNumberRPS1);
            this.Date = itemView.findViewById(R.id.DateRPS1);
            this.FromLocation = itemView.findViewById(R.id.FromLocationRPS1);
            this.ToLocation = itemView.findViewById(R.id.ToLocationRPS1);
            this.StartTime = itemView.findViewById(R.id.StartTimeRPS1);
            this.EndTime = itemView.findViewById(R.id.EndTimeRPS1);
        }
    }
}
