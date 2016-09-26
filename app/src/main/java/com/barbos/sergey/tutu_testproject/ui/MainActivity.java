package com.barbos.sergey.tutu_testproject.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    public static final String LIST_OF_DESTINATION_STATIONS = "listOfDestinationStations";
    public static final String LIST_OF_DEPARTURE_STATIONS = "listOfDepartureStations";
    public static final int DEPARTURE_REQUEST_CODE = 1;
    public static final int DESTINATION_REQUEST_CODE = 2;
    public static final String DEPARTURE_STATION = "departureStations";
    public static final String DESTINATION_STATION = "destinationStations";
    public static final int DIALOG = 1;


    private String mJsonData;

    private DetailForDuty mDetailForDuty;

    private Button mDepartureEd;
    private Button mDestinationEd;
    private Button mDatePicker;

    private TextView mTextViewShowStatus;
    private ProgressBar mProgressBar;

    //Переменные для отображения меню в виде AlertDialog
    private LinearLayout view;
    private TextView mTextViewCopyright;
    private TextView mTextViewVersion;
    private String versionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDepartureEd = (Button) findViewById(R.id.DepartureAddressMainActivity);
        mDestinationEd = (Button) findViewById(R.id.DestinationAddressMainActivity);
        mDatePicker = (Button) findViewById(R.id.datePicker);

        mTextViewShowStatus = (TextView) findViewById(R.id.textViewShowStatus);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        try {
            versionName =  getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mDetailForDuty = new DetailForDuty();

        //Запуск чтения из файла и парсинга JSON формата в отдельном потоке.

        Thread runInBackground = new Thread(new Runnable() {
            @Override
            public void run() {
                loadJSONFromAsset();
                /*Log.d(TAG, mJsonData);*/
                if (mJsonData != null) {
                    parseJSONData(mJsonData);
                }
            }
        });
        runInBackground.start();


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

            //Заполняем массив mStationsOrigination объекта Station
            mDetailForDuty.setStationsOrigination(getStationsFrom(jsonObject));

            //Заполняем массив mStationsDestination Station
            mDetailForDuty.setStationsDestination(getStationsTo(jsonObject));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDestinationEd.setEnabled(true);
                    mDepartureEd.setEnabled(true);
                    mDatePicker.setEnabled(true);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mTextViewShowStatus.setVisibility(View.INVISIBLE);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Station[] getStationsFrom(JSONObject jsonOnject) throws JSONException {

        ArrayList<Station> stationFrm = new ArrayList<Station>();

        //Получаем JSON массив всех стран и городов
        JSONArray countriesAndCities = jsonOnject.getJSONArray("citiesFrom");

        //Проходим по массиву стран и городов, и заполняем массив станций
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
        writeStationsToFile(stationFrm, LIST_OF_DEPARTURE_STATIONS);

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
        writeStationsToFile(stationTo, LIST_OF_DESTINATION_STATIONS);

        return stationTo.toArray(new Station[stationTo.size()]);
    }

    private void writeStationsToFile(ArrayList<Station> stationTo, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(fos);
            oos.writeObject(stationTo);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Запуск DepartureActivity для станций отправления
    public void departureMethod(View view) {
        Intent intent = new Intent(getApplicationContext(), DepartureActivity.class);
        intent.putExtra(LIST_OF_DEPARTURE_STATIONS, true);
        startActivityForResult(intent, DEPARTURE_REQUEST_CODE);
    }

    //Запуск DestinationActivity для станций назначения
    public void destinationMethod(View view) {
        Intent intent = new Intent(getApplicationContext(), DepartureActivity.class);
        intent.putExtra(LIST_OF_DESTINATION_STATIONS, true);
        startActivityForResult(intent, DESTINATION_REQUEST_CODE);
    }


    public void callDatePicker(View view) {

        DialogFragment dialog = new com.barbos.sergey.tutu_testproject.ui.DatePicker();
        dialog.show(getFragmentManager(), "datePicker");
    }

    //Здесь проверяем requestCode & resultCode и делаем соответствующие действия
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DEPARTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            mDepartureEd.setText(data.getStringExtra(DEPARTURE_STATION));
        } else if (requestCode == DESTINATION_REQUEST_CODE && resultCode == RESULT_OK) {
            mDestinationEd.setText(data.getStringExtra(DESTINATION_STATION));
        } else {
            Toast.makeText(this, "Station was not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showSchelduler(View view) {
        Toast.makeText(this, R.string.SCHELDULER_TOAST, Toast.LENGTH_SHORT).show();
    }

    //Переопределяем метод для обработки нажатия на кнопку выхода
    @Override
    public void onBackPressed(){
        openQuitDialog();
    }

    private void openQuitDialog(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle(R.string.EXIT_COMFIRMATION);

        //Добавляем кнопку согласия
        quitDialog.setPositiveButton(R.string.EXIT_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        //Добавляем кнопку отмены
        quitDialog.setNegativeButton(R.string.EXIT_CANCELED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.About);

        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        showDialog(DIALOG);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.About);
        // создаем view из dialog.xml
        view = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.menu_dialog, null);
        // устанавливаем ее, как содержимое тела диалога
        adb.setView(view);
        // находим TexView для отображения информации о копирайте и версии программы
        mTextViewCopyright = (TextView) view.findViewById(R.id.tvCopyright);
        mTextViewCopyright.setText(R.string.Copyright);
        mTextViewVersion = (TextView) view.findViewById(R.id.tvVersion);
        mTextViewVersion.setText(getResources().getString(R.string.Version) + " " + versionName + "\n\n");

        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);

        //Nothing to change here right now
    }

}
