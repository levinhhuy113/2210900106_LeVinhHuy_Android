package com.example.a2210900106_levinhhuy;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLSanpham.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và cột
    private static final String TABLE_NAME = "SanPham06";
    private static final String COL_ID = "MaSanPham";
    private static final String COL_NAME = "TenSanPham";
    private static final String COL_QUANTITY = "SoLuong";
    private static final String COL_PRICE = "DonGia";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " TEXT PRIMARY KEY, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_QUANTITY + " INTEGER NOT NULL, " +
                COL_PRICE + " REAL NOT NULL)";
        db.execSQL(createTable);

        // Thêm dữ liệu mẫu
        String insertData = "INSERT INTO " + TABLE_NAME + " (" + COL_ID + ", " + COL_NAME + ", " + COL_QUANTITY + ", " + COL_PRICE + ") VALUES " +
                "('SP001', 'Sản phẩm 1', 5, 20000)," +
                "('SP002', 'Sản phẩm 2', 15, 15000)," +
                "('SP003', 'Sản phẩm 3', 8, 10000)";
        db.execSQL(insertData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Phương thức thêm sản phẩm
    public long addProduct(String id, String name, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_PRICE, price);
        return db.insert(TABLE_NAME, null, values);
    }

    // Phương thức lấy tất cả sản phẩm
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Phương thức sửa sản phẩm
    public int updateProduct(String id, String name, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_PRICE, price);
        return db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id});
    }

    // Phương thức xóa sản phẩm
    public int deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{id});
    }
}
