package com.example.novigradv2.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.novigradv2.R;

import java.util.List;

public class RequestedServiceListView extends ArrayAdapter<RequestedService> {
    private Activity context;
    private List<RequestedService> list;

    public RequestedServiceListView(Activity context, List<RequestedService> list) {
        super(context, R.layout.layout_services_fields,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_services_fields, null, true);
        TextView textViewField = (TextView) listViewItem.findViewById(R.id.textViewField);
        textViewField.setText(list.get(position).getService().getTitle().concat(" : ").concat(list.get(position).getStatus()));
        return listViewItem;
    }
}
