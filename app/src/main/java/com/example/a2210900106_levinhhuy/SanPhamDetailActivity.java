package com.example.a2210900106_levinhhuy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SanPhamDetailActivity extends AppCompatActivity {
    private EditText etMaSanPham, etTenSanPham, etSoLuong, etDonGia;
    private Button btnLuu, btnThoat;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham_detail);

        etMaSanPham = findViewById(R.id.etMaSanPham06);
        etTenSanPham = findViewById(R.id.etTenSanPham06);
        etSoLuong = findViewById(R.id.etSoLuong06);
        etDonGia = findViewById(R.id.etDonGia06);
        btnLuu = findViewById(R.id.btnLuuSanPham06);
        btnThoat = findViewById(R.id.btnThoat06);

        dbHelper = new DatabaseHelper(this);

        // Kiểm tra có phải sửa sản phẩm không
        String maSanPham = getIntent().getStringExtra("MaSanPham");
        if (maSanPham != null) {
            Cursor cursor = dbHelper.getAllProducts();
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(maSanPham)) {
                    etMaSanPham.setText(cursor.getString(0));
                    etTenSanPham.setText(cursor.getString(1));
                    etSoLuong.setText(String.valueOf(cursor.getInt(2)));
                    etDonGia.setText(String.valueOf(cursor.getDouble(3)));
                    break;
                }
            }
            etMaSanPham.setEnabled(false);
        }

        // Xử lý nút lưu
        btnLuu.setOnClickListener(v -> {
            String id = etMaSanPham.getText().toString();
            String name = etTenSanPham.getText().toString();
            int quantity = Integer.parseInt(etSoLuong.getText().toString());
            double price = Double.parseDouble(etDonGia.getText().toString());

            if (maSanPham == null) {
                dbHelper.addProduct(id, name, quantity, price);
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateProduct(id, name, quantity, price);
                Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }

            // Gửi Intent trở lại MainActivity để làm mới dữ liệu
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updateData", true);  // Cờ để MainActivity biết phải cập nhật dữ liệu
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Xử lý nút thoát
        btnThoat.setOnClickListener(v -> finish());
    }
}
