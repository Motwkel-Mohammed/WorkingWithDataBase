
package com.example.koli.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;

import static com.example.koli.database.R.id.count;
import static com.example.koli.database.R.id.name;
import static com.example.koli.database.R.id.salary;

public class MainActivity extends AppCompatActivity {

    HelperClass helper ;
    SQLiteDatabase db ;

    EditText name_text;
    EditText salary_text;
    Button ok;
    TextView tableCount;
    TextView info;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new HelperClass(this);

        // make phone un rotated
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //tableCount = (TextView) findViewById(R.id.display_count);
        //info = (TextView) findViewById(R.id.display_info);

        ok = (Button) findViewById(R.id.ok);

        String[] projection = {
                Contract._ID,
                Contract.COLUMN_NAME,
                Contract.COLUMN_SALARY};

        TextView textView = (TextView) findViewById(R.id.count);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try {
            textView.setText("there is " + cursor.getCount() + " in the table :\n");
            db.close();

            textView.append(Contract._ID + " - " +
                    Contract.COLUMN_NAME + " - " +
                    Contract.COLUMN_SALARY + "\n");

            // Figure out the index of each column
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

                // Display the values from each column of the current row in the cursor in the TextView
                textView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentSalary));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_text = (EditText) findViewById(R.id.name);
                salary_text = (EditText) findViewById(R.id.salary);


                final String name = name_text.getText().toString();
                final String salary = salary_text.getText().toString();

                helper.insertting(name, salary);


                Intent intent = new Intent(MainActivity.this, Main_Activity2.class);

                intent.putExtra("name", name);
                intent.putExtra("salary", salary);

                startActivity(intent);

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
        if (id == R.id.insert_employee) {
            return false;
        }
        if (id == R.id.about_us) {
            //do something
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


}