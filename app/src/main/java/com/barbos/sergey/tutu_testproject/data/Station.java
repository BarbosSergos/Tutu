package com.barbos.sergey.tutu_testproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Sergey on 20.09.2016.
 */
public class Station implements Parcelable, Serializable {

    private String mCountryTitle;
    private String mCityTitle;
    private String mStationTitle;
    private String mRegionTitle;
    private String mDistrictTitle;



    public Station(){

    }

    public Station(String countryTitle, String cityTitle, String stationTitle, String regionTitle, String districtTitle) {

        mCountryTitle = countryTitle;
        mCityTitle = cityTitle;
        mStationTitle = stationTitle;
        mRegionTitle = regionTitle;
        mDistrictTitle = districtTitle;
    }

    protected Station(Parcel in) {
        mCountryTitle = in.readString();
        mCityTitle = in.readString();
        mStationTitle = in.readString();
        mRegionTitle = in.readString();
        mDistrictTitle = in.readString();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    public String getCountryTitle() {
        return mCountryTitle;
    }

    public void setCountryTitle(String countryTitle) {
        mCountryTitle = countryTitle;
    }

    public String getCityTitle() {
        return mCityTitle;
    }

    public void setCityTitle(String cityTitle) {
        mCityTitle = cityTitle;
    }

    public String getStationTitle() {
        return mStationTitle;
    }

    public void setStationTitle(String stationTitle) {
        mStationTitle = stationTitle;
    }

    public String getRegionTitle() {
        return mRegionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        mRegionTitle = regionTitle;
    }

    public String getDistrictTitle() {
        return mDistrictTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        mDistrictTitle = districtTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCountryTitle);
        parcel.writeString(mCityTitle);
        parcel.writeString(mStationTitle);
        parcel.writeString(mRegionTitle);
        parcel.writeString(mDistrictTitle);
    }
}
