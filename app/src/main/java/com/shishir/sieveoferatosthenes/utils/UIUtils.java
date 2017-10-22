package com.shishir.sieveoferatosthenes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shishir.sieveoferatosthenes.R;

/**
 * Created by Shishir on 10/21/2017.
 */

public class UIUtils {

    public static void showToast(Context ctx, int msg) {

        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context ctx, String msg) {

        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static void showSnackBar(View coordinatorLayout, String msg) {

        Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).show();
    }

}
