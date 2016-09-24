package com.barbos.sergey.tutu_testproject.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;

import com.barbos.sergey.tutu_testproject.R;

import java.util.TimeZone;

/**
 * Created by Sergey on 25.09.2016.
 */

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){



        Dialog picker = new DatePickerDialog(getActivity(), this, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);


        picker.setTitle("Choose date");

        return picker;
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        EditText tv = (EditText) getActivity().findViewById(R.id.datePicker);
        tv.setText(day + "/" + month + "/" + year);

    }
}
