package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.dao.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        EditText ed_user = findViewById(R.id.ed_user);
        EditText ed_pass = findViewById(R.id.ed_pass);
        Button btn_dangnhap = findViewById(R.id.btn_dangnhap);
        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ed_user.getText().toString();
                String pass = ed_pass.getText().toString();
                if (thuThuDAO.checkDangNhap(user,pass)){
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt",user);
                    editor.commit();
                    startActivity(new Intent(DangNhapActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(DangNhapActivity.this, "Username va mat khau khong dung", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}