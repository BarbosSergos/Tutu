package com.barbos.sergey.tutu_testproject.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.adapter.DepartureAdapter;
import com.barbos.sergey.tutu_testproject.data.Station;

import java.util.ArrayList;
import java.util.Arrays;

public class DepartureActivity extends ListActivity {

    private Station[] mStations;
    private EditText mEditText;
    private DepartureAdapter departureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        mEditText = (EditText) findViewById(R.id.editTextSearch);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DEPARTURE);

        mStations = Arrays.copyOf(parcelables, parcelables.length, Station[].class);

        departureAdapter = new DepartureAdapter(getApplicationContext(), mStations);
        setListAdapter(departureAdapter);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Когда, юзер изменяет текст он работает
                DepartureActivity.this.departureAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
