package com.example.android.habittrackerapp.data;

import com.example.android.habittrackerapp.data.HabitContract.HabitEntry;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class CatelogActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catelog);
        // Setup FAB to open EditorActivity

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatelogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the habits database.
     */
    public Cursor displayDatabaseInfo() {
        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        mDbHelper.getDatabaseName();
        try {
            mDbHelper.getDatabaseName();
        } catch (SQLException sqle) {
            throw sqle;
        }

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_EXCERCISE,
                HabitEntry.COLUMN_HABIT_PRAYING,
                HabitEntry.COLUMN_HABIT_EATING,
                HabitEntry.COLUMN_HABIT_SMOKING};

        // Perform a query on the habits table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> habits.
            // _id -  -praying - eating- weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The Habit table contains " + cursor.getCount() + " habit.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_EXCERCISE + " - " +
                    HabitEntry.COLUMN_HABIT_PRAYING + " - " +
                    HabitEntry.COLUMN_HABIT_EATING + " - " +
                    HabitEntry.COLUMN_HABIT_SMOKING + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int excerciseColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_EXCERCISE);
            int prayingColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_PRAYING);
            int eatingColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_EATING);
            int smokingColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_SMOKING);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentExcercise = cursor.getString(excerciseColumnIndex);
                String currentPraying = cursor.getString(prayingColumnIndex);
                int currentEating = cursor.getInt(eatingColumnIndex);
                int currentSmoking = cursor.getInt(smokingColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentExcercise + " - " +
                        currentPraying + " - " +
                        currentEating + " - " +
                        currentSmoking));
            }
            return cursor;
        }
        finally {
            // Always close theursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded habit data into the database. For debugging purposes only.
     */
    private void insertHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and habit  attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_EXCERCISE, "Aerobics");
        values.put(HabitEntry.COLUMN_HABIT_PRAYING, "Confession");
        values.put(HabitEntry.COLUMN_HABIT_EATING, HabitEntry.EATING_BREAKFAST);
        values.put(HabitEntry.COLUMN_HABIT_SMOKING, 1);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the habit table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catelog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                Toast.makeText(this, "Adding data", Toast.LENGTH_LONG).show();
                insertHabit();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                Toast.makeText(this, "Task Deleted", Toast.LENGTH_LONG).show();
                //delete the data
                return true;
        }
        return false;
    }
}
