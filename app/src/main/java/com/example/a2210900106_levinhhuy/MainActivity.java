package com.example.a2210900106_levinhhuy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvSanPham;
    private Button btnThemSanPham;
    private DatabaseHelper dbHelper;
    private ArrayList<SanPham> sanPhamList;
    private SanPhamAdapter adapter;

    private static final int REQUEST_CODE = 1;  // Mã yêu cầu cho Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvSanPham = findViewById(R.id.lvSanPham06);
        btnThemSanPham = findViewById(R.id.btnThemSanPham06);

        dbHelper = new DatabaseHelper(this);
        sanPhamList = new ArrayList<>();
        adapter = new SanPhamAdapter(this, sanPhamList);
        lvSanPham.setAdapter(adapter);

        // Đăng ký menu ngữ cảnh cho ListView
        registerForContextMenu(lvSanPham);

        // Hiển thị danh sách sản phẩm
        loadData();

        // Xử lý nút thêm sản phẩm
        btnThemSanPham.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SanPhamDetailActivity.class);
            startActivityForResult(intent, REQUEST_CODE);  // Sử dụng startActivityForResult để nhận kết quả
        });
    }

    private void loadData() {
        sanPhamList.clear();
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                double price = cursor.getDouble(3);
                sanPhamList.add(new SanPham(id, name, quantity, price));
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    // Tạo menu ngữ cảnh
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        SanPham selectedProduct = sanPhamList.get(info.position);

        // Kiểm tra ID của menu item
        if (item.getItemId() == R.id.menuSua) {
            Intent intent = new Intent(MainActivity.this, SanPhamDetailActivity.class);
            intent.putExtra("MaSanPham", selectedProduct.getMaSanPham());
            startActivityForResult(intent, REQUEST_CODE);  // Sử dụng startActivityForResult để nhận kết quả
            return true;
        } else if (item.getItemId() == R.id.menuXoa) {
            dbHelper.deleteProduct(selectedProduct.getMaSanPham());
            loadData();
            Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.menuChiTiet) {
            Toast.makeText(this, selectedProduct.getTenSanPham(), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onContextItemSelected(item);  // Gọi phương thức lớp cha
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Kiểm tra cờ từ Intent để xác nhận có phải làm mới dữ liệu không
            if (data != null && data.getBooleanExtra("updateData", false)) {
                loadData();  // Làm mới dữ liệu
            }
        }
    }
}
