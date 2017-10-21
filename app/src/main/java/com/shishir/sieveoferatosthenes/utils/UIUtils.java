package com.shishir.sieveoferatosthenes.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Shishir on 10/21/2017.
 */

public class UIUtils {

    public static void showToast(Context ctx, String msg){

        Toast.makeText(ctx,msg, Toast.LENGTH_LONG).show();
    }
}
