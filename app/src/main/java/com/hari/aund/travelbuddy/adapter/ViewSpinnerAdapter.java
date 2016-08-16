package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hari.aund.travelbuddy.activity.PlacesCategoryActivity;

/**
 * Created by Hari Nivas Kumar R P on 8/16/2016.
 */
public class ViewSpinnerAdapter extends ArrayAdapter<String>
        implements ThemedSpinnerAdapter {

    private static final String LOG_TAG = ViewSectionsPagerAdapter.class.getSimpleName();

    private PlacesCategoryActivity placesCategoryActivity;
    private final Helper mDropDownHelper;

    public ViewSpinnerAdapter(PlacesCategoryActivity placesCategoryActivity,
                              Context context,
                              String[] subTypeNames) {
        super(context, android.R.layout.simple_list_item_1, subTypeNames);
        this.placesCategoryActivity = placesCategoryActivity;
        mDropDownHelper = new Helper(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return mDropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        mDropDownHelper.setDropDownViewTheme(theme);
    }
}
