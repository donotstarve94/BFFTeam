package com.example.gnud.dulich.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gnud.dulich.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by GNUD on 17/08/2016.
 */
public class ListStickAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Activity activity;
    private int layoutId, layoutId1;
    private ArrayList<String> myArray = null;


    public ListStickAdapter(Activity activity, int layoutId, int layoutId1, ArrayList<String> arrayList) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.layoutId1 = layoutId1;
        this.myArray = arrayList;
    }


    @Override
    public int getCount() {
        return myArray.size();
    }

    @Override
    public Object getItem(int position) {
        return myArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
            holder.textView = (TextView) convertView.findViewById(R.id.txtRowListStick);
            holder.imgView = (ImageView) convertView.findViewById(R.id.imgRowListStick);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(getCount() > 2){
            holder.textView.setText(myArray.get(position).substring(myArray.get(position).indexOf(",") + 1,myArray.get(position).length() - 1));//check
            String rowcheck = myArray.get(position).substring(myArray.get(position).indexOf(","));
            if('0' == rowcheck.charAt(rowcheck.length()-1)){
                holder.imgView.setImageResource(R.drawable.ic_unchecked);
                holder.textView.setAlpha(1.0f);
            } else {
                holder.imgView.setImageResource(R.drawable.ic_checked);
                holder.textView.setAlpha(0.5f);
            }
        }

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final HeaderViewHolder holder;
        if(convertView == null){
            holder = new HeaderViewHolder();
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(layoutId1, null);
            holder.text = (TextView) convertView.findViewById(R.id.txtHeaderListStick);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
//        if(countries[position].equals("1") || countries[position].equals("2")){
//            String headerText = countries[position];
//            holder.text.setText(headerText);
//        }
        if(getCount() > 2){
            String headerText = "" + myArray.get(position).substring(2, myArray.get(position).indexOf(","));
            holder.text.setText(headerText);
            holder.text.setAlpha(1);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
//        if(myArray.get(position) instanceof Text){
//
//        }
        return super.getItemViewType(position);
    }

    @Override
    public long getHeaderId(int position){
        if(getCount() > 2) return Long.parseLong(myArray.get(position).substring(0,2));
        return 0;
    }
    class HeaderViewHolder{
        TextView text;
    }

    class ViewHolder{
        TextView textView;
        ImageView imgView;
    }
}
