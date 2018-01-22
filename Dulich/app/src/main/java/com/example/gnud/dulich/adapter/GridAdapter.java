package com.example.gnud.dulich.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gnud.dulich.R;
import com.example.gnud.dulich.item.EntityActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GNUD on 09/08/2016.
 */
public class GridAdapter extends BaseAdapter {
    private Activity activity;
    private int layoutId;
    private List<EntityActivity> mylist = null;
    public GridAdapter(Activity activity, int layoutId, ArrayList<EntityActivity> arr){
        this.activity = activity;
        this.layoutId = layoutId;
        this.mylist = arr;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        if(mylist.size() > 0){
            TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            ImageView imgView = (ImageView) convertView.findViewById(R.id.imgView);
            LinearLayout linearLayoutItem = (LinearLayout) convertView.findViewById(R.id.linearlayoutItem);

            EntityActivity entityActivity = mylist.get(position);
            imgView.setImageResource(entityActivity.getImageId());
            txtTitle.setText(entityActivity.getTitle());
            if(entityActivity.isCheck()) {
                linearLayoutItem.setBackgroundResource(R.drawable.background_icon_selected);
                txtTitle.setTextColor(Color.parseColor("#ffffff"));
                imgView.setColorFilter(Color.parseColor("#ffffff"));
            }

        }
        return convertView;
    }
}
