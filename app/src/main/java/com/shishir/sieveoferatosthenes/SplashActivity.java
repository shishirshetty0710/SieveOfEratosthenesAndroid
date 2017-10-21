package com.shishir.sieveoferatosthenes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nvanbenschoten.motion.ParallaxImageView;

public class SplashActivity extends Activity {

    /**Used Parallax effect to splash screen background*/
    private ParallaxImageView mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mBackground = (ParallaxImageView)findViewById(android.R.id.background);

        mBackground.setImageResource(R.drawable.ancient_greek);
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
}
