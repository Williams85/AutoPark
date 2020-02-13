package com.upc.autoparqueo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.upc.autoparqueo.R;
import com.upc.autoparqueo.modelos.Distrito;

import java.util.ArrayList;
import java.util.List;

public class DistritoAdapter extends ArrayAdapter {

    private Context mContext;
    private List<Distrito> data = new ArrayList<>();

    public DistritoAdapter(@NonNull Context context, List<Distrito> data) {
        super(context, 0 , data);
        mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        ((TextView) convertView).setText(data.get(position).getNombre());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        ((TextView) convertView).setText(data.get(position).getNombre());
        return convertView;

    }
}