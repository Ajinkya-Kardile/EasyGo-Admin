package com.ajinkya.easygo_admin.Adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Model.BusModel;
import com.ajinkya.easygo_admin.R;


import java.util.ArrayList;

public class BusViewTableAdapter extends RecyclerView.Adapter<BusViewTableAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<BusModel> busModelArrayList;

    public BusViewTableAdapter(Context context, ArrayList<BusModel> busModelArrayList) {
        this.context = context;
        this.busModelArrayList = busModelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vb_table_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!busModelArrayList.isEmpty()){
            BusModel busModel = busModelArrayList.get(position);
            holder.SerialNumber.setText(String.valueOf(position+1));
            holder.Date1.setText(busModel.getDate());
            holder.BusNo.setText(busModel.getBusNo());
            holder.FromLocation.setText(busModel.getFromLocation());
            holder.ToLocation.setText(busModel.getToLocation());
            holder.StartTime.setText(busModel.getDepartureTime());
            holder.EndTime.setText(busModel.getArrivalTime());
            holder.SeatType.setText(busModel.getTypeSit());
            holder.AvailableSeats.setText(busModel.getAvailableSeat());
            holder.TicketPrice.setText(busModel.getTicketPrice());
            Log.e(TAG, "onBindViewHolder: "+busModel.getBusNo() );
        }

    }

    @Override
    public int getItemCount() {
        return busModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView SerialNumber,Date1,BusNo,FromLocation,ToLocation,StartTime,EndTime,SeatType,AvailableSeats,TicketPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SerialNumber = itemView.findViewById(R.id.SrNoTBV);
            Date1 = itemView.findViewById(R.id.DateTBV);
            BusNo = itemView.findViewById(R.id.BusNoTBV);
            FromLocation = itemView.findViewById(R.id.FromLocationTBV);
            ToLocation = itemView.findViewById(R.id.ToLocationTBV);
            StartTime = itemView.findViewById(R.id.StartTimeTBV);
            EndTime = itemView.findViewById(R.id.EndTimeTBV);
            SeatType = itemView.findViewById(R.id.SeatTypeTBV);
            AvailableSeats = itemView.findViewById(R.id.AvailableSeatsTBV);
            TicketPrice = itemView.findViewById(R.id.TicketPriceTBV);
        }
    }
}
