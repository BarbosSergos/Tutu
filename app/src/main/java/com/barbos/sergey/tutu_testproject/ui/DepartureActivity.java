package com.barbos.sergey.tutu_testproject.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.adapter.DepartureAdapter;
import com.barbos.sergey.tutu_testproject.data.Station;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static com.barbos.sergey.tutu_testproject.ui.MainActivity.*;
import static com.barbos.sergey.tutu_testproject.ui.MainActivity.LIST_OF_DEPARTURE_STATIONS;

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

        int DepartureBehaviorID = intent.getIntExtra(LIST_OF_DEPARTURE_STATIONS, 0);
        int DestinationBehaviorID = intent.getIntExtra(LIST_OF_DESTINATION_STATIONS, 0);

        /*Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DEPARTURE);

        mStations = Arrays.copyOf(parcelables, parcelables.length, Station[].class);*/

        if (DepartureBehaviorID == 1 && DestinationBehaviorID == 0) {

            //Попробуем здесь получить ссылку на файл с массивом станций отправления, прочесть его содержимое и преобразовать в лист с помощью адаптера.
            FileInputStream fis;
            try {
                fis = openFileInput(LIST_OF_DEPARTURE_STATIONS);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Station> returnlist = (ArrayList<Station>) ois.readObject();
                ois.close();
                //Преобразуем ArrayList<Stations> в обычный массив объектов Station
                mStations = new Station[returnlist.size()];
                returnlist.toArray(mStations);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (DepartureBehaviorID == 0 && DestinationBehaviorID == 2){
            //Попробуем здесь получить ссылку на файл с массивом станций назначения, прочесть его содержимое и преобразовать в лист с помощью адаптера.
            FileInputStream fis;
            try {
                fis = openFileInput(LIST_OF_DESTINATION_STATIONS);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Station> returnlist = (ArrayList<Station>) ois.readObject();
                ois.close();
                //Преобразуем ArrayList<Stations> в обычный массив объектов Station
                mStations = new Station[returnlist.size()];
                returnlist.toArray(mStations);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        departureAdapter = new DepartureAdapter(getApplicationContext(), mStations);
        setListAdapter(departureAdapter);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // данный метод работает каждый раз, когда пользователь изменяет текст
                DepartureActivity.this.departureAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
