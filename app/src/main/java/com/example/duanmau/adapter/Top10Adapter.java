package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thongke10,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Ma sach : "+String.valueOf(list.get(position).getMasach()));
        holder.txtTenSach.setText("Ten sach : "+list.get(position).getTensach());
        holder.txtSoLuong.setText("So luong muon : "+String.valueOf(list.get(position).getSoluongmuon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach,txtTenSach,txtSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSach=itemView.findViewById(R.id.txtMaSachTop10);
            txtTenSach=itemView.findViewById(R.id.txtTenSachTop10);
            txtSoLuong=itemView.findViewById(R.id.txtSoLuongMuonTop10);

        }
    }
}
