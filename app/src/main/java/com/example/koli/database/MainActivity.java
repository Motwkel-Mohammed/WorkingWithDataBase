
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

import static com.example.koli.database.R.id.count;

public class MainActivity extends AppCompatActivity {

    HelperClass helper;
    SQLiteDatabase db;

    EditText name_text;
    EditText salary_text;
    Button ok;
    TextView tableCount;
    TextView info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // make phone un rotated
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //tableCount = (TextView) findViewById(R.id.display_count);
        //info = (TextView) findViewById(R.id.display_info);

        ok = (Button) findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_text = (EditText) findViewById(R.id.name);
                salary_text = (EditText) findViewById(R.id.salary);



                final String name = name_text.getText().toString();
                final String salary = salary_text.getText().toString();

                Intent intent = new Intent(MainActivity.this, Main_Activity2.class);

                intent.putExtra("name", name);
                intent.putExtra("salary", salary);

                startActivity(intent);



            }
        });

    }

//_____________________________________________________________________________

    private void insertPet() {
        // Gets the database in write mode
         db = helper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(Contract.COLUMN_NAME, "Toto");
        values.put(Contract.COLUMN_SALARY, "Terrier");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(Contract.TABLE_NAME, null, values);
        Log.v("MainActivity","problem in "+newRowId);
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
         db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Contract._ID,
                Contract.COLUMN_NAME,
                Contract.COLUMN_SALARY};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                Contract.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.count);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + " Employee.\n\n");
            displayView.append(Contract._ID + " - " +
                    Contract.COLUMN_NAME + " - " +
                    Contract.COLUMN_SALARY + " \n " );

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(Contract._ID);
            int nameColumnIndex = cursor.getColumnIndex(Contract.COLUMN_NAME);
            int breedColumnIndex = cursor.getColumnIndex(Contract.COLUMN_SALARY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBreed + " "));}
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
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
        if (id == R.id.insert_employee) {
            insertPet();
            displayDatabaseInfo();
            return false;
        }
        if (id == R.id.about_us) {
            //do something
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


}