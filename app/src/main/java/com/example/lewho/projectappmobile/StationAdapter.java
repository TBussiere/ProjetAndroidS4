package com.example.lewho.projectappmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.p1601248.miniproj.R;
//import com.example.p1601248.miniproj.Station;

import java.util.ArrayList;
import java.util.List;

public class StationAdapter extends ArrayAdapter<Station> implements Filterable {
    List<Station> items;
    List<Station> itemsSearch;


    //Stations est la liste des models à afficher
    public StationAdapter(Context context, List<Station> Stations) {
        super(context, 0, Stations);
        items = Stations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_station,parent, false);
        }

        StationViewHolder viewHolder = (StationViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new StationViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.bikesAvailable = (TextView) convertView.findViewById(R.id.bikesAvailable);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Station> Stations
        Station Station = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(Station.getName());
        viewHolder.address.setText(Station.getAddress());
        viewHolder.bikesAvailable.setText("Bikes available " + Station.getAvailableBikes() + "/" + Station.getBikeStands());
        return convertView;
    }

    private class StationViewHolder{
        public TextView name;
        public TextView address;
        public TextView bikesAvailable;
    }


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //items = (List<Station>) results;
                clear();
                //StationAdapter.super.clear();
                List<Station> res = (List<Station>)results.values;

                for (int i = 0; i < results.count ; i++) {
                    //StationAdapter.super.add(res.get(i));
                    add(res.get(i));
                }
                System.out.println(getCount());
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                //System.out.println("test");

                //itemsSearch = items;
                FilterResults results = new FilterResults();
                ArrayList<Station> FilteredArrayNames = new ArrayList<Station>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < getCount(); i++) {
                    Station data = getItem(i);
                    if (data.getName().toLowerCase().contains(constraint.toString()))  {
                        FilteredArrayNames.add(data);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
    }
}
