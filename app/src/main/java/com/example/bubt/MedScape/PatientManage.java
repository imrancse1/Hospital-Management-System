package com.example.bubt.MedScape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientManage extends AppCompatActivity {

    Button reportPageButton;
    PatientDataSource patientDataSource = new PatientDataSource(PatientManage.this);
    List<Patient> expens = new ArrayList<>();
    TextView emptyExpense;
    final static String DATAPASSKEY = "aExpense";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_manage);


        getSupportActionBar().setTitle("Patient List");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reportPageButton = findViewById(R.id.reportButton);

        ListView expanseLV = findViewById(R.id.showExpanseLV);
        emptyExpense = findViewById(R.id.emptyExpenseTV);

        expens = patientDataSource.getAllExpense();

        if (expens.size()==0){
            emptyExpense.setVisibility(View.VISIBLE);
        }else if(expens.size()>0){
            PatientAdapter patientAdapter = new PatientAdapter(PatientManage.this, expens);
            expanseLV.setAdapter(patientAdapter);
        }

        expanseLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = expens.get(position);
                Intent intent = new Intent(PatientManage.this,ViewPatient.class);
                intent.putExtra(DATAPASSKEY, patient);
                startActivity(intent);
            }
        });

        reportPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientManage.this, ReportGeneratedActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addNewExpanse(View view) {
        Intent intent = new Intent(PatientManage.this,PatientAdd.class);
        startActivity(intent);
        // Toast.makeText(this, "going to add new hospital page", Toast.LENGTH_SHORT).show();

    }

//    public void viewExpanse(View view) {
//        Intent intent = new Intent(PatientManage.this,ViewPatient.class);
//        startActivity(intent);
//        // Toast.makeText(this, "going to view hospital page", Toast.LENGTH_SHORT).show();
//    }
}
