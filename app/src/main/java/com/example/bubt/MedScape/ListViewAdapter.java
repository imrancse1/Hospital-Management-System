package com.example.bubt.MedScape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    //varisbls

    Context mcontext;
    LayoutInflater inflater;
    List<Model> modellist;
    ArrayList<Model> arrayList;

    public ListViewAdapter(Context context, List<Model> modellist) {
        mcontext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder {
        TextView mTitleTv, mDescTv, mPhoneTV;
        ImageView miconIv;
    }


    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row, null);

            holder.mTitleTv = view.findViewById(R.id.mainTitle);
            holder.mDescTv = view.findViewById(R.id.mainDesc);
            holder.miconIv = view.findViewById(R.id.mainIcon);
            holder.mPhoneTV = view.findViewById(R.id.mainPhone);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mTitleTv.setText(modellist.get(position).getTitle());
        holder.mDescTv.setText(modellist.get(position).getDesc());
        holder.miconIv.setImageResource(modellist.get(position).getIcon());
        holder.mPhoneTV.setText(modellist.get(position).getPhone());
        return view;
    }

    //filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length() == 0) {
            modellist.addAll(arrayList);
        } else {
            for (Model model : arrayList) {
                if (model.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}