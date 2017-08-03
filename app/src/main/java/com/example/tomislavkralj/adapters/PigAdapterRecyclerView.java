package com.example.tomislavkralj.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomislavkralj.animals.Hog;
import com.example.tomislavkralj.animals.Pig;
import com.example.tomislavkralj.animals.Sow;
import com.example.tomislavkralj.dbSqlite.MyDbHelper;
import com.example.tomislavkralj.farmanimallife.PigsDetailsActivity;
import com.example.tomislavkralj.farmanimallife.R;

import java.util.List;

public class PigAdapterRecyclerView extends RecyclerView.Adapter<PigAdapterRecyclerView.ViewHolder> {

    private List<Pig> values;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pig_id;
        public TextView pig_weight;
        public ImageView pig_gander;
        public ImageButton pig_delete;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            pig_id = (TextView) v.findViewById(R.id.pig_id);
            pig_weight = (TextView) v.findViewById(R.id.pig_list_weight);
            pig_gander = (ImageView) v.findViewById(R.id.pig_list_gender);
            pig_delete = (ImageButton) v.findViewById(R.id.delete_pig_bttn);
        }
    }

    public void add(int position, Pig item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public PigAdapterRecyclerView(List<Pig> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }

    @Override
    public PigAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PigAdapterRecyclerView.ViewHolder holder, final int position) {
        Resources res = context.getResources();
        final Pig pig = values.get(position);

        holder.pig_id.setText(res.getString(R.string.idAndID, pig.getId()));
        holder.pig_weight.setText(res.getString(R.string.weightNumAndKg, pig.getWeight()));
        holder.pig_gander.setImageResource( pig.isGender() ? R.drawable.female_sign : R.drawable.male_sign);
        holder.pig_delete.setImageResource(R.drawable.delfatjpg);

        holder.pig_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDbHelper myDb = new MyDbHelper(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                Resources res = context.getResources();
                builder.setMessage((res.getString(R.string.alertDeletePig, pig.getId())));

                builder.setPositiveButton(res.getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       myDb.deletePig(pig.getId());
                       remove(position);
                       notifyItemRemoved(position);
                       myDb.close();
                    }
                });
                builder.setNegativeButton(res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                builder.show();
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PigsDetailsActivity.class);

                if(pig.isGender()){
                    intent.putExtra("OBJEKT", (Sow)pig);
                }else{
                    Hog hog = (Hog) pig;
                    intent.putExtra("OBJEKT", hog);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  values.size();
    }
}