package com.example.koli.database;

/**
 * Created by Koli on 11/10/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HelperClass extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employee.dp";

    private static final int DATABASE_VERSION = 3;

    String CREATE_TABLE = "CREATE TABLE " + Contract.TABLE_NAME + " ("
            + Contract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Contract.COLUMN_NAME + " TEXT NOT NULL, "
            + Contract.COLUMN_SALARY + " TEXT NOT NULL );";

    public HelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_NAME);
        onCreate(db);
    }

    //________________________________________________________________________________
    final public long insertting(String name, String salary) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Contract.COLUMN_NAME, name);
        values.put(Contract.COLUMN_SALARY, salary);

        // insert row
        long id = db.insert(Contract.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //________________________________________________________________________________
    public Contract getEmployee(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Contract.TABLE_NAME,
                new String[]{Contract._ID, Contract.COLUMN_NAME, Contract.COLUMN_SALARY},
                Contract._ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare contract object
        Contract contract = new Contract(
                cursor.getInt(cursor.getColumnIndex(Contract._ID)),
                cursor.getString(cursor.getColumnIndex(Contract.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Contract.COLUMN_SALARY)));

        // close the db connection
        cursor.close();

        return contract;
    }

    //________________________________________________________________________________
    public List<Contract> getAllEmployee() {
        List<Contract> employees = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Contract.TABLE_NAME + " ORDER BY " +
                Contract.COLUMN_SALARY + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contract contract = new Contract();
                contract.setId(cursor.getInt(cursor.getColumnIndex(Contract._ID)));
                contract.setName(cursor.getString(cursor.getColumnIndex(Contract.COLUMN_NAME)));
                contract.setSalary(cursor.getString(cursor.getColumnIndex(Contract.COLUMN_SALARY)));

                employees.add(contract);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return employees list
        return employees;
    }

    //________________________________________________________________________________
    public int getEmployeeCount() {
        String countQuery = "SELECT  * FROM " + Contract.TABLE_NAME;
        //open the database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    //________________________________________________________________________________
    public int updateEmployee(Contract contract) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.COLUMN_NAME, contract.getName());
        values.put(Contract.COLUMN_SALARY, contract.getSalary());

        // updating row
        return db.update(Contract.TABLE_NAME, values, Contract._ID + " = ?",
                new String[]{String.valueOf(contract.getId())});
    }

    //________________________________________________________________________________
    public void deleteEmployee(Contract contract) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Contract.TABLE_NAME, Contract._ID + " = ?",
                new String[]{String.valueOf(contract.getId())});
        db.close();
    }
}
