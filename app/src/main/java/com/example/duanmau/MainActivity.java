package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.dao.ThuThuDAO;
import com.example.duanmau.fragment.QLLoaiSachFragment;
import com.example.duanmau.fragment.QLPhieuMuonFragment;
import com.example.duanmau.fragment.ThongKeDoanhThuFragment;
import com.example.duanmau.fragment.ThongKeTop10;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toobar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
         drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId()==R.id.QLPhieuMuon){
                    fragment = new QLPhieuMuonFragment();
                } else if (item.getItemId()==R.id.QLLoaiSach) {
                    fragment = new QLLoaiSachFragment();
                }else if(item.getItemId()==R.id.Thoat){
                    Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if (item.getItemId()==R.id.DoiMatKhau){
                    showDialogDoiMK();
                } else if (item.getItemId()==R.id.Top10) {
                    fragment = new ThongKeTop10();
                }else if (item.getItemId()==R.id.DoanhThu) {
                    fragment = new ThongKeDoanhThuFragment();
                }

                if (fragment!=null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout,fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);


                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId()==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
      }
        return super.onOptionsItemSelected(item);
    }
    private void showDialogDoiMK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cap nhat",null)
                .setPositiveButton("Huy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau,null);
        EditText edPassCu = view.findViewById(R.id.edPassCu);
        EditText edPassMoi = view.findViewById(R.id.edPassMoi);
        EditText edPassNhapLai = view.findViewById(R.id.edPassMoiNhapLai);


        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passCu = edPassCu.getText().toString();
                String passMoi = edPassMoi.getText().toString();
                String passMoiNhapLai = edPassNhapLai.getText().toString();
                if (passCu.equals("")||passMoi.equals("")||passMoiNhapLai.equals("")){
                    Toast.makeText(MainActivity.this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                }
                if (passMoi.equals(passMoiNhapLai)){
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
                    String matt = sharedPreferences.getString("matt","");

                    // cap nhat

                    ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                    int check = thuThuDAO.capNhatMK(matt,passCu,passMoi);
                    if (check==1){
                        Toast.makeText(MainActivity.this, "Cap nhat mat khau thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else if (check ==0){
                        Toast.makeText(MainActivity.this, "Mat khau cu khong dung", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(MainActivity.this, "Cap nhat mat khau that bai", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Nhap mat khua khong trung nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}