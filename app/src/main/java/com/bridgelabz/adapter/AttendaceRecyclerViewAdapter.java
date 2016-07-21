package com.bridgelabz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bridgelabz.R;
import com.bridgelabz.model.AttendaceDetailsData;
import com.bridgelabz.model.AttendanceDataModel;

import java.util.ArrayList;

public class AttendaceRecyclerViewAdapter extends RecyclerView.Adapter<AttendaceRecyclerViewAdapter.DetailViewHolder> {
    Context context;
    ArrayList<AttendanceDataModel> data;

    public AttendaceRecyclerViewAdapter(Context context, ArrayList<AttendanceDataModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_details, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.txtSrNo.setText(data.get(position).toString());
        holder.txtInTime.setText(data.get(position).toString());
        holder.txtOutTime.setText(data.get(position).toString());
        holder.txtTotalTime.setText(data.get(position).toString());
        holder.txtDate.setText(data.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView txtSrNo, txtInTime, txtOutTime, txtTotalTime, txtDate;

        DetailViewHolder(View itemView) {
            super(itemView);
            txtSrNo = (TextView) itemView.findViewById(R.id.txtSrNo);
            txtInTime = (TextView) itemView.findViewById(R.id.txtInTime);
            txtOutTime = (TextView) itemView.findViewById(R.id.txtOutTime);
            txtTotalTime = (TextView) itemView.findViewById(R.id.txtTotalTime);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        }
    }
}
