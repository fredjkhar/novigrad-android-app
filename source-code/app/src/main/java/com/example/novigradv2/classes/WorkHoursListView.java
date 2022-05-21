package com.example.novigradv2.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.novigradv2.R;

import java.util.List;


public class WorkHoursListView extends ArrayAdapter<WorkHours> {
    private Activity context;
    private List<WorkHours> list;

    public WorkHoursListView(Activity context, List<WorkHours> list) {
        super(context, R.layout.layout_services_fields,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_work_hours, null, true);
        TextView textView = (TextView) listViewItem.findViewById(R.id.workHoursEditText);
        WorkHours workHours = list.get(position);
        String startHour =String.valueOf(workHours.getStartHour());
        String startMinute = String.valueOf(workHours.getStartMinute());
        String endHour = String.valueOf(workHours.getEndHour());
        String endMinute = String.valueOf(workHours.getEndMinute());

        if (startMinute.equals("0")) startMinute = "00";
        if (endMinute.equals("0")) endMinute = "00";
        textView.setText(workHours.getDay().concat(", from ").concat(startHour).concat("h")
                        .concat(startMinute).concat(" to ")
                        .concat(endHour).concat("h")
                        .concat(endMinute));
        return listViewItem;
    }
}
