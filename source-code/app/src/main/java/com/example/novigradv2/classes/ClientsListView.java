package com.example.novigradv2.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.novigradv2.R;

import java.util.List;

public class ClientsListView extends ArrayAdapter<Client> {
    private Activity context;
    private List<Client> list;

    public ClientsListView(Activity context, List<Client> list) {
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

        Client client = list.get(position);
        textViewEmail.setText(client.getEmail());
        textViewUsername.setText(client.getUsername());
        return listViewItem;
    }
}
