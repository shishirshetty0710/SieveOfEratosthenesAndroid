package com.shishir.sieveoferatosthenes;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nvanbenschoten.motion.ParallaxImageView;
import com.shishir.sieveoferatosthenes.adapter.SieveOfEratosthenesAdapter;
import com.shishir.sieveoferatosthenes.adapter.SpacesItemDecoration;
import com.shishir.sieveoferatosthenes.data.SieveNum;
import com.shishir.sieveoferatosthenes.utils.PermissionUtil;
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

    int requestedNum = 0;

    ArrayList<SieveNum> sieNums = null;

    private final int EDT_TEXT_DIALOG = 0;
    private final int WEB_VIEW_DIALOG = 1;

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
                    doTheMath(checkIfNumber(result.get(0)));
                }
                break;
            }

        }
    }

    private void doTheMath(int number) {

        sieNums = new ArrayList<>();

        if (number < 2) {
            recyclerView.setVisibility(View.GONE);
            ll_mic.setVisibility(View.VISIBLE);
            requestedNum = -1;
            return;
        }

        requestedNum = number;
        number += 1;


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
                showAlertEnterNum(MainActivity.this, EDT_TEXT_DIALOG);
                return true;

            case R.id.item3:

                if (sieNums != null) {

                    checkPermission();
                }


                return true;

            case R.id.item4:

                showAlertEnterNum(MainActivity.this, WEB_VIEW_DIALOG);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Alert dialog code with an Edit text to take number
     */
    void showAlertEnterNum(Activity context, int type) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = null;
        if (type == 0)
            dialogView = inflater.inflate(R.layout.dialog_main_activity, null);
        else if (type == 1)
            dialogView = inflater.inflate(R.layout.dialog_web_activity, null);
        dialogBuilder.setView(dialogView);

        if (type == 0) {
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

        } else if (type == 1) {

            final WebView webView = (WebView) dialogView.findViewById(R.id.wb_edt_diag);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes");
            dialogBuilder.setMessage("About this project");
            dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
        }

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    void checkPermission() {

        PermissionUtil.checkPermission(MainActivity.this, context, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onNeedPermission() {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
                        );
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        UIUtils.showToast(context, "Need this permssion to save result to text file.");
                    }

                    @Override
                    public void onPermissionDisabled() {
                        UIUtils.showToast(context, "Permission Disabled.");
                    }

                    @Override
                    public void onPermissionGranted() {
                        writeToFile();
                    }
                });

    }

    void writeToFile() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("-------------------------------\n");
        stringBuilder.append("Prime numbers from 1 to " + requestedNum + "\n");
        for (SieveNum objSeSieveNum : sieNums) {

            if (objSeSieveNum.isFlag()) {

                stringBuilder.append(objSeSieveNum.getNum()).append("\n");
            }

        }
        stringBuilder.append("-------------------------------\n");

        UIUtils.writeToFile(stringBuilder.toString(), context);
    }
}

