package com.barbos.sergey.tutu_testproject.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;

import com.barbos.sergey.tutu_testproject.R;


/**
 * Created by Sergey on 25.09.2016.
 */

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    java.util.Calendar calendar;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        calendar = java.util.Calendar.getInstance();


        Dialog picker = new DatePickerDialog(getActivity(), this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH));


        picker.setTitle("Choose date");

        return picker;
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Button tv = (Button) getActivity().findViewById(R.id.datePicker);
        tv.setText(day + "/" + (month+1) + "/" + year);

    }
}
