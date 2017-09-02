package com.example.tomislavkralj.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.farmanimallife.R;


/**
 * Created by tomislav.kralj on 29.5.2017..
 */

public class PigAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Pig> mDataSource;

    public PigAdapter(Context context, ArrayList<Pig> items) {
        mContext = context;
        mDataSource = items;
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
        View rowView = mInflater.inflate(null,parent,false);
        Resources res = mContext.getResources();

        TextView pig_list_weight =
                (TextView) rowView.findViewById(R.id.pig_list_weight);
        TextView pig_id =
                (TextView) rowView.findViewById(R.id.pig_id);
        ImageView pig_list_gender =
                (ImageView) rowView.findViewById(R.id.pig_list_gender);
        Pig pig  = (Pig) getItem(position);

        pig_list_weight.setText(res.getString(R.string.weightAndKg, pig.getWeight()));
        pig_id.setText(res.getString(R.string.idAndID, pig.getId()));

        pig_list_gender.setImageResource( pig.isGender() ? R.drawable.female_sign : R.drawable.male_sign);

        return rowView;
    }
}
