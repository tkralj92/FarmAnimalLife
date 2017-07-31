package com.example.tomislavkralj.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tomislavkralj.farmanimallife.R;

/**
 * Created by tomislav.kralj on 31.7.2017..
 */

public class PigAdapterRecyclerView extends RecyclerView.Adapter<PigAdapterRecyclerView.MyViewHolder> {

    private String[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public MyViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView15);;
        }
    }

    public PigAdapterRecyclerView(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PigAdapterRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PigAdapterRecyclerView.MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
