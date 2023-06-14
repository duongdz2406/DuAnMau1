package com.example.duanmau.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanmau.R;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list,ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(com.example.duanmau.R.layout.item_qlthanhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTV.setText("MA TV : "+ list.get(position).getMatv());
        holder.txtTenTV.setText("Ho ten : "+ list.get(position).getHoten());
        holder.txtNamSinh.setText("Nam sinh : "+ list.get(position).getNamsinh());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEdit(list.get(holder.getAdapterPosition()));
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = thanhVienDAO.xoaTV(list.get(holder.getAdapterPosition()).getMatv());
//                switch (check){
//                    case 1:
//                        Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
//                        loaddata();
//                        break;
//                    case 0:
//                        Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
//                        break;
//                    case -1:
//                        Toast.makeText(context, "Thanh vien ton tai phieu muon,khong duoc phep xoa", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
                if (check==1){
                    Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                       loaddata();
                }else if (check ==0){
                    Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
                }else if (check==-1){
                    Toast.makeText(context, "Thanh vien ton tai phieu muon,khong duoc phep xoa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaTV,txtTenTV,txtNamSinh;
        ImageView imgEdit,imgDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(com.example.duanmau.R.id.txtMaTV);
            txtTenTV = itemView.findViewById(com.example.duanmau.R.id.txtHotenTV);
            txtNamSinh = itemView.findViewById(com.example.duanmau.R.id.txtNamSinhTV);
            imgEdit = itemView.findViewById(com.example.duanmau.R.id.imgEditThanhVien);
            imgDel = itemView.findViewById(com.example.duanmau.R.id.imgDelThanhVien);
        }
    }
    private void showDialogEdit(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(com.example.duanmau.R.layout.dialog_suathanhvien,null);
        builder.setView(view);

        TextView txtMatv = view.findViewById(com.example.duanmau.R.id.txtMatvEdit);
        EditText edHoten =view.findViewById(com.example.duanmau.R.id.edHoTenEdit);
        EditText edNamSinh =view.findViewById(com.example.duanmau.R.id.edNamSinhEdit);
        txtMatv.setText("Ma thanh vien : "+thanhVien.getMatv());
        edHoten.setText(thanhVien.getHoten());
        edNamSinh.setText(thanhVien.getNamsinh());
        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edHoten.getText().toString();
                String namsinh = edNamSinh.getText().toString();
                int id = thanhVien.getMatv();

                boolean check = thanhVienDAO.capnhatTV(id,hoten,namsinh);
                if (check){
                    Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                    loaddata();
                }else{
                    Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
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
    private void loaddata(){
        list.clear();
        list=thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }
}
