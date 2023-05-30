package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DangNhapActivity;
import com.example.duanmau.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.ThuThuDAO;
import com.example.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder>{
    private Context context;
    ArrayList<LoaiSach> list;
    int maloai;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtMaLoai.setText("Ma loai : "+list.get(position).getId());
    holder.txtTenLoai.setText("Ten loai : "+list.get(position).getTenloai());

    holder.imgDeleteLoaiSach.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getId());
            switch (check){
                case 1:
                    //load data;
                    list.clear();
                    list = loaiSachDAO.getDSLoaiSach();
                    notifyDataSetChanged();
                    break;
                case -1:
                    Toast.makeText(context, "Khong the xoa vi co sach thuoc the loai nay", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(context, "Xoa loai sach khong thanh cong", Toast.LENGTH_SHORT).show();
                    default:
                        break;
            }
        }
    });
    holder.imgEditLoaiSach.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoaiSach loaiSach = list.get(holder.getAdapterPosition());
            showDialogEditLoaiSach(loaiSach);

        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoai,txtTenLoai;
        ImageView imgDeleteLoaiSach,imgEditLoaiSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            imgDeleteLoaiSach = itemView.findViewById(R.id.imgDelLoaiSach);
            imgEditLoaiSach = itemView.findViewById(R.id.imgEditLoaiSach);
        }
    }
    private void showDialogEditLoaiSach(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setNegativeButton("Cap nhat",null)
                .setPositiveButton("Huy",null);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_sualoaisach,null);
        EditText edSua = view.findViewById(R.id.edSuaLoaiSach);
        edSua.setText(loaiSach.getTenloai());


        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edSua.getText().toString();
                 maloai = loaiSach.getId();
                 LoaiSach loaiSach1 = new LoaiSach(maloai,tenloai);
                 LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                 if (loaiSachDAO.suaLoaiSach(loaiSach1)){
                     list = loaiSachDAO.getDSLoaiSach();
                     notifyDataSetChanged();
                     alertDialog.cancel();
                 }else{
                     Toast.makeText(context, "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }



}
