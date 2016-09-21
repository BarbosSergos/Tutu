package com.barbos.sergey.tutu_testproject.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.adapter.DepartureAdapter;
import com.barbos.sergey.tutu_testproject.data.Station;

import java.util.Arrays;

public class DepartureActivity extends ListActivity {

    private Station[] mStations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DEPARTURE);

        mStations = Arrays.copyOf(parcelables, parcelables.length, Station[].class);

        DepartureAdapter departureAdapter = new DepartureAdapter(getApplicationContext(), mStations);
        setListAdapter(departureAdapter);

    }
}
