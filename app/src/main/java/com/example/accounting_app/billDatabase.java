package com.example.accounting_app;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import com.example.accounting_app.arrayListDef;

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

    //Dynamic創建新表格
    public void createNewTable(String tableName){
        //tableName遵守以下格式：Bill_YYYY_MM
        SQLiteDatabase db = this.getWritableDatabase();
        String Bill="CREATE TABLE "+tableName+" ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"Date Text,"
                +"inOrout Text,"
                +"Tag Text,"
                +"Money Real,"
                +"Ps Text" +" )";
        db.execSQL(Bill);
    }

    //判斷對應表格是否存在
    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean tableExists = cursor.getCount() > 0;
        cursor.close();
        return tableExists;
    }

    public void insertItem(String tableName,String newDate,String newInorout,String newTag,double newMoney,String newPs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date",newDate);
        values.put("inOrout",newInorout);
        values.put("Tag",newTag);
        values.put("Money",newMoney);
        values.put("Ps",newPs);
        db.insert(tableName,null,values);
        db.close();

    }


    public void updateItem(String tableName,long Id, String newDate,String newInorout,String newTag,double newMoney,String newPs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date",newDate);
        values.put("inOrout",newInorout);
        values.put("Tag",newTag);
        values.put("Money",newMoney);
        values.put("Ps",newPs);
        db.update(tableName,values,"_id=?", new String[]{String.valueOf(Id)});
        db.close();

    }

    public ArrayList<arrayListDef> showItem(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        ArrayList<arrayListDef> BillsForReturnToFirstFeg = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                //取得內容
                long id = cursor.getLong(0);
                String date = cursor.getString(1);
                String inOrOut = cursor.getString(2);
                String tag = cursor.getString(3);
                double money = cursor.getDouble(4);
                String ps = cursor.getString(5);

                BillsForReturnToFirstFeg.add(new arrayListDef(id,date,inOrOut,tag,money,ps));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return BillsForReturnToFirstFeg;
    }

    public void deleteItem(String tableName,long idToDelete){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(tableName,"_id = ?",new String[]{String.valueOf(idToDelete)});
        db.close();

    }
}
//資料庫名 billDatabase，表格名 BillsList
//格式：ID、日期、收支、tag、金額、備註
//新增、刪除、edit 項目
//主頁要讀取資料庫+顯示
//bill頁要新增


