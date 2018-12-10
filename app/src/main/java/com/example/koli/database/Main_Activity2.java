package com.example.koli.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main_Activity2 extends AppCompatActivity {

    HelperClass helper;
    SQLiteDatabase db;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        helper = new HelperClass(this);
        try {
            ArrayList<DisplayEmployee> item = new ArrayList<>();

            db = helper.getReadableDatabase();

            String[] projection = {
                    Contract._ID,
                    Contract.COLUMN_NAME,
                    Contract.COLUMN_SALARY};

            Cursor cursor = db.query(Contract.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);


            int idColumnIndex = cursor.getColumnIndex(Contract._ID);
            int nameColumnIndex = cursor.getColumnIndex(Contract.COLUMN_NAME);
            int salaryColumnIndex = cursor.getColumnIndex(Contract.COLUMN_SALARY);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentSalary = cursor.getString(salaryColumnIndex);


                item.add(new DisplayEmployee(currentName, currentSalary ));
            }
            Collections.reverse(item);
            //getting count of the table
            count = cursor.getCount();

            EmployeeAdapter adapter = new EmployeeAdapter(this, item);

            ListView listView = (ListView) findViewById(R.id.list);

            listView.setAdapter(adapter);
        } finally {
            db.close();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.show_employees_info) {

            Toast.makeText(this, "There is " + count + " Employees in the Table", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (id == R.id.about_us) {
            //do something
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}





                /*
                item.add(new DisplayEmployee(name, salary));
                item.add(new DisplayEmployee("Ahmed", "3000"));
                item.add(new DisplayEmployee("Ali", "2500"));
                */

        /*
        List<Contract> employees = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Contract.TABLE_NAME + " ORDER BY " +
                Contract.COLUMN_SALARY + " ASC";

         db = helper.getWritableDatabase();
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

        */

