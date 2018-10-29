package com.example.nitin.sqlitedemo;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "myemployeedatabase";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "createEmployeeTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "department";
    public static final String COL_4 = "salary";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,department TEXT,salary)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public List<DataModel> getdata() {
        List<DataModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new DataModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("ID"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String salary = cursor.getString(cursor.getColumnIndexOrThrow("salary"));
            String department = cursor.getString(cursor.getColumnIndexOrThrow("department"));
            dataModel.setName(name);
            dataModel.setSalary(salary);
            dataModel.setDepartment(department);
            dataModel.setID(id);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }
}
