package com.example.android.habittrackerapp.data;

/**
 * Created by Etumusei on 7/16/2017.
 */

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        /**
         * Name of database table for pets
         */
        public final static String TABLE_NAME = "habits";

        /**
         * Unique ID number for the pet (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * excercise habit
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_EXCERCISE = "excercise";

        /**
         * .
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_PRAYING = "praying";

        /**
         * Gender of the habit.
         * <p>
         * The only possible values are {@link #EATING_DINNER}, {@link #EATING_BREAKFAST},
         * or {@link #EATING_LUNCH}.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_HABIT_EATING = "eating";

        /**
         * smooking Habits.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_HABIT_SMOKING = "smoking";

        /**
         * Possible values for the smoking .
         */
        public static final int EATING_DINNER = 0;
        public static final int EATING_BREAKFAST = 1;
        public static final int EATING_LUNCH = 2;
    }

}

