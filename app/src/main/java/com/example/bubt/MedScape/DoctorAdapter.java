package com.example.bubt.MedScape;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> implements Filterable {
    private List<Model> modelList = new ArrayList<>();
    private List<Model> filteredList = new ArrayList<>();
    private OnCallListener onCallListener;

    public DoctorAdapter(List<Model> modelList) {
        this.modelList = modelList;
        this.filteredList = modelList;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup,false);
        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder doctorHolder, int i) {
        doctorHolder.bindTo(filteredList.get(i));
    }


    public void setOnCallListener(OnCallListener onCallListener) {
        this.onCallListener = onCallListener;
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Model> tempList = new ArrayList<>();
                if (constraint.toString().isEmpty()) {
                    filteredList = modelList;
                } else {
                    for (Model model : modelList) {
                        if (model.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(model);
                        }
                    }
                    filteredList = tempList;
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<Model>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class DoctorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mTitleTv;
        final TextView mDescTv;
        final ImageView miconIv;
        final TextView mPhoneTV;
        final Button mCallBtn;

        public DoctorHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.mainTitle);
            mDescTv = itemView.findViewById(R.id.mainDesc);
            miconIv = itemView.findViewById(R.id.mainIcon);
            mPhoneTV = itemView.findViewById(R.id.mainPhone);
            mCallBtn = itemView.findViewById(R.id.call_btn);

            mCallBtn.setOnClickListener(this);
        }

        void bindTo(Model model) {
            mTitleTv.setText(model.getTitle());
            mDescTv.setText(model.getDesc());
            miconIv.setImageResource(model.getIcon());
            mPhoneTV.setText(model.getPhone());
        }

        @Override
        public void onClick(View v) {
            if (onCallListener != null) {
                onCallListener.onCallClicked(filteredList.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallListener {
        void onCallClicked(Model model);
    }
}
