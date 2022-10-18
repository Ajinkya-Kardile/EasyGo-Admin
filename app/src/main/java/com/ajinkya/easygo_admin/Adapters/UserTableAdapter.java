package com.ajinkya.easygo_admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.easygo_admin.Model.UserModel;
import com.ajinkya.easygo_admin.R;

import java.util.ArrayList;

public class UserTableAdapter extends RecyclerView.Adapter<UserTableAdapter.ViewHolder> {
    Context context;

    public UserTableAdapter(Context context, ArrayList<UserModel> userModelArrayList) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
    }

    ArrayList<UserModel> userModelArrayList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vu_table_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!userModelArrayList.isEmpty()){
            UserModel userModel = userModelArrayList.get(position);
            holder.SrNo.setText(String.valueOf(position+1));
            holder.UserName.setText(userModel.getName());
            holder.MobileNo.setText(userModel.getPhoneNo());
            holder.EmailID.setText(userModel.getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView SrNo, UserName, MobileNo, EmailID;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SrNo = itemView.findViewById(R.id.SrNoTBU);
            UserName = itemView.findViewById(R.id.UserNameTBU);
            MobileNo = itemView.findViewById(R.id.MobileNoTBU);
            EmailID = itemView.findViewById(R.id.EmailIdTBU);
        }
    }
}
