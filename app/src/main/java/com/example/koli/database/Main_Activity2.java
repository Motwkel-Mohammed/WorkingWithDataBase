package com.example.koli.database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main_Activity2 extends AppCompatActivity {

    HelperClass helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        Intent intent = getIntent();

        final String name = intent.getStringExtra("name");
        final String salary = intent.getStringExtra("salary");

                ArrayList<DisplayEmployee> item = new ArrayList<>();
                item.add(new DisplayEmployee("Mohamed", "3500"));
                item.add(new DisplayEmployee(name, salary));
                item.add(new DisplayEmployee("Ahmed", "3000"));
                item.add(new DisplayEmployee("Ali", "2500"));

                EmployeeAdapter adapter = new EmployeeAdapter(this, item);

                ListView listView = (ListView) findViewById(R.id.list);

                listView.setAdapter(adapter);

    }
}
