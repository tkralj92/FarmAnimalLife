package com.example.tomislavkralj.toasts;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Toast;

import com.example.tomislavkralj.farmanimallife.R;

/**
 * Created by tomislav.kralj on 11.7.2017..
 */

public class CustomToast {
    public static void fillAllFields(Context context){

        Resources res = context.getResources();
        Toast msg = Toast.makeText(context,res.getString(R.string.toastFill), Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        msg.show();
    }
}
