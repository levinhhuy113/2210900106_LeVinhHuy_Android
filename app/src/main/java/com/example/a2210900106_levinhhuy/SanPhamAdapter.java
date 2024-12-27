package com.example.a2210900106_levinhhuy;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;

    public SanPhamAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        }

        SanPham sanPham = sanPhamList.get(position);

        TextView tvMaSanPham = convertView.findViewById(R.id.tvMaSanPham06);
        TextView tvTenSanPham = convertView.findViewById(R.id.tvTenSanPham06);
        TextView tvThanhTien = convertView.findViewById(R.id.tvThanhTien06);

        tvMaSanPham.setText(sanPham.getMaSanPham());
        tvTenSanPham.setText(sanPham.getTenSanPham());
        tvThanhTien.setText(String.format("Thành tiền: %.2f", sanPham.getThanhTien()));

        return convertView;
    }
}

