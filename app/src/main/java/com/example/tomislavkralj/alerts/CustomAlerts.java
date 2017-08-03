package com.example.tomislavkralj.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;

/**
 * Created by tomislav.kralj on 3.8.2017..
 */

public class CustomAlerts {

    public boolean customAlert(Context context, int posBttn, int negBttn, int messg){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        Resources res = context.getResources();
        builder.setMessage(res.getString(messg));
        final boolean[] answ = new boolean[1];

        builder.setPositiveButton(res.getString(posBttn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                answ[0] = true;
            }
        });
        builder.setNegativeButton(res.getString(negBttn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                answ[0] = false;
            }
        });

        builder.show();
        return answ[0];
    }

}
