package com.barbos.sergey.tutu_testproject.data;

/**
 * Created by Sergey on 20.09.2016.
 */
public class Station {

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
}
