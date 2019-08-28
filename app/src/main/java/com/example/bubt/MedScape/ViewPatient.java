package com.example.bubt.MedScape;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPatient extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_REQUEST = 100;
    EditText viewExpanseNameET, viewExpanseAmountET;
    Spinner viewExpanseCatagorySP;
    TextView dateTV, timeTV;
    ImageView dateIV;
    int selectedCategory;
    int expenseId = 0;
    long selectedUnixDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:MM");
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    byte[] photo;
   /* int hour = calendar.get(Calendar.HOUR);
    int min = calendar.get(Calendar.MINUTE);*/

    PatientDataSource dataSource = new PatientDataSource(this);

    private TextView uploadTextView;
    private ImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);


        getSupportActionBar().setTitle("Patient Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewExpanseNameET = findViewById(R.id.expanseNameET);
        viewExpanseAmountET = findViewById(R.id.expanseAmountET);
        viewExpanseCatagorySP = findViewById(R.id.expanseCatagorySP);
        dateTV = findViewById(R.id.dateTV);
        uploadTextView = findViewById(R.id.upload_tv);
        userImageView = findViewById(R.id.user_iv);
        //timeTV=findViewById(R.id.timeTV);
        String[] Catagory = getResources().getStringArray(R.array.expanseCatagory);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewPatient.this, R.layout.support_simple_spinner_dropdown_item, Catagory);
        viewExpanseCatagorySP.setAdapter(arrayAdapter);


        uploadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ViewPatient.this).withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, IMAGE_CAPTURE_REQUEST);
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
        Intent i = getIntent();
        Patient patient = (Patient) i.getSerializableExtra(PatientManage.DATAPASSKEY);
        expenseId = patient.getId();
        String name = patient.getName();
        int category = patient.getCategory();
        double amount = patient.getAmount();
        photo = patient.getPhoto();
        userImageView.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
        selectedUnixDate = patient.getUnixDateTime();
        String unixDate = simpleDateFormat.format(patient.getUnixDateTime());
        String unixTime = simpleTimeFormat.format(patient.getUnixDateTime());
        viewExpanseNameET.setText(name);
        viewExpanseAmountET.setText(String.valueOf(amount));
        viewExpanseCatagorySP.setSelection(category);
        dateTV.setText(unixDate);
        //timeTV.setText(unixTime);

        viewExpanseCatagorySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                selectedCategory = adapterView.getSelectedItemPosition();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photo!=null){
                    Intent intent = new Intent(ViewPatient.this,ViewPresiptionActivity.class);
                    intent.putExtra(ViewPresiptionActivity.EXTRA_PHOTO, photo);
                    startActivity(intent);
                }
            }
        });
        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewPatient.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateStr = day + "/" + (month + 1) + "/" + year;
                        dateTV.setText(dateStr);
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = (Date) formatter.parse(dateStr);
                            selectedUnixDate = date.getTime();
                        } catch (ParseException ex) {
                            Toast.makeText(ViewPatient.this, "Date invalid", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(ViewPatient.this, "Unix date: " + date.getTime(), Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dateIV = findViewById(R.id.dateIV);
        dateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewPatient.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateStr = day + "/" + (month + 1) + "/" + year;
                        dateTV.setText(dateStr);
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = null;
                        try {
                            date = (Date) formatter.parse(dateStr);
                            selectedUnixDate = date.getTime();
                        } catch (ParseException ex) {
                            Toast.makeText(ViewPatient.this, "Date invalid", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(ViewPatient.this, "Unix date: " + date.getTime(), Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        /*timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(ViewPatient.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        timeTV.setText(getAmPmTime(hour, min));
                    }
                }, hour, min, false);
                timePickerDialog.show();
            }
        });*/

    }

    /*private String getAmPmTime(int hours, int minutes) {
        if (hours > 12) {
            hours -= 12;
            return hours+":"+minutes+"PM";
        } else {
            return hours+":"+minutes+"AM";
        }
    }*/

    public void updateExpense(View view) {
        if (viewExpanseNameET.getText().toString().equals("")) {
            viewExpanseNameET.setError("Required Fill Not Null");
        } else {

            if (viewExpanseAmountET.getText().toString().equals("") || Double.parseDouble(viewExpanseAmountET.getText().toString()) < 0) {
                viewExpanseAmountET.setError("Required Fill Not Null");
            } else {
                String expenseName = viewExpanseNameET.getText().toString();
                double amount = Double.parseDouble(viewExpanseAmountET.getText().toString());
                Patient patient = new Patient(expenseId, expenseName, selectedCategory, amount, selectedUnixDate, photo);
                boolean updated = dataSource.updateExpense(expenseId, patient);
                if (updated) {
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ViewPatient.this, PatientManage.class));
                } else {
                    Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();
                }
            }

        }


    }

    public void deleteExpense(View view) {
        AlertDialog.Builder deletedAlert = new AlertDialog.Builder(ViewPatient.this);
        deletedAlert.setTitle("Deleted Patient");
        deletedAlert.setMessage("Do You want to Delete???");
        deletedAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean deleted = dataSource.deleteExpense(expenseId);
                if (deleted) {
                    Toast.makeText(ViewPatient.this, "Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ViewPatient.this, PatientManage.class));
                } else {
                    Toast.makeText(ViewPatient.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });
        deletedAlert.setNegativeButton("No", null);
        deletedAlert.setNeutralButton("Cancel", null);
        deletedAlert.show();

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
