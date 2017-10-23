package com.shishir.sieveoferatosthenes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = IntroActivity.this;

        addSlide(AppIntroFragment.newInstance("Welcome", "This app will help you generate Prime Numbers (Greek Style!).", R.drawable.ancient_greek, Color.parseColor("#1976D2")));
        addSlide(AppIntroFragment.newInstance("How?", "Simply say the number by tapping the Mic button.", R.drawable.intro_pg_1, Color.parseColor("#1976D2")));
        addSlide(AppIntroFragment.newInstance("Alternatively..", "Manually input numbers from the Menu option (Please be considerate)", R.drawable.intro_pg_2, Color.parseColor("#1976D2")));
        addSlide(AppIntroFragment.newInstance("Wow!", "You can also save the result into a text file (Please give app the necessary permissions.)", R.drawable.intro_pg_3, Color.parseColor("#1976D2")));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        gotToSplashScreen();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        gotToSplashScreen();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    void gotToSplashScreen() {

        startActivity(new Intent(ctx, SplashActivity.class));
        finish();
    }
}
