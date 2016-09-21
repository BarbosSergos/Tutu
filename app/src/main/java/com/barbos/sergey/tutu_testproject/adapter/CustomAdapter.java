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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 21.09.2016.
 */
public class CustomAdapter extends BaseAdapter implements Filterable{

    private Context mContext;
    private List<Station> mResults;

    public CustomAdapter(Context context, Station[] stations) {
        mContext = context;
        mResults = Arrays.asList(stations);
    }


    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int i) {
        return mResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        ViewHolder holder;

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.display_one_station_list, viewGroup, false);
            holder = new ViewHolder();

            holder.mStationTitle = (TextView) view.findViewById(R.id.stationTitileTextVIew);
            holder.mCityTitle = (TextView) view.findViewById(R.id.cityTitleTextView);
            holder.mRegionTitle = (TextView) view.findViewById(R.id.regionTitleTextView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        Station station = mResults.get(position);

        holder.mStationTitle.setText(station.getStationTitle());
        holder.mCityTitle.setText(station.getCityTitle());
        holder.mRegionTitle.setText(station.getRegionTitle());


        return view;
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
                } else {
                    List<Station> stationsAll = showAll(mContext);
                    filterResults.values = stationsAll;
                    filterResults.count = stationsAll.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    mResults = (List<Station>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private List<Station> showAll(Context context) {
        List<Station> stations = mResults;

        return stations;
    }

    private List<Station> findStations(Context context, String searchFor) {

        List<Station> stations = new ArrayList<Station>(mResults.size());

        for (int i = 0; i < mResults.size(); i++) {
            if (mResults.get(i).getStationTitle().toLowerCase().startsWith(searchFor.toLowerCase())) {
                stations.add(mResults.get(i));
            }
        }
        return stations;
    }

    public static class ViewHolder{

        private TextView mStationTitle;
        private TextView mCityTitle;
        private TextView mRegionTitle;
    }
}
