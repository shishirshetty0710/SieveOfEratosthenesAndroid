package com.shishir.sieveoferatosthenes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shishir.sieveoferatosthenes.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.R.attr.path;

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

    public static void writeToFile(String data, Context ctx) {
        String filename = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/sieveoferatosthenes.txt";
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            showToast(ctx, "Something went wrong while writing to file!");
        }

        showToast(ctx, "Check file " + filename);
    }

}
