package com.example.bubt.MedScape;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientReportAdapter extends ArrayAdapter {

    private Context context;
    private List<Patient> patientList = new ArrayList<>();
    private LayoutInflater inflater;

    String[] categories = {
            "All",
            "Diabetes",
            "Typhoid",
            "Jaundice",
            "Skin disease",
            "Diarrhoea",
            "Others"
    };

    public PatientReportAdapter(@NonNull Context context, List<Patient> patientList) {
        super(context, R.layout.report_listview, patientList);
        this.context = context;
        this.patientList = patientList;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.report_listview,parent,false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameReportTextView);
        TextView categoryTextView = convertView.findViewById(R.id.categoryReportTextView);
        TextView amountTextView = convertView.findViewById(R.id.amountReportTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateReportTextView);
        nameTextView.setText(patientList.get(position).getName());
        categoryTextView.setText(categories[patientList.get(position).getCategory()]);
        amountTextView.setText(patientList.get(position).getAmount()+"");
        dateTextView.setText(String.valueOf(patientList.get(position).getDateInString()));
        return convertView;
    }
}
