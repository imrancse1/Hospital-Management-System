package com.example.bubt.MedScape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PatientDataSource {

    private PatientDatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Patient patient;

    PatientDataSource(Context context) {
        databaseHelper = new PatientDatabaseHelper(context);
    }

    public void openConnection() {
        database = databaseHelper.getWritableDatabase();
    }

    public void closeConnection() {
        database.close();
    }

    public boolean addExpense(Patient patient) {

        openConnection();
        ContentValues values = new ContentValues();
        values.put(PatientDatabaseHelper.EXPENSE_COL_NAME, patient.getName());
        values.put(PatientDatabaseHelper.EXPENSE_COL_CATEGORY, patient.getCategory());
        values.put(PatientDatabaseHelper.EXPENSE_COL_AMOUNT, patient.getAmount());
        values.put(PatientDatabaseHelper.EXPENSE_COL_DATE, patient.getUnixDateTime());
        values.put(PatientDatabaseHelper.EXPENSE_COL_PHOTO, patient.getPhoto());
        long savedRowID = database.insert(PatientDatabaseHelper.EXPENSE_TABLE, null, values);
        closeConnection();
        if (savedRowID > 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<Patient> getAllExpense() {
        List<Patient> patientList = new ArrayList<>();
        openConnection();
        //String query = "SELECT * FROM expense_table ORDER BY EXPENSE_COL_DATE DESC";
        //String order = PatientDatabaseHelper.EXPENSE_COL_DATE+"DESC";
        Cursor cursor = database.query(PatientDatabaseHelper.EXPENSE_TABLE, null, null, null, null, null, null);
        if (cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_NAME));
                int category = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_AMOUNT));
                long date = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_DATE));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_PHOTO));
                Patient patient = new Patient(id, name, category, amount, date, photo);
                patientList.add(patient);
            }while (cursor.moveToNext());
            cursor.close();
            closeConnection();
        }

        return patientList;
    }

    public ArrayList<Patient> getCategoryExpense(int categoryIndex) {
        ArrayList<Patient> patientList = new ArrayList<>();
        openConnection();

        String whereClause = PatientDatabaseHelper.EXPENSE_COL_CATEGORY + "=?";
        String[] selectionArgs = {String.valueOf(categoryIndex)};

        Cursor cursor = database.query(PatientDatabaseHelper.EXPENSE_TABLE, null, whereClause,
                selectionArgs, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_NAME));
                int category = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_AMOUNT));
                long date = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_DATE));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_PHOTO));
                Patient patient = new Patient(id, name, category, amount, date, photo);
                patientList.add(patient);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeConnection();
        return patientList;
    }

    public ArrayList<Patient> getExpenseByDate(long fromDate, long toDate) {
        ArrayList<Patient> patientList = new ArrayList<>();
        openConnection();

        String whereClause = PatientDatabaseHelper.EXPENSE_COL_DATE
                + ">= ?"
                + " AND " +
                PatientDatabaseHelper.EXPENSE_COL_DATE
                + "<= ?";
        String[] selectionArgs = {String.valueOf(fromDate), String.valueOf(toDate)};

        Cursor cursor = database.query(PatientDatabaseHelper.EXPENSE_TABLE, null,
                whereClause,
                selectionArgs, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_NAME));
                int category = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_AMOUNT));
                long date = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_DATE));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_PHOTO));
                Patient patient = new Patient(id, name, category, amount, date, photo);
                patientList.add(patient);
                cursor.moveToNext();
            }
        }

        closeConnection();

        // return patient list between two dates
        return patientList;
    }

    public ArrayList<Patient> getExpenseByDate(long fromDate) {
        ArrayList<Patient> patientList = new ArrayList<>();
        openConnection();

        String whereClause = PatientDatabaseHelper.EXPENSE_COL_DATE + "> ?";
        String[] selectionArgs = {String.valueOf(fromDate)};

        Cursor cursor = database.query(PatientDatabaseHelper.EXPENSE_TABLE, null,
                whereClause,
                selectionArgs, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_NAME));
                int category = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_AMOUNT));
                long date = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_DATE));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_PHOTO));
                Patient patient = new Patient(id, name, category, amount, date, photo);
                patientList.add(patient);
                cursor.moveToNext();
            }
        }

        closeConnection();

        // return patient list between two dates
        return patientList;

    }

    public ArrayList<Patient> getExpenseByCategoryAndDate(int catIndex, long unixFromDate,
                                                          long unixToDate) {
        ArrayList<Patient> patientList = new ArrayList<>();
        openConnection();

        String whereClause = PatientDatabaseHelper.EXPENSE_COL_DATE
                + ">= ?"
                + " AND " +
                PatientDatabaseHelper.EXPENSE_COL_DATE
                + "<= ?"
                + " AND " +
                PatientDatabaseHelper.EXPENSE_COL_CATEGORY
                + "= ?";
        String[] selectionArgs = {String.valueOf(unixFromDate),
                String.valueOf(unixToDate),
                String.valueOf(catIndex)};

        Cursor cursor = database.query(PatientDatabaseHelper.EXPENSE_TABLE, null,
                whereClause,
                selectionArgs, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_NAME));
                int category = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_AMOUNT));
                long date = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_DATE));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_PHOTO));
                Patient patient = new Patient(id, name, category, amount, date, photo);
                patientList.add(patient);
                cursor.moveToNext();
            }
        }

        closeConnection();

        // return patient list between two dates
        return patientList;
    }

    public ArrayList<Patient> getExpenseByCategoryAndDate(int catIndex, long unixFromDate) {
        ArrayList<Patient> patientList = new ArrayList<>();
        openConnection();

        String whereClause = PatientDatabaseHelper.EXPENSE_COL_DATE
                + ">= ?"
                + " AND " +
                PatientDatabaseHelper.EXPENSE_COL_CATEGORY
                + "= ?";
        String[] selectionArgs = {String.valueOf(unixFromDate),
                String.valueOf(catIndex)};

        Cursor cursor = database.query(PatientDatabaseHelper.EXPENSE_TABLE, null,
                whereClause,
                selectionArgs, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_NAME));
                int category = cursor.getInt(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_CATEGORY));
                double amount = cursor.getDouble(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_AMOUNT));
                long date = cursor.getLong(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_DATE));
                byte[] photo = cursor.getBlob(cursor.getColumnIndex(PatientDatabaseHelper.EXPENSE_COL_PHOTO));
                Patient patient = new Patient(id, name, category, amount, date, photo);
                patientList.add(patient);
                cursor.moveToNext();
            }
        }

        closeConnection();

        // return patient list between two dates
        return patientList;
    }

    public boolean updateExpense(int id, Patient patient) {
        openConnection();
        ContentValues values = new ContentValues();
        String whereClause = PatientDatabaseHelper.EXPENSE_COL_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        values.put(PatientDatabaseHelper.EXPENSE_COL_NAME, patient.getName());
        values.put(PatientDatabaseHelper.EXPENSE_COL_CATEGORY, patient.getCategory());
        values.put(PatientDatabaseHelper.EXPENSE_COL_AMOUNT, patient.getAmount());
        values.put(PatientDatabaseHelper.EXPENSE_COL_DATE, patient.getUnixDateTime());
        int deletedRowId = database.update(PatientDatabaseHelper.EXPENSE_TABLE, values, whereClause, whereArgs);
        closeConnection();
        if (deletedRowId > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteExpense(int id) {
        openConnection();
        String whereClause = PatientDatabaseHelper.EXPENSE_COL_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        int deletedRowId = database.delete(PatientDatabaseHelper.EXPENSE_TABLE, whereClause, whereArgs);
        closeConnection();
        if (deletedRowId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
