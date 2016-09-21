package com.barbos.sergey.tutu_testproject.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.adapter.CustomAdapter;
import com.barbos.sergey.tutu_testproject.data.DetailForDuty;
import com.barbos.sergey.tutu_testproject.data.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private String mJsonData;

    private DetailForDuty mDetailForDuty;

    private AutoCompleteTextView mDepartureAutoCompleteTextView;
    private AutoCompleteTextView mDestinationAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDepartureAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteDeparture);
        mDestinationAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteDestination);

        mDetailForDuty = new DetailForDuty();

        loadJSONFromAsset();
        Log.d(TAG, mJsonData);
        if (mJsonData != null) {
            parseJSONData(mJsonData);
        }

/*        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        CustomAdapter adapterDeparture = new CustomAdapter(getApplicationContext(), mDetailForDuty.getStationsOrigination());
        mDepartureAutoCompleteTextView.setAdapter(adapterDeparture);

        CustomAdapter adapterDestination = new CustomAdapter(getApplicationContext(), mDetailForDuty.getStationsDestination());
        mDestinationAutoCompleteTextView.setAdapter(adapterDestination);
        /*mDepartureAutoCompleteTextView.showDropDown();*/

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

        return stationFrm.toArray(new Station[stationFrm.size()]);
    }

    private Station[] getStationsTo(JSONObject jsonOnject) throws JSONException {

        ArrayList<Station> stationFrm = new ArrayList<Station>();

        //Получаем JSON массив всех стран и городов
        JSONArray countriesAndCities = jsonOnject.getJSONArray("citiesTo");

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

        return stationFrm.toArray(new Station[stationFrm.size()]);
    }


}
