package com.example.gnud.dulich.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gnud.dulich.R;
import com.example.gnud.dulich.item.Trip;

import java.util.ArrayList;

/**
 * Created by GNUD on 16/08/2016.
 */
public class ListviewAdapter extends ArrayAdapter<Trip> {
    private Activity activity;
    private ArrayList<Trip> myArray = null;
    private int layoutId;
    private int lastPosition = -1;

    public ListviewAdapter(Activity activity, int layoutId, ArrayList<Trip> array) {
        super(activity, layoutId, array);
        this.activity = activity;
        this.layoutId = layoutId;
        this.myArray = array;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if(myArray.size() > 0){
            TextView txtlocation = (TextView) convertView.findViewById(R.id.txtlocation);
            TextView txtdatemonth = (TextView) convertView.findViewById(R.id.txtdateofmonth);

            Trip trip = myArray.get(position);
            txtlocation.setText(trip.getLocation());
            txtdatemonth.setText(trip.getDatemonth());

            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.listview_up_from_bottom : R.anim.listview_down_from_top);
            convertView.startAnimation(animation);
            lastPosition = position;
        }

        return convertView;
    }

}
