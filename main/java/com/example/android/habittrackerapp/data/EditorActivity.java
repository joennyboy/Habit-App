package com.example.android.habittrackerapp.data;

import com.example.android.habittrackerapp.data.HabitContract.HabitEntry;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the habits excercise
     */
    private EditText mExcerciseEditText;

    /**
     * EditText field to enter the praying habit
     */
    private EditText mPrayingEditText;

    /**
     * EditText field to enter the  smooking habit
     */
    private EditText mSmokingEditText;

    /**
     * Spinner field to enter the  eating habit
     */
    private Spinner mEatingSpinner;
    /**
     * Gender of the pet. The possible valid values are in the PetContract.java file:
     * {@link HabitEntry#EATING_BREAKFAST}, {@link HabitEntry#EATING_LUNCH}, or
     * {@link HabitEntry#EATING_DINNER}.
     */
    private int mEating = HabitEntry.EATING_BREAKFAST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mExcerciseEditText = (EditText) findViewById(R.id.edit_habit_excercise);
        mPrayingEditText = (EditText) findViewById(R.id.edit_habit_praying);
        mSmokingEditText = (EditText) findViewById(R.id.edit_habit_smoking);
        mEatingSpinner = (Spinner) findViewById(R.id.spinner_eating);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the eating habit .
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter eatingSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_eating_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        eatingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mEatingSpinner.setAdapter(eatingSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mEatingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.eating_breakfast))) {
                        mEating = HabitEntry.EATING_BREAKFAST;
                    } else if (selection.equals(getString(R.string.eating_lunch))) {
                        mEating = HabitEntry.EATING_LUNCH;
                    } else {
                        mEating = HabitEntry.EATING_DINNER;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mEating = HabitEntry.EATING_DINNER;
            }
        });
    }

    /**
     * Get user input from editor and save new habit into database.
     */
    private void insertPet() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String excerciseString = mExcerciseEditText.getText().toString().trim();
        String prayingString = mPrayingEditText.getText().toString().trim();
        String smookingString = mSmokingEditText.getText().toString().trim();
        int smoking = Integer.parseInt(smookingString);

        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_EXCERCISE, excerciseString);
        values.put(HabitEntry.COLUMN_HABIT_PRAYING, prayingString);
        values.put(HabitEntry.COLUMN_HABIT_EATING, mEating);
        values.put(HabitEntry.COLUMN_HABIT_SMOKING, smoking);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving Habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertPet();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}