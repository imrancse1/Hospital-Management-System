package com.example.bubt.MedScape;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientAdd extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_REQUEST = 100;
    EditText expanseNameET, expanseAmountET;
    Spinner expanseCatSP;
    ImageView dateIV;
    TextView dateTV, timeTV;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMin = calendar.get(Calendar.MINUTE);
    private List<Patient> patientList = new ArrayList<>();
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = null;
    PatientDataSource patientDataSource = new PatientDataSource(PatientAdd.this);
    boolean added;

    String currentDate = day + "/" + month + "/" + year;
    String currentTime = currentHour + ":" + currentMin;
    private long unixDate;
    private byte[] photo;

    private TextView uploadTextView;
    private ImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);

        expanseNameET = findViewById(R.id.expanseNameET);
        expanseAmountET = findViewById(R.id.expanseAmountET);
        expanseCatSP = findViewById(R.id.expanseCatSP);
        dateTV = findViewById(R.id.dateTV);
        uploadTextView = findViewById(R.id.upload_tv);
        userImageView = findViewById(R.id.user_iv);
        //timeTV = findViewById(R.id.timeTV);

        dateTV.setText(currentDate);
        //timeTV.setText(getAmPmTime(currentHour,currentMin));


        getSupportActionBar().setTitle("Patient Add");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        try {
            date = formatter.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        unixDate = date.getTime();

        String[] Catagory = getResources().getStringArray(R.array.expanseCatagory);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PatientAdd.this, R.layout.support_simple_spinner_dropdown_item, Catagory);
        expanseCatSP.setAdapter(arrayAdapter);

        uploadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(PatientAdd.this).withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,IMAGE_CAPTURE_REQUEST);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        expanseCatSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                String expaseCat = (String) adapterView.getItemAtPosition(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        patientList = patientDataSource.getAllExpense();


        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateStr = day + "/" + (month + 1) + "/" + year;
                        dateTV.setText(dateStr);
                        formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            date = (Date) formatter.parse(dateStr);
                            unixDate = date.getTime();
                        } catch (ParseException ex) {
                            Toast.makeText(PatientAdd.this, "Date invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        dateIV = findViewById(R.id.dateIV);
        dateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateStr = day + "/" + (month + 1) + "/" + year;
                        dateTV.setText(dateStr);
                        formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            date = (Date) formatter.parse(dateStr);
                            unixDate = date.getTime();
                        } catch (ParseException ex) {
                            Toast.makeText(PatientAdd.this, "Date invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

      /*  timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(PatientAdd.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        timeTV.setText(getAmPmTime(hour, min));
                    }
                }, currentHour, currentMin, false);
                timePickerDialog.show();
            }
        });
*/

      userImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (photo!=null){
                  Intent intent = new Intent(PatientAdd.this,ViewPresiptionActivity.class);
                  intent.putExtra(ViewPresiptionActivity.EXTRA_PHOTO, photo);
                  startActivity(intent);
              }
          }
      });
    }

    /*private String getAmPmTime(int hours, int minutes) {
        if (hours > 12) {
            hours -= 12;
            return hours+":"+minutes+"PM";
        } else {
            return hours+":"+minutes+"AM";
        }
    }*/

    public void saveExpanse(View view) {

        if (expanseNameET.getText().toString().equals("")) {
            expanseNameET.setError("Required Fill Not Null");
            return;
        }
        if (TextUtils.isEmpty(expanseAmountET.getText())){
            expanseAmountET.setError("Amount required");
            return;
        }
        if (photo==null){
            uploadTextView.setError("Photo required");
            return;
        }
        saveData();

        /*if(expanseNameET.getText().toString().equals("")){
            expanseNameET.setError("Required Fill Not Null");
        }else if(expanseAmountET.getText().toString().equals("")  || Double.parseDouble(expanseAmountET.getText().toString())<0)
            {
                expanseAmountET.setError("Required Fill Not Null");
            }
            else
            {
                saveData();
            }*/


    }

    private void saveData() {
        String expanseName = expanseNameET.getText().toString();
        int expanseCatagory = expanseCatSP.getSelectedItemPosition();
        double expanseAmount = Double.parseDouble(expanseAmountET.getText().toString());
        long date = unixDate;
        Patient patient = new Patient(expanseName, date, expanseCatagory, expanseAmount, photo);
        added = patientDataSource.addExpense(patient);
        if (added) {
            Intent intent = new Intent(PatientAdd.this, PatientManage.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            userImageView.setImageBitmap(bmp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            photo = stream.toByteArray();
        }
    }
}
