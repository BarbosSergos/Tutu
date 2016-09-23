package com.barbos.sergey.tutu_testproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.barbos.sergey.tutu_testproject.R;
import com.barbos.sergey.tutu_testproject.data.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 21.09.2016.
 */

public class DepartureAdapter extends BaseAdapter implements Filterable{

    private Context mContext;
    private Station[] mStationList;
    private Station[] mCopyOfFullStationList;

    public DepartureAdapter(Context applicationContext, Station[] stations) {
        mContext = applicationContext;
        mStationList = stations;
        mCopyOfFullStationList = stations;
    }

    @Override
    public int getCount() {
        return mStationList.length;
    }

    @Override
    public Object getItem(int i) {
        return mStationList[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View viewConvert, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (viewConvert == null) {
            viewConvert = LayoutInflater.from(mContext).inflate(R.layout.display_one_station_list, null);
            viewHolder = new ViewHolder();
            viewHolder.mCityTitle = (TextView) viewConvert.findViewById(R.id.cityTitleTextView);
            viewHolder.mRegionTitle = (TextView) viewConvert.findViewById(R.id.regionTitleTextView);
            viewHolder.mStationTitle = (TextView) viewConvert.findViewById(R.id.stationTitileTextVIew);

            viewConvert.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) viewConvert.getTag();
        }

        Station station = mStationList[position];

        viewHolder.mCityTitle.setText(station.getCityTitle());
        viewHolder.mRegionTitle.setText(station.getRegionTitle());
        viewHolder.mStationTitle.setText(station.getStationTitle());

        return viewConvert;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence != null) {
                    List<Station> stations = findStations(mContext, charSequence.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = stations;
                    filterResults.count = stations.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                if (filterResults != null && filterResults.count > 0) {
                    List<Station> tmp = (List<Station>) filterResults.values;
                    mStationList = new Station[tmp.size()];
                    tmp.toArray(mStationList);
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }


    private List<Station> findStations(Context context, String searchFor) {

        List<Station> stations = new ArrayList<Station>(mCopyOfFullStationList.length);

        for (int i = 0; i < mCopyOfFullStationList.length - 1; i++) {
            if (mCopyOfFullStationList[i].getStationTitle().toLowerCase().startsWith(searchFor.toLowerCase()) || mCopyOfFullStationList[i].getStationTitle().toLowerCase().contains(searchFor.toLowerCase())) {
                stations.add(mCopyOfFullStationList[i]);
            }
        }
        return stations;
    }

    public static class ViewHolder{
        private TextView mCityTitle;
        private TextView mStationTitle;
        private TextView mRegionTitle;
    }
}
