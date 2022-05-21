package com.example.novigradv2.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.novigradv2.R;

import java.util.List;

public class EmployeesListView extends ArrayAdapter<Employee> {
    private Activity context;
    private List<Employee> list;

    public EmployeesListView(Activity context, List<Employee> list) {
        super(context,R.layout.layout_users_list,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_users_list, null, true);

        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewUsername = (TextView) listViewItem.findViewById(R.id.textViewUsername);

        Employee employee = list.get(position);
        textViewEmail.setText(employee.getEmail());
        textViewUsername.setText(employee.getUsername());

        return listViewItem;
    }

}
