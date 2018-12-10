
package com.example.koli.database;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HelperClass helper;
    SQLiteDatabase db;

    EditText search;
    EditText name_text;
    EditText salary_text;
    Button ok;
    Button delete;
    Spinner spinner;
    CheckBox checkBox;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        ArrayList<String> category = new ArrayList<String>();

        category.add(0, "Zero");
        category.add(1, "1 - 3");
        category.add(2, "4 - 7");
        category.add(3, "8 - more");

        ArrayAdapter<String> datatype = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, category);
        spinner.setAdapter(datatype);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do something
            }
        });

        helper = new HelperClass(this);

        // make phone un rotated
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //tableCount = (TextView) findViewById(R.id.display_count);
        //info = (TextView) findViewById(R.id.display_info);

        ok = (Button) findViewById(R.id.ok);

        name_text = (EditText) findViewById(R.id.name);

        salary_text = (EditText) findViewById(R.id.salary);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = name_text.getText().toString();
                final String salary = salary_text.getText().toString();

                if (name.equals("") || salary.equals("")) {
                    Toast.makeText(MainActivity.this, "Please complete your information", Toast.LENGTH_SHORT).show();

                } else {
                    helper.insertting(name, salary);
                    Intent intent = new Intent(MainActivity.this, Main_Activity2.class);
                    startActivity(intent);
                }

            }
        });


        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = (EditText) findViewById(R.id.text);
                final String searchTarget = search.getText().toString();
                db = helper.getWritableDatabase();
                db.delete(Contract.TABLE_NAME, Contract.COLUMN_NAME + " = ?",
                        new String[]{String.valueOf(searchTarget)});
                db.close();

            }
        });

    }



    //_____________________________________________________________________________

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
            Intent intent = new Intent(MainActivity.this, Main_Activity2.class);
            startActivity(intent);
            return false;
        }
        if (id == R.id.about_us) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


}