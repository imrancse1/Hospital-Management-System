package com.example.bubt.MedScape;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PatientDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expenseDB";
    public static final int DATABASE_VERSION = 1;

    public static final String EXPENSE_TABLE = "expense_table";

    public static final String EXPENSE_COL_ID = "id";
    public static final String EXPENSE_COL_NAME = "name";
    public static final String EXPENSE_COL_CATEGORY = "category";
    public static final String EXPENSE_COL_AMOUNT = "amount";
    public static final String EXPENSE_COL_DATE = "date";
    public static final String EXPENSE_COL_PHOTO = "photo";

    private static final int CATEGORY_GROCERY = 0;
    private static final int CATEGORY_UTILITY = 1;
    private static final int CATEGORY_PERSONAL_EXPENSE = 2;
    private static final int CATEGORY_HOUSING = 3;
    private static final int CATEGORY_HEALTH_CARE = 4;
    private static final int CATEGORY_ENTERTAINMENT = 5;
    private static final int CATEGORY_TRANSPORT = 6;
    private static final int CATEGORY_OTHERS = 7;

    public static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + EXPENSE_TABLE + " (" +
            EXPENSE_COL_ID + " INTEGER PRIMARY KEY, " +
            EXPENSE_COL_NAME + " TEXT, " +
            EXPENSE_COL_CATEGORY + " INTEGER, " +
            EXPENSE_COL_AMOUNT + " INTEGER, " +
            EXPENSE_COL_DATE + " REAL, "+
            EXPENSE_COL_PHOTO + " BLOB );";

    public PatientDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
