package com.example.novigradv2.classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.novigradv2.R;

import java.util.List;

public class BranchesListView extends ArrayAdapter<Branch>{
    private Activity context;
    private List<Branch> list;

    public BranchesListView(Activity context, List<Branch> list) {
        super(context, R.layout.layout_services_fields,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_services_fields, null, true);
        TextView textViewField = (TextView) listViewItem.findViewById(R.id.textViewField);
        Branch branch = list.get(position);
        String branchName = (branch.getAddress()).concat(", ").concat(branch.getCity()).concat(", ")
                .concat(branch.getProvince()).concat(",  ").concat(branch.getCodeZIP());
        textViewField.setText(branchName);
        return listViewItem;
    }
}


