package com.example.accounting_app;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class billDatabase extends SQLiteOpenHelper{
    private static final String DataBaseName="bill.db";
    private static final int DataBaseVer=1;
    private Context mContext;

    public billDatabase(Context context) {
        super(context, DataBaseName, null, DataBaseVer);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 創建資料庫表格的程式碼
        String Bill="CREATE TABLE "+"BillsList"+" ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"Date Text,"
                +"inOrout Text,"
                +"Tag Text,"
                +"Money Real,"
                +"Ps Text" +" )";
        // 執行SQL語句來創建資料庫表格
        db.execSQL(Bill);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 有要新增或刪除資料庫欄位時使用
    }
    public void insertItem(String newDate,String newInorout,String newTag,double newMoney,String newPs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date",newDate);
        values.put("inOrout",newInorout);
        values.put("Tag",newTag);
        values.put("Money",newMoney);
        values.put("Ps",newPs);
        db.insert("BillsList",null,values);
        db.close();

    }


    public void updateItem(String Id, String newDate,String newInorout,String newTag,double newMoney,String newPs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date",newDate);
        values.put("inOrout",newInorout);
        values.put("Tag",newTag);
        values.put("Money",newMoney);
        values.put("Ps",newPs);
        db.update("BillsList",values,"id=?", new String[]{String.valueOf(Id)});
        db.close();

    }

    public void showItem(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "BillsList", null);
        if (cursor.moveToFirst()) {
            do {
                //取得內容
                long id = cursor.getLong(0);
                String date = cursor.getString(1);
                String inOrOut = cursor.getString(2);
                String tag = cursor.getString(3);
                double money = cursor.getDouble(4);
                String ps = cursor.getString(5);

                // 輸出
                System.out.println("ID: " + id + ", Date: " + date + ", In/Out: " + inOrOut + ", Tag: " + tag + ", Money: " + money + ", Ps: " + ps);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
}
//資料庫名 billDatabase，表格名 BillsList

//新增、刪除、edit 項目
//主頁要讀取資料庫+顯示
//bill頁要新增


