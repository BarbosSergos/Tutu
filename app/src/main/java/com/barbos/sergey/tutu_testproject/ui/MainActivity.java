package com.barbos.sergey.tutu_testproject.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.data.DetailForDuty;
import com.barbos.sergey.tutu_testproject.data.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DEPARTURE = "DEPARTURE";
    public static final String LIST_OF_DESTINATION_STATIONS = "listOfDestinationStations";
    public static final String LIST_OF_DEPARTURE_STATIONS = "listOfDepartureStations";


    private String mJsonData;

    private DetailForDuty mDetailForDuty;

    private EditText mDepartureEd;
    private EditText mDestinationEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDepartureEd = (EditText)findViewById(R.id.editTextDepartureAddress);
        mDestinationEd = (EditText)findViewById(R.id.editTextDestinationAddress);

        mDetailForDuty = new DetailForDuty();

        loadJSONFromAsset();
        Log.d(TAG, mJsonData);
        if (mJsonData != null) {
            parseJSONData(mJsonData);
        }


    }



    //Читаем помещенный ранее в res/raw JSON файл, в строку
    public void loadJSONFromAsset() {
        try {

            InputStream is = getResources().openRawResource(R.raw.allstations);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            mJsonData = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Парсим данные из JSON
    private void parseJSONData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            //Заполняем массив mStationsOrigination

            mDetailForDuty.setStationsOrigination(getStationsFrom(jsonObject));

            //Заполняем массив mStationsDestination
            mDetailForDuty.setStationsDestination(getStationsTo(jsonObject));

            //mCountriesFrm = getStationsFrom(jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Station[] getStationsFrom(JSONObject jsonOnject) throws JSONException {

        ArrayList<Station> stationFrm = new ArrayList<Station>();

        //Получаем JSON массив всех стран и городов
        JSONArray countriesAndCities = jsonOnject.getJSONArray("citiesFrom");

        //Проходим по массиву стран и городов, и заполняем станции
        for (int i = 0; i < countriesAndCities.length(); i++) {
            //Получаем объект "Страна, город"
            JSONObject countryCity = countriesAndCities.getJSONObject(i);
            //Получаем JSON массив станций данного города
            JSONArray stations = countryCity.getJSONArray("stations");

            //заполняем массив станция для данного города
            for (int j = 0; j < stations.length(); j++) {
                JSONObject station = stations.getJSONObject(j);

                Station objectToAdd = new Station();

                objectToAdd.setCountryTitle(station.getString("countryTitle"));
                objectToAdd.setCityTitle(station.getString("cityTitle"));
                objectToAdd.setRegionTitle(station.getString("regionTitle"));
                objectToAdd.setDistrictTitle(station.getString("districtTitle"));
                objectToAdd.setStationTitle(station.getString("stationTitle"));

                stationFrm.add(objectToAdd);
            }

        }

        //Запишем полученный массив станций отправления в файл

        FileOutputStream fos = null;
        try {
        fos = getApplicationContext().openFileOutput(LIST_OF_DEPARTURE_STATIONS, Context.MODE_PRIVATE);
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(fos);
        oos.writeObject(stationFrm);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationFrm.toArray(new Station[stationFrm.size()]);
    }

    private Station[] getStationsTo(JSONObject jsonOnject) throws JSONException {

        ArrayList<Station> stationTo = new ArrayList<Station>();

        //Получаем JSON массив всех стран и городов
        JSONArray countriesAndCities = jsonOnject.getJSONArray("citiesTo");

        //Проходим по массиву стран и городов, и заполняем станции
        for (int i = 0; i < countriesAndCities.length(); i++) {
            //Получаем объект "Страна, город"
            JSONObject countryCity = countriesAndCities.getJSONObject(i);
            //Получаем JSON массив станций данного города
            JSONArray stations = countryCity.getJSONArray("stations");

            //заполняем массив станций для данного города
            for (int j = 0; j < stations.length(); j++) {
                JSONObject station = stations.getJSONObject(j);

                Station objectToAdd = new Station();

                objectToAdd.setCountryTitle(station.getString("countryTitle"));
                objectToAdd.setCityTitle(station.getString("cityTitle"));
                objectToAdd.setRegionTitle(station.getString("regionTitle"));
                objectToAdd.setDistrictTitle(station.getString("districtTitle"));
                objectToAdd.setStationTitle(station.getString("stationTitle"));

                stationTo.add(objectToAdd);
            }

        }

        //Запишем полученный массив станций назначения в файл

        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput(LIST_OF_DESTINATION_STATIONS, Context.MODE_PRIVATE);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(fos);
            oos.writeObject(stationTo);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationTo.toArray(new Station[stationTo.size()]);
    }

//Пока отключим эти методы, в XML убрал вызов метода onClick, буду пробовать формировать лист по mEditText.addTextChangedListener
    public void departureMethod(View view) {
        Intent intent = new Intent(getApplicationContext(), DepartureActivity.class);
        /*intent.putExtra(DEPARTURE, mDetailForDuty.getStationsOrigination());*/
        /*intent.putExtra(LIST_OF_DEPARTURE_STATIONS, 1);*/
        intent.putExtra(LIST_OF_DEPARTURE_STATIONS, true);
        startActivity(intent);
    }

    public void destinationMethod(View view) {
        Intent intent = new Intent(getApplicationContext(), DepartureActivity.class);
        /*intent.putExtra(DEPARTURE, mDetailForDuty.getStationsDestination());*/
        /*intent.putExtra(LIST_OF_DESTINATION_STATIONS, 2);*/
        intent.putExtra(LIST_OF_DESTINATION_STATIONS, true);
        startActivity(intent);
    }


    public void callDatePicker(View view) {

        DialogFragment dialog = new com.barbos.sergey.tutu_testproject.ui.DatePicker();
        dialog.show(getFragmentManager(), "datePicker");
    }
}
