package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;
    private  ArrayList<HashMap<String,Object>> listHm;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list,ArrayList<HashMap<String,Object>> listHm,SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHm= listHm;
        this.sachDAO =sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_qlsach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMasach.setText("Ma Sach : "+list.get(position).getMasach());
        holder.txtTensach.setText("Ten Sach : "+list.get(position).getTensach());
        holder.txtGiathue.setText("Gia : "+list.get(position).getGiathue());
        holder.txtMaloai.setText("Ma Loai : "+list.get(position).getMaloai());
        holder.txtTenloai.setText("Ten loai : "+list.get(position).getTenloai());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "xoa ko thanh cong", Toast.LENGTH_SHORT).show();

                        break;
                    case -1:
                        Toast.makeText(context, "xoa ko thanh cong vi sach co trong phieu muon", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMasach,txtTensach,txtGiathue,txtMaloai,txtTenloai;
        ImageView imgEdit,imgDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMasach = itemView.findViewById(R.id.txtMaSach1);
            txtTensach = itemView.findViewById(R.id.txtTenSach1);
            txtGiathue = itemView.findViewById(R.id.txtGiaThue1);
            txtMaloai = itemView.findViewById(R.id.txtMaLoai1);
            txtTenloai = itemView.findViewById(R.id.txtTenLoai1);
            imgEdit = itemView.findViewById(R.id.imgEditSach);
            imgDel = itemView.findViewById(R.id.imgDelSach);
        }
    }
    private void showDialog(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suasach,null);

        builder.setView(view);

        EditText edTenSach = view.findViewById(R.id.edSuaTenSach);
        EditText edTien = view.findViewById(R.id.edSuaTien);
        TextView txtMaSach = view.findViewById(R.id.txtMaSachSua);
        Spinner spnLoaiSach = view.findViewById(R.id.spnSuaLoaiSach);

        txtMaSach.setText("MaSach "+sach.getMasach());
        edTenSach.setText(sach.getTensach());
        edTien.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHm,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);

        int index =0;
        int position=-1;
        for (HashMap<String,Object> item :listHm){
            if ((int)item.get("maloai")== sach.getMaloai()){
                position = index;
            }
            index++;
        }
        spnLoaiSach.setSelection(position);
        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edTenSach.getText().toString();
                int tien = Integer.parseInt(edTien.getText().toString());
                HashMap<String,Object> hs  = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");
                boolean check = sachDAO.capNhatSach(sach.getMasach(),tensach,tien,maloai);
                if (check){
                    Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                }else{
                    Toast.makeText(context, "khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void loadData(){
        list.clear();
        list = sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }
}
