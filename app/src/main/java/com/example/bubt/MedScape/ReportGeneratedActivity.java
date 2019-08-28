package com.example.bubt.MedScape;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.bubt.MedScape.PatientManage.DATAPASSKEY;

public class ReportGeneratedActivity extends AppCompatActivity {
    private TextView fromDateTextView;
    private TextView toDateTextView;
    private ImageButton fromDateImageButton;
    private ImageButton toDateImageButton;
    private Button generateReportButton;
    private TextView emptyReportTextView;
    private LinearLayout reportLinearLayout;
    private ListView expanseReportLV;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    Spinner expenseCategorySpinner;

    private boolean isFromDateChanged = false;
    private boolean isToDateChanged = false;

    PatientDataSource patientDataSource = new PatientDataSource(ReportGeneratedActivity.this);

    List<Patient> expens = new ArrayList<>();
    ListView expenseReportListView;
    PatientReportAdapter patientReportAdapter;

    String[] categories = {
            "All",
            "Diabetes",
            "Typhoid",
            "Jaundice",
            "Skin disease",
            "Diarrhoea",
            "Others"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_generated);


        getSupportActionBar().setTitle("Patient Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        expens = patientDataSource.getAllExpense();

        emptyReportTextView = findViewById(R.id.emptyReportTextView);
        expenseCategorySpinner = findViewById(R.id.expenseCategorySpinner);
        fromDateTextView = findViewById(R.id.dateFromTextView);
        toDateTextView = findViewById(R.id.dateToTextView);
        fromDateImageButton = findViewById(R.id.dateFromImageButton);
        toDateImageButton = findViewById(R.id.dateToImageButton);
        expenseReportListView = findViewById(R.id.expenseReportListView);
        generateReportButton = findViewById(R.id.reportGenerateButton);
        reportLinearLayout = findViewById(R.id.reportLinearLayout);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        categories); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(spinnerArrayAdapter);

        patientReportAdapter = new PatientReportAdapter(this, expens);
        expenseReportListView.setAdapter(patientReportAdapter);


        expanseReportLV = findViewById(R.id.expenseReportListView);

        expanseReportLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Patient patient = expens.get(position);
                Intent intent = new Intent(ReportGeneratedActivity.this,ViewPatient.class);
                intent.putExtra(DATAPASSKEY, patient);
                startActivity(intent);
            }
        });

    }

    public void showFromDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(ReportGeneratedActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        fromDateTextView.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
        isFromDateChanged = true;
    }

    public void showToDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(ReportGeneratedActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        toDateTextView.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
        isToDateChanged = true;
    }

    public void generateReport(View view) {
        if (expenseCategorySpinner.getSelectedItemPosition() > 0
                && isFromDateChanged
                && isToDateChanged) {
            int catIndex = expenseCategorySpinner.getSelectedItemPosition()-1;
            long unixFromDate = getUnixDate(fromDateTextView.getText().toString());
            long unixToDate = getUnixDate(toDateTextView.getText().toString());
            ArrayList<Patient> datePatientList = patientDataSource.getExpenseByCategoryAndDate(catIndex,
                    unixFromDate,
                    unixToDate);
            if (datePatientList.size() > 0) {
                emptyReportTextView.setVisibility(View.GONE);
                reportLinearLayout.setVisibility(View.VISIBLE);
                patientReportAdapter = new PatientReportAdapter(this, datePatientList);
                expenseReportListView.setAdapter(patientReportAdapter);
            } else {
                emptyReportTextView.setVisibility(View.VISIBLE);
                reportLinearLayout.setVisibility(View.GONE);
            }
        }
        else if (expenseCategorySpinner.getSelectedItemPosition() > 0
                && isFromDateChanged) {
            int catIndex = expenseCategorySpinner.getSelectedItemPosition()-1;
            long unixFromDate = getUnixDate(fromDateTextView.getText().toString());
            ArrayList<Patient> datePatientList = patientDataSource.getExpenseByCategoryAndDate(catIndex,
                    unixFromDate);
            if (datePatientList.size() > 0) {
                emptyReportTextView.setVisibility(View.GONE);
                reportLinearLayout.setVisibility(View.VISIBLE);
                patientReportAdapter = new PatientReportAdapter(this, datePatientList);
                expenseReportListView.setAdapter(patientReportAdapter);
            } else {
                emptyReportTextView.setVisibility(View.VISIBLE);
                reportLinearLayout.setVisibility(View.GONE);
            }
        }
        else if (expenseCategorySpinner.getSelectedItemPosition() == 0
                && isFromDateChanged
                && isToDateChanged) {
            long unixFromDate = getUnixDate(fromDateTextView.getText().toString());
            long unixToDate = getUnixDate(toDateTextView.getText().toString());
            ArrayList<Patient> datePatientList = patientDataSource.getExpenseByDate(unixFromDate,
                    unixToDate);
            if (datePatientList.size() > 0) {
                emptyReportTextView.setVisibility(View.GONE);
                reportLinearLayout.setVisibility(View.VISIBLE);
                patientReportAdapter = new PatientReportAdapter(this, datePatientList);
                expenseReportListView.setAdapter(patientReportAdapter);
            } else {
                emptyReportTextView.setVisibility(View.VISIBLE);
                reportLinearLayout.setVisibility(View.GONE);
            }

        }
        else if (expenseCategorySpinner.getSelectedItemPosition() == 0
                && isFromDateChanged) {
            long unixDate = getUnixDate(fromDateTextView.getText().toString());
            ArrayList<Patient> datePatientList = patientDataSource.getExpenseByDate(unixDate);

            if (datePatientList.size() > 0) {
                emptyReportTextView.setVisibility(View.GONE);
                reportLinearLayout.setVisibility(View.VISIBLE);
                patientReportAdapter = new PatientReportAdapter(this, datePatientList);
                expenseReportListView.setAdapter(patientReportAdapter);
            } else {
                emptyReportTextView.setVisibility(View.VISIBLE);
                reportLinearLayout.setVisibility(View.GONE);
            }
        }
        else if (expenseCategorySpinner.getSelectedItemPosition() == 0) {
            ArrayList<Patient> catPatientList = (ArrayList<Patient>) patientDataSource.getAllExpense();
            if (catPatientList.size() > 0) {
                emptyReportTextView.setVisibility(View.GONE);
                reportLinearLayout.setVisibility(View.VISIBLE);
                patientReportAdapter = new PatientReportAdapter(this, catPatientList);
                expenseReportListView.setAdapter(patientReportAdapter);
            } else {
                emptyReportTextView.setVisibility(View.VISIBLE);
                reportLinearLayout.setVisibility(View.GONE);
            }
        }
        else {
            int catIndex = expenseCategorySpinner.getSelectedItemPosition();
            ArrayList<Patient> catPatientList = patientDataSource.getCategoryExpense(catIndex);

            patientReportAdapter = new PatientReportAdapter(this, catPatientList);
            expenseReportListView.setAdapter(patientReportAdapter);
        }
    }

    private long getUnixDate(String dateStr) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        long unixDate = -1;
        try {
            Date date = formatter.parse(dateStr);
            unixDate = date.getTime();
        } catch (ParseException e) {
            Toast.makeText(this, "Cannot format Date", Toast.LENGTH_SHORT).show();
        }
        return unixDate;
    }
}
