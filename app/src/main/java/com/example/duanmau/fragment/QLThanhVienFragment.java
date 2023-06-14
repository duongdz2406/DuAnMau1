package com.example.duanmau.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QLThanhVienFragment extends Fragment {
    ThanhVienDAO thanhVienDAO;
    RecyclerView recyclerTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien,container,false);
         recyclerTV = view.findViewById(R.id.recyclerThanhVien);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAddThanhVien);
         thanhVienDAO = new ThanhVienDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themthanhvien,null);
        builder.setView(view);

        EditText edHoten = view.findViewById(R.id.edHoTen);
        EditText edNamSinh = view.findViewById(R.id.edNamSinh);

        builder.setNegativeButton("Them", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edHoten.getText().toString();
                String namsinh = edNamSinh.getText().toString();
                boolean check = thanhVienDAO.themTV(hoten,namsinh);
                if (check){
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
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
    private void  loadData(){
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTV.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(),list,thanhVienDAO);
        recyclerTV.setAdapter(adapter);
    }
}
