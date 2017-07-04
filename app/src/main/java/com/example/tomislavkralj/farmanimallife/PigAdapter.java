package com.example.tomislavkralj.farmanimallife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.tomislavkralj.animals.Pig;


/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public class PigAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Pig> mDataSource;

    public PigAdapter(Context context, ArrayList<Pig> items) {
        mContext = context;
        mDataSource = (ArrayList<Pig>)items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {

        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.list_item_pig,parent,false);
        TextView pig_list_weight =
                (TextView) rowView.findViewById(R.id.pig_list_weight);
        TextView pig_id =
                (TextView) rowView.findViewById(R.id.pig_id);
        ImageView pig_list_gender =
                (ImageView) rowView.findViewById(R.id.pig_list_gender);
        Pig sow  = (Pig) getItem(position);
        pig_list_weight.setText("Weight: "+Integer.toString(sow.getWeight())+"kg");
        pig_id.setText("ID: "+Integer.toString(sow.getId()));
        if(!sow.isGender()){
            pig_list_gender.setImageResource(R.drawable.male_sign);
        }else{
            pig_list_gender.setImageResource(R.drawable.female_sign);
        }
        return rowView;
    }
}
