package com.example.koli.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Koli on 11/11/2018.
 */

public class EmployeeAdapter extends ArrayAdapter<DisplayEmployee> {

    public EmployeeAdapter(Activity context, ArrayList<DisplayEmployee> displayEmployees) {
        super(context, 0, displayEmployees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.display_employee, parent, false);
        }

        DisplayEmployee currentEmployee = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.display_name);
        name.setText(currentEmployee.getName());

        TextView salary = (TextView) listItemView.findViewById(R.id.display_salary);
        salary.setText(currentEmployee.getSalary());

        return listItemView;

    }
}
