package com.shishir.sieveoferatosthenes;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.nvanbenschoten.motion.ParallaxImageView;
import com.shishir.sieveoferatosthenes.adapter.SieveOfEratosthenesAdapter;
import com.shishir.sieveoferatosthenes.adapter.SpacesItemDecoration;
import com.shishir.sieveoferatosthenes.data.SieveNum;
import com.shishir.sieveoferatosthenes.utils.UIUtils;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    /**
     * Used Parallax effect to splash screen background
     */
    private ParallaxImageView mBackground;

    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    Context context;

    RecyclerView recyclerView;
    LinearLayout ll_mic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        mBackground = (ParallaxImageView) findViewById(android.R.id.background);

        mBackground.setImageResource(R.drawable.bg_gradient);

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.list);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        ll_mic = (LinearLayout) findViewById(R.id.ll_mic);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        recyclerView.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * Google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            UIUtils.showToast(getApplicationContext(),
                    R.string.speech_not_supported);
        }
    }

    /**
     * Processing speech input from user
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("check", result.get(0) + "");

                    doTheMath(checkIfNumber(result.get(0)));
                }
                break;
            }

        }
    }

    private void doTheMath(int number) {

        if (number == -1)
            return;
        number += 1;
        ArrayList<SieveNum> sieNums = new ArrayList<>();

        for (int i = 0; i < number; i++) {

            sieNums.add(new SieveNum(i, true));

        }
        setValues(sieNums);
        setValues(Logic.processGrid(number, sieNums));
    }

    @Override
    public void onResume() {
        super.onResume();
        mBackground.registerSensorManager();
    }

    @Override
    public void onPause() {
        mBackground.unregisterSensorManager();
        super.onPause();
    }

    void setValues(ArrayList<SieveNum> sienumList) {
        recyclerView.setVisibility(View.VISIBLE);
        ll_mic.setVisibility(View.GONE);
        recyclerView.setAdapter(new SieveOfEratosthenesAdapter(sienumList));
    }


    int checkIfNumber(String num) {
        UIUtils.showSnackBar(findViewById(R.id.rl_mainactivity), "You said, " + num);
        int number = 0;
        if (null != num && num.equals("hundred"))
            return 100;
        try {
            number = Integer.parseInt(num);

        } catch (Exception e) {
            UIUtils.showSnackBar(findViewById(R.id.rl_mainactivity), "Try saying a number this time!");
            return -1;
        }
        return number;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:

                recyclerView.setVisibility(View.GONE);
                ll_mic.setVisibility(View.VISIBLE);

                return true;
            case R.id.item2:

                recyclerView.setVisibility(View.GONE);
                ll_mic.setVisibility(View.VISIBLE);
                showAlertEnterNum(MainActivity.this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showAlertEnterNum(Activity context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_main_activity, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edt_insert_num);

        dialogBuilder.setMessage("Enter number here");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                doTheMath(checkIfNumber(edt.getText().toString()));
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //dismiss dialog
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

}
