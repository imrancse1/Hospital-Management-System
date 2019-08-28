package com.example.bubt.MedScape;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobile App Develop on 4/27/2018.
 */

public class PatientAdapter extends ArrayAdapter {

    private List<Patient> patientList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    public PatientAdapter(@NonNull Context context, List<Patient> patientList) {
        super(context, R.layout.custome_expanse_layout, patientList);
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custome_expanse_layout, parent, false);
        }

        TextView expanseNameTV = convertView.findViewById(R.id.expanseNameTV);
        TextView expanseDateTV = convertView.findViewById(R.id.dateTV);
        TextView expanseAmountTV = convertView.findViewById(R.id.expanseAmountTv);
        ImageView imgeview = convertView.findViewById(R.id.imageView);

        Patient currentExpanse = patientList.get(position);

        expanseNameTV.setText(currentExpanse.getName());
        long unixDateTime = currentExpanse.getUnixDateTime();
        String date = format.format(unixDateTime);
        expanseDateTV.setText(date);
        expanseAmountTV.setText("$ " + String.valueOf(currentExpanse.getAmount()));
        if (currentExpanse.getPhoto()!=null) {
            imgeview.setImageBitmap(BitmapFactory.decodeByteArray(currentExpanse.getPhoto(), 0, currentExpanse.getPhoto().length));
        }else {
            imgeview.setImageResource(R.drawable.user_avater);
        }
        return convertView;
    }
}
