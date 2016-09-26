package com.barbos.sergey.tutu_testproject.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.adapter.DepartureAdapter;
import com.barbos.sergey.tutu_testproject.data.Station;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static com.barbos.sergey.tutu_testproject.ui.MainActivity.*;
import static com.barbos.sergey.tutu_testproject.ui.MainActivity.LIST_OF_DEPARTURE_STATIONS;

public class DepartureActivity extends ListActivity {

    private Station[] mStations;
    private EditText mEditText;
    private DepartureAdapter departureAdapter;

    boolean DepartureBehaviorID = false;
    boolean DestinationBehaviorID = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        mEditText = (EditText) findViewById(R.id.editTextSearch);


        Intent intent = getIntent();

        //Получаем extras с массивом станций отправления
        /*boolean DepartureBehaviorID = intent.getBooleanExtra(LIST_OF_DEPARTURE_STATIONS, false);*/
        DepartureBehaviorID = intent.getBooleanExtra(LIST_OF_DEPARTURE_STATIONS, false);
        //Получаем extras с массивом станций назначения
        /*boolean DestinationBehaviorID = intent.getBooleanExtra(LIST_OF_DESTINATION_STATIONS, false);*/
        DestinationBehaviorID = intent.getBooleanExtra(LIST_OF_DESTINATION_STATIONS, false);

        if (DepartureBehaviorID && !DestinationBehaviorID) {

            //Попробуем здесь получить ссылку на файл с массивом станций отправления, прочесть его содержимое и преобразовать в лист с помощью адаптера.
            readStationsFromFile(LIST_OF_DEPARTURE_STATIONS);
        } else if (!DepartureBehaviorID && DestinationBehaviorID){
            //Попробуем здесь получить ссылку на файл с массивом станций назначения, прочесть его содержимое и преобразовать в лист с помощью адаптера.
            readStationsFromFile(LIST_OF_DESTINATION_STATIONS);
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

    private void readStationsFromFile(String listOfDepartureStations) {
        FileInputStream fis;
        try {
            fis = openFileInput(listOfDepartureStations);
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (DepartureBehaviorID && !DestinationBehaviorID) {

            String parcelOfDepartureStations = departureAdapter.getStationList()[position].getStationTitle() + "\n"
                    + departureAdapter.getStationList()[position].getCityTitle() + "\n"
                    + departureAdapter.getStationList()[position].getCountryTitle();

            Intent intent = new Intent();
            intent.putExtra(MainActivity.DEPARTURE_STATION, parcelOfDepartureStations);
            setResult(RESULT_OK, intent);
            finish();

        } else if (!DepartureBehaviorID && DestinationBehaviorID) {
/*            Log.d("Sergey", "View: " + v.getId() + "\n"
                    + "Position: " + position + "\n"
                    + "ID: " + id + "\n"
                    + "Departure station details: " + "\n"
                    + "mStation.stationName:" + mStations[position].getStationTitle() + "\n"
                    + "mStation.cityName:" + mStations[position].getCityTitle() + "\n"
                    + "mStation.countyName:" + mStations[position].getCountryTitle() + "\n"
                    + "mStation.districtName:" + mStations[position].getDistrictTitle() + "\n"
                    + "mStation.regionName:" + mStations[position].getRegionTitle() + "\n"
                    + "Departure Adapter Information: "
                    + departureAdapter.getStationList()[position].getStationTitle()
                    + departureAdapter.getStationList()[position].getCityTitle()
                    + departureAdapter.getStationList()[position].getRegionTitle()
                    + departureAdapter.getStationList()[position].getDistrictTitle()
                    + departureAdapter.getStationList()[position].getCountryTitle());*/

            String parcelOfDestinationStations = departureAdapter.getStationList()[position].getStationTitle() + "\n"
                    + departureAdapter.getStationList()[position].getCityTitle() + "\n"
                    + departureAdapter.getStationList()[position].getCountryTitle();

            Intent intent = new Intent();
            intent.putExtra(MainActivity.DESTINATION_STATION, parcelOfDestinationStations);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
