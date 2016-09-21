package com.barbos.sergey.tutu_testproject.data;

/**
 * Created by Sergey on 20.09.2016.
 */
public class DetailForDuty {

    private Station[] mStationsOrigination;
    private Station[] mStationsDestination;


    public Station[] getStationsOrigination() {
        return mStationsOrigination;
    }

    public void setStationsOrigination(Station[] stationsOrigination) {
        mStationsOrigination = stationsOrigination;
    }

    public Station[] getStationsDestination() {
        return mStationsDestination;
    }

    public void setStationsDestination(Station[] stationsDestination) {
        mStationsDestination = stationsDestination;
    }
}
